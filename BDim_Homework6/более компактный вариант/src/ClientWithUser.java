//вариант реализации где логика приема, отправки сообщений,
//а так же выхода и закрытия сокета реализована через вспомогательный класс User

import java.io.IOException;
import java.net.Socket;

public class ClientWithUser {
    private final String IP = "localhost";
    private User user;

    public ClientWithUser(){
        try {
            user = new User("Сервер", new Socket(IP, 666));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new ClientWithUser();
    }
}
