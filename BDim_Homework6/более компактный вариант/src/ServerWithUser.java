//вариант реализации где логика приема, отправки сообщений,
//а так же выхода и закрытия сокета реализована через вспомогательный класс User

import java.io.IOException;
import java.net.ServerSocket;

public class ServerWithUser {
    private ServerSocket server;
    private User user;
    private final int PORT = 666;

    public ServerWithUser(){
        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            user = new User("Клиент", server.accept());
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
    public static void main(String[] args) {
        new ServerWithUser();
    }
}
