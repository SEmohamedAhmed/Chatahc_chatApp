package GUI;

import Chatahc.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import static GUI.Main.app;
import static GUI.Main.utilities;
import static GUI.SignUpController.uSignup;

public class SetProfile
{
    @FXML public GridPane pane;
    @FXML private TextField userDesc;
    @FXML private ImageView userImage;
    @FXML public User currentUser = uSignup;
    private String imageLink = "resources/img/userDefaultImage.png";
    private String userDescription = "Hi there,It's chatahc";
    public void chooseImage(ActionEvent actionEvent) {
       Stage stage = utilities.getCurrentStage(actionEvent);
       File selectedFile = utilities.uploadImage(stage);
       if (selectedFile != null)
           imageLink = selectedFile.getAbsolutePath();
       userImage.setImage(new Image(imageLink));
   }
    public void switchToHome(ActionEvent actionEvent) throws SQLException, IOException {
        currentUser.setUserImageLink(imageLink);
        String userDes = userDesc.getText();
        if(!userDes.equals("")) {
            userDescription = userDes;
        }
        userDescription = utilities.prepareString(userDescription, 21);
        currentUser.setProfileDesc(userDescription);
        app.registerForUser(currentUser);
        uSignup.setId(app.getLastUserId());         // order matters
        utilities.gotoHere("../UI/home_view.fxml",actionEvent);
    }
}

