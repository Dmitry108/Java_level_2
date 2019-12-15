package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegController {
    @FXML private PasswordField passwordField;
    @FXML private TextField loginField;
    @FXML private TextField nickField;
    MyClient myClient;

    @FXML
    public void tryReg(ActionEvent event) {
        myClient.tryReg(nickField.getText(), loginField.getText(), passwordField.getText());
    }
    public PasswordField getPasswordField() {
        return passwordField;
    }
    public TextField getLoginField() {
        return loginField;
    }
    public void nickFieldPress(ActionEvent event) {
        loginField.requestFocus();
    }
    public void loginFieldPress(ActionEvent event) {
        passwordField.requestFocus();
    }
}