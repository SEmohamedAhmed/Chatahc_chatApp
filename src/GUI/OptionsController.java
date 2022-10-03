package GUI;

import Chatahc.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static GUI.HomeController.HomeUser;
import static GUI.Main.app;
import static GUI.Main.utilities;

public class OptionsController implements Initializable{
    @FXML private ImageView userImage;
    @FXML private TextField bioTextField;
    @FXML private PasswordField passwordField;
    @FXML private  TextField contactName;
    @FXML private TextField contactNumber;
    private File uploadedImage;
    protected CustomAlert alert;
    private User u = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            u = app.getUser(HomeUser.getId());
            if(userImage != null)
                 userImage.setImage(new Image(app.getUser(HomeUser.getId()).getUserImageLink()));
            if(bioTextField != null)
                bioTextField.setText(u.getProfileDesc());
        } catch (Exception e) {
            System.out.println("Options Initialization failed");
            e.getMessage();
        }
        }
    public void uploadPhoto(ActionEvent actionEvent) {
        Stage currentStage = utilities.getCurrentStage(actionEvent);
        uploadedImage = utilities.uploadImage(currentStage);
        if(uploadedImage != null) {
            Image userNewImage = new Image(uploadedImage.getAbsolutePath());
            userImage.setImage(userNewImage);
        }
    }
    public void saveProfileDescChanges(ActionEvent actionEvent) throws SQLException, IOException {
        if (uploadedImage != null) {
            app.resetUserImage(HomeUser.getId(),uploadedImage.getAbsolutePath());
        }
        String profileDesc = bioTextField.getText();
        if(!HomeUser.getProfileDesc().equals(profileDesc)&&!profileDesc.equals("")) {
            profileDesc = utilities.prepareString(profileDesc, 20);
            app.resetUserDesc(HomeUser.getId(),profileDesc);
            System.out.println("profile Desc was reset");
        }
        alert = new CustomAlert("Changes saved", "All is done");
        alert.openAlert();
    }
    public void resetPassword(ActionEvent actionEvent) throws SQLException, IOException {
        String newPassword = passwordField.getText();
        if(!newPassword.equals(""))
        {
            newPassword = utilities.prepareString(newPassword, 40);
            app.resetPassword(HomeUser.getId(), newPassword);
            passwordField.clear();
            alert = new CustomAlert("Password change", "Password was reset successfully");
            alert.openAlert();
        }
    }
    public void addNewContact(ActionEvent actionEvent) throws SQLException, IOException {
        String contactSavedName = contactName.getText();
        contactSavedName = utilities.prepareString(contactSavedName, 20);
        User newContact = isValidContact(contactSavedName,contactNumber.getText());
        if(newContact != null ) {
            // u cant double a saved contact
            String fName = app.getFriendName(HomeUser.getId(),newContact.getId());
            if( fName!= null && !fName.equals("0")) {       // isSaved and is connection between us
                alert = new CustomAlert("Contact was not added", "Contact was already added");
            }
            // u cant add your self
            else if(newContact.getPhoneNumber().equals(HomeUser.getPhoneNumber())) {
                alert = new CustomAlert("Contact was not added", "You cant add yourself");
            }
            else {

                app.addConnection(HomeUser.getId(),newContact.getId(),contactSavedName);
                alert = new CustomAlert("Contact was added", "Contact was added successfully");
            }
        }
        else {
            alert =new CustomAlert("Contact was not added", "Enter the data correctly");
        }
        alert.openAlert();
        contactName.clear();
        contactNumber.clear();
    }
    private User isValidContact(String contName,String phoneNum) throws SQLException {
        if(contName.equals("") || phoneNum.equals("")||!utilities.checkPhoneNum(phoneNum))
            return null;
        return app.getUserByPhoneNumber(phoneNum);
    }

    public void goto1 (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHere("../UI/Options _change_Profile_Description_Scene.fxml", mouseEvent);
    }
    public void goto2 (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHere("../UI/Options _Password_Scene.fxml", mouseEvent);
    }
    public void goto3 (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHere("../UI/Options _Profile_Privacy_Scene.fxml", mouseEvent);

    }
    public void goto4 (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHere("../UI/Options _ContactsList_Scene_HandeledbylistView.fxml", mouseEvent);
    }
    public void goto5 (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHere("../UI/Options _AddNewContact.fxml", mouseEvent);
    }
    public void goto6 (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHere("../UI/Options _AddNewGroup_handeledbylistView.fxml", mouseEvent);
    }
    public void gotoHome (MouseEvent mouseEvent) throws IOException {
        utilities.gotoHome(mouseEvent);
    }
    public void logout(MouseEvent mouseEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Chatahc");
        alert.setContentText("Are you sure to logout from chatahc ?");
        if(alert.showAndWait().get() == ButtonType.OK){
            HomeUser = LoginController.uLogin = SignUpController.uSignup=null;
            utilities.gotoHere("../UI/Login.fxml", mouseEvent);
        }
    }
}
