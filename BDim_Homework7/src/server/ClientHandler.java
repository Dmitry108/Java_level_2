package server;

import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientHandler {
    private MyServerChat server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;

    public ClientHandler (MyServerChat server,  Socket socket){
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(()->{
                String str;
                try {
                    //процесс авторизации
                    while (true){
                        str = in.readUTF();
                        if (str.startsWith("/auth")){
                            String[] sss = str.split(" ", 3);
                            String newNick = server.getAutherization().getNick(sss[1], sss[2]);
                            if (newNick!=null) {
                                send("/authOK "+newNick);
                                nick = newNick;
                                server.broadcast(nick + " подключился к чату");
                                System.out.printf("Клиент %s подключился%n", nick);
                                send("Авторизация прошла успешно");
                                server.subscribe(this);
                                break;
                            } else {
                                send("Неверный логин или пароль");
                            }
                        }
                    }
                    while (true) {
                        str = in.readUTF();
                        if (str.equals("/end")) {
                            send("/end");
                            server.broadcast(nick + " вышел из чата");
                            break;
                        }
                        if (str.startsWith("/w ")){
                            String[] sss = str.split(" ", 3);
                            server.sendPrivate(sss[1], nick + " для " + sss[1] + ": " + sss[2], this);
                        } else {
                        server.broadcast(nick + ": " + str);
                        }
                    }
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                server.unscribe(this);
                System.out.printf("Клиент %s отключился%n", nick);
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void send (String message){
        try {
            out.writeUTF(message);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getNick(){
        return nick;
    }
}