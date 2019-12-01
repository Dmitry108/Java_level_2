package messenger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MessengerController {
    @FXML public TextField messageTF;
    @FXML public TextArea fieldOfMessageTA;
    @FXML public Button sendBtn;

    @FXML
    public void send(ActionEvent event) {
        fieldOfMessageTA.appendText(messageTF.getText()+"\n");
        messageTF.setText("");
        messageTF.requestFocus();
    }
    @FXML
    public void pressEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode()== KeyCode.ENTER)
            send(new ActionEvent());
    }
    @FXML
    public void newChart(ActionEvent event) {
        fieldOfMessageTA.setText("");
    }
    @FXML
    public void close(ActionEvent event) {
        System.exit(0);
    }
}