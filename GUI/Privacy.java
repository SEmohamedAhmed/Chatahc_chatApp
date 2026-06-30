package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static GUI.HomeController.HomeUser;
import static GUI.Main.app;

public class Privacy extends OptionsController implements Initializable {
    @FXML private RadioButton everyOne;
    @FXML private RadioButton contactsOnly;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(app.getUser(HomeUser.getId()).isProfileVisibility())
                everyOne.setSelected(true);
            else
                contactsOnly.setSelected(true);
        } catch (Exception e) {
            System.out.println("PrivacyOpenFailed");
            e.printStackTrace();
        }
    }
    public void saveProfileVisibility(ActionEvent actionEvent) throws SQLException, IOException {
            if(everyOne.isSelected())
                app.resetUserProfileVisibility(HomeUser.getId(), true);
            else
                app.resetUserProfileVisibility(HomeUser.getId(), false);
            alert = new CustomAlert("Saving changes", "Visibility updated");
            alert.openAlert();
    }
}
