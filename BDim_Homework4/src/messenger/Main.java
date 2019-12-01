//Dmitrii Babanov, 1.12.2019
//Java level 2, lesson 4, Homework
//Создать окно для клиентской части чата: большое текстовое поле для отображения переписки в центре окна.
//  Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели.
//  Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
//  При «отсылке» сообщение перекидывается из нижнего поля в центральное.

package messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Main extends Application {
    @Override
    public void start(Stage stg) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Messenger.fxml"));
        Scene scn = new Scene(root);
        stg.setScene(scn);
        stg.setTitle("Чат");
        stg.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}