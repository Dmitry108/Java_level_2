package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient {
    @FXML private HBox authBox;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private TextArea textArea;
    @FXML private HBox sendBox;
    @FXML private TextField sendField;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private boolean isAuthenticated;
    private String nick;

    final private String IP = "localhost";
    final private int PORT = 12345;

    public void initialize (){
        setAuthenticated(false);
        textArea.setText("Введите логин и пароль\n");
    }
    private void setAuthenticated (boolean authenticated){
        this.isAuthenticated = authenticated;
        authBox.setManaged(!isAuthenticated);
        authBox.setVisible(!isAuthenticated);
        sendBox.setManaged(isAuthenticated);
        sendBox.setVisible(isAuthenticated);
        if (!isAuthenticated) {
            nick = "";
        }
    }
    @FXML
    public void authorization(ActionEvent event) {
        if (loginField.getText().equals("") || passwordField.getText().equals("")){
            textArea.appendText("Введите логин / пароль\n");
            return;
        }
        if (socket==null || socket.isClosed()){
            connection();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connection(){
        try {
            socket = new Socket(IP, PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            new Thread(()->{
                try{
                    String str;
                    //цикл авторизации
                    while (true){
                        str = in.readUTF();
                        if (str.startsWith("/authOK")){
                            setAuthenticated(true);
                            nick = str.split(" ", 2)[1];
                            break;
                        }
                        textArea.appendText(str + "\n");
                    }
                    setTitle("Чат: " + nick);
                    textArea.clear();
                    sendField.requestFocus();
                    //цикл ожидания сообщений
                    while (true){
                        str = in.readUTF();
                        if (str.equals("/end")) {
                            setAuthenticated(false);
                            textArea.clear();
                            break;
                        }
                        textArea.appendText(str + "\n");
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
                setTitle("Чат");
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void send(ActionEvent event) {
        //метод написания сообщений
        try {
            out.writeUTF(sendField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendField.clear();
        sendField.requestFocus();
    }
    public void toPassword(ActionEvent event) {
        passwordField.requestFocus();
    }
    public void setTitle (String title){
        Platform.runLater(()->{
            ((Stage)textArea.getScene().getWindow()).setTitle(title);
        });
    }
}