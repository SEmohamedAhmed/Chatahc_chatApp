package cells;

import Chatahc.Message;
import GUI.HomeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MessageCustomCell extends ListCell<Message> {
    @FXML
    private GridPane root;
    @FXML
    private Label messageTime;
    @FXML
    private Label messageText;
    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        FXMLLoader fxmlLoader;
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            if (item.getSenderId() == HomeController.HomeUser.getId()){             //Home user id
                fxmlLoader = new FXMLLoader(getClass().getResource("../UI/OutMessage.fxml"));
            }
            else {
                fxmlLoader = new FXMLLoader(getClass().getResource("../UI/InMessage.fxml"));
            }
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.getMessage();
            }
            messageTime.setText(item.getTime());
            messageText.setText(item.getMessageText());
            setGraphic(root);
        }
    }
}
