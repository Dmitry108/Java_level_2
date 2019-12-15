package server;

import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientHandler {
    private MyServerChat server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private String login;

    public ClientHandler (MyServerChat server,  Socket socket){
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            //new Thread(()->{
            //таймаут в основном потоке, процесс авторизации - в параллельном
                try {
                    socket.setSoTimeout(120000);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            //}).start();
            new Thread(()->{
                String str;
                boolean isAuth = false;
                try {
                    //процесс авторизации
                    while (true){
                        str = in.readUTF();
                        if (str.startsWith("/")){
                            if (str.startsWith("/reg")){
                                String[] sss = str.split(" ");
                                if (server.getAutherization().registration(sss[1],sss[2],sss[3])){
                                    send("Регистрация прошла успешно");
                                    send("/regOK");
                                } else {
                                    send("Этот логин используется");
                                }
                            }
                            if (str.startsWith("/auth")){
                                String[] sss = str.split(" ", 3);
                                if (server.checkLogin(sss[1])){
                                    String newNick = server.getAutherization().getNick(sss[1], sss[2]);
                                    if (newNick!=null) {
                                        send("/authOK "+newNick);
                                        nick = newNick;
                                        login = sss[1];
                                        isAuth = true;
                                        server.broadcast(nick + " подключился к чату");
                                        System.out.printf("Клиент %s подключился%n", nick);
                                        send("Авторизация прошла успешно");
                                        server.subscribe(this);
                                        break;
                                    } else {
                                        send("Неверный логин или пароль");
                                    }
                                } else {
                                    System.out.println("С данным логином уже авторизовались");
                                    send("С данным логином уже авторизовались");
                                }
                            }
                            if (str.equals("/end")) {
                                out.writeUTF(str);
                                break;
                            }
                        }
                    }
                    socket.setSoTimeout(0);
                    while (isAuth) {
                        str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                send("/end");
                                server.broadcast(nick + " вышел из чата");
                                break;
                            }
                            if (str.startsWith("/w ")){
                                String[] sss = str.split(" ", 3);
                                server.sendPrivate(sss[1], nick + " для " + sss[1] + ": " + sss[2], this);
                            }
                        } else {
                            server.broadcast(nick + ": " + str);
                        }
                    }
                } catch (SocketTimeoutException e) {
                    System.out.println("Время ожидания авторизации истекло, сокет закрывается");
                    send("/end");
                } catch (IOException e){
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (isAuth) {
                    server.unscribe(this);
                    System.out.printf("Клиент %s отключился%n", nick);
                }
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
    public String getLogin(){
        return login;
    }
}