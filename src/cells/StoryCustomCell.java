package cells;

import cells.StoryListCell;
import cells.UserListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class StoryCustomCell extends ListCell<StoryListCell> {
    @FXML
    public GridPane root;
    @FXML
    public ImageView userImage;
    @FXML
    public Label userNameLabel;
    @Override
    protected void updateItem(StoryListCell item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../UI/story_list_cell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            userNameLabel.setText(item.getUserName());
            userImage.setImage(new Image(item.getUserImage()));
            setGraphic(root);
        }
    }
}
