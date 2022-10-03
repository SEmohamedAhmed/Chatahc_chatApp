package GUI;

import cells.UserListCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.net.URL;
import java.util.ResourceBundle;
import static GUI.Main.utilities;
public class ContactList extends OptionsController implements Initializable {
    @FXML private ListView<UserListCell> friendsList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            utilities.prepareFriendListGUI(friendsList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
