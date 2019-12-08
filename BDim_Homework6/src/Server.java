import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    //private Thread inThread;
    //private Thread outThread;

    private final int PORT = 666;

    public Server(){
        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            socket = server.accept();
            System.out.println("Клиент подключен!\nВведите \\q для выхода!");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            //поток ожидания сообщения
            Thread inThread = new Thread(()->{
                String str;
                try {
                    do {
                        str = in.readUTF();
                        if (str.equals("\\q")){
                            System.out.println("Клиент прервал соединение!");
                            in.close();
                            out.close();
                            socket.close();
                        } else {
                            System.out.printf("Клиент: %s%n", str);
                        }
                    } while (!socket.isClosed());
                } catch (IOException e) {
                    System.out.println("Прием сообщений завершен!");//e.printStackTrace();
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
                            socket.close();
                        }
                    } while (!socket.isClosed());
                } catch (IOException e) {
                    System.out.println("Клиент недоступен!");//e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            try {
                //socket.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new Server();
    }
}
