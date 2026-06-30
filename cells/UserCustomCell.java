package cells;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class UserCustomCell extends ListCell<UserListCell> {
    @FXML public GridPane root;
    @FXML public ImageView userImage;
    @FXML public Label userNameLabel;
    @FXML public Label lastMessageLabel;
    @FXML public Label messageTimeLabel;
    @FXML public Label notificationsNumber;
    @FXML public StackPane notificationPane;
    @Override
    protected void updateItem(UserListCell item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/userChat_cell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userNameLabel.setText(item.getUserName());
            lastMessageLabel.setText(item.getLastMessage());
            messageTimeLabel.setText(item.getTime());
            String notificationsNum = item.getNotificationsNumber();
            userImage.setImage(item.getUserImage());
            if (!notificationsNum.equals("0")) {
                notificationsNumber.setText(notificationsNum);
                if (!notificationPane.isVisible()) notificationPane.setVisible(true);
            }
            setGraphic(root);
        }
    }
}
