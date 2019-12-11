//вариант реализации где логика приема, отправки сообщений,
//а так же выхода и закрытия сокета реализована через вспомогательный класс User

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class User {

    public User(String name, Socket socket){
        try {
            System.out.printf("%s доступен!\n", name);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            //поток ожидания сообщений
            Thread inThread = new Thread(()->{
                String str;
                try {
                    do {
                        str = in.readUTF();
                        if (str.equals("\\q")){
                            System.out.printf("%s прервал соединение!", name);
                            in.close();
                            out.close();
                            socket.close();
                        } else {
                            System.out.printf("%s: %s%n", name, str);
                        }
                    } while (!socket.isClosed());
                } catch (IOException e) {
                    System.out.println("Прием сообщений завершен!");
                }
            });
            //поток написания сообщения
            Thread outThread = new Thread(()->{
                String str;
                Scanner sc = new Scanner(System.in);
                try {
                    do {
                        str = sc.nextLine();
                        out.writeUTF(str);
                        if (str.equals("\\q")){
                            System.out.println("Вы прервали соединение!");
                            in.close();
                            out.close();
                            socket.close();
                        }
                    } while (!socket.isClosed());
                } catch (IOException e) {
                    System.out.println("Сервер недоступен!");
                }
            });
            inThread.start();
            outThread.start();
            try {
                inThread.join();
                outThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Сервер недоступен!");//e.printStackTrace();
        }
    }
}
