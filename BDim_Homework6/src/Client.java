import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private final String IP = "localhost";
    private final int PORT = 666;

    public Client(){
        try {
            socket = new Socket(IP, PORT);
            System.out.println("Сервер доступен!\nВведите \\q для выхода!");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            //поток ожидания сообщений
            Thread inThread = new Thread(()->{
                String str;
                try {
                    do {
                        str = in.readUTF();
                        if (str.equals("\\q")){
                            System.out.println("Сервер прервал соединение!");
                            in.close();
                            out.close();
                            socket.close();
                        } else {
                            System.out.printf("Сервер: %s%n", str);
                        }
                    } while (!socket.isClosed());
                } catch (IOException e) {
                    System.out.println("Прием сообщений завершен!");//e.printStackTrace();
                } while (!socket.isClosed());
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
                    System.out.println("Сервер недоступен!");//e.printStackTrace();
                } while (!socket.isClosed());
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

    public static void main(String[] args) {
        new Client();
    }
}
