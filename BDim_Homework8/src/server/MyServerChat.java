package server;

import java.net.ServerSocket;
import java.io.IOException;
import java.util.Vector;

public class MyServerChat {
    private ServerSocket server;
    private Vector<ClientHandler> clients;
    private Autherization autherization;

    public MyServerChat() {
        try {
            server = new ServerSocket(222);
            System.out.println("Сервер открыт");
            clients = new Vector<>();
            autherization = new AuthData();
            while (true) {
                new ClientHandler(this, server.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void broadcast (String message){
        for (ClientHandler ch: clients){
            ch.send(message);
        }
    }
    public void sendPrivate(String nick, String message, ClientHandler sender) {
        //boolean isAbsent = true;
        for (ClientHandler ch: clients) {
            if (ch.getNick().equals(nick)) {
                ch.send(message);
                sender.send(message);
          //      isAbsent = false;
            //    break;
                return;
            }
        }
        //if (isAbsent) {
            sender.send(nick + " отсутствует в чате");
        //}
    }
    public Autherization getAutherization(){
        return autherization;
    }
    public void subscribe (ClientHandler client){
        clients.add(client);
        sendClientList();
    }
    public void unscribe (ClientHandler client) {
        clients.remove(client);
        sendClientList();
    }
    public void sendClientList(){
        StringBuilder s = new StringBuilder("/clients ");
        for (ClientHandler ch: clients) {
            s.append(String.format("%s ", ch.getNick()));
        }
        broadcast(s.toString());
    }
    public boolean checkLogin(String login){
        for (ClientHandler c: clients){
            if (c.getLogin().equals(login)) return false;
        }
        return true;
    }
}