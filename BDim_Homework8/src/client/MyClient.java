package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient {
    @FXML private ListView<String> clientsList;
    @FXML private HBox areaBox;
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
    private Stage regStage;
    private RegController rc;

    final private String IP = "localhost";
    final private int PORT = 222;

    public void initialize (){
        regStage = getRegstrationWindow();
        setAuthenticated(false);
        textArea.setText("Введите логин и пароль\n");
        Platform.runLater(()->{
            (textArea.getScene().getWindow()).setOnCloseRequest(event -> {
                if (socket != null && !socket.isClosed()){
                    try {
                        out.writeUTF("/end");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }
    private void setAuthenticated (boolean authenticated){
        this.isAuthenticated = authenticated;
        authBox.setManaged(!isAuthenticated);
        authBox.setVisible(!isAuthenticated);
        clientsList.setManaged(isAuthenticated);
        clientsList.setVisible(isAuthenticated);
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
                    //цикл регистрации и авторизации
                    while (true){
                        str = in.readUTF();
                        if (str.startsWith("/")){
                            if (str.equals("/regOK")) {
                                Platform.runLater(() -> {
                                    loginField.setText(rc.getLoginField().getText());
                                    passwordField.setText(rc.getPasswordField().getText());
                                    regStage.close();
                                    passwordField.requestFocus();
                                });
                            }
                            if (str.startsWith("/authOK")){
                                setAuthenticated(true);
                                nick = str.split(" ", 2)[1];
                                break;
                            }
                            if (str.equals("/end")) break;
                        } else {
                            textArea.appendText(str + "\n");
                        }
                    }
                    setTitle("Чат: " + nick);
                    textArea.clear();
                    //цикл ожидания сообщений
                    while (isAuthenticated){
                        str = in.readUTF();
                        if (str.startsWith("/")){
                            if (str.equals("/end")) {
                                setAuthenticated(false);
                                textArea.clear();
                                break;
                            }
                            if (str.startsWith("/clients")){
                                String[] sss = str.split(" ");
                                int l = sss.length;
                                Platform.runLater(()->{
                                    clientsList.getItems().clear();
                                    for (int i=1; i<l; i++){
                                        clientsList.getItems().add(sss[i]);
                                    }
                                });
                            }
                        } else {
                            textArea.appendText(str + "\n");
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
    public void clickItem(MouseEvent mouseEvent) {
        sendField.setText(String.format("/w %s ",
                clientsList.getSelectionModel().getSelectedItem()));
        sendField.requestFocus();
        sendField.selectEnd();
    }
    public void regisration(ActionEvent event) {
        regStage.show();
    }
    public Stage getRegstrationWindow(){
        Stage stage = new Stage();
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("registration.fxml"));
            Scene sc = new Scene(fxml.load());
            stage.initModality(Modality.APPLICATION_MODAL);
            rc = fxml.getController();
            rc.myClient = this;
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Регистрация");
            stage.setScene(sc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }
    public void tryReg(String nick, String login, String password){
        if (socket == null || socket.isClosed()){
            connection();
        }
        try {
            out.writeUTF(String.format("/reg %s %s %s", nick, login, password));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}