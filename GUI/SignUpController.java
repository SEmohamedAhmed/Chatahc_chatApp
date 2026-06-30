package GUI;

import Chatahc.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import static GUI.Main.app;
import static GUI.Main.utilities;

public class SignUpController
{
    @FXML private TextField phoneID;
    @FXML private TextField userName;
    @FXML private PasswordField password;
    public static User uSignup = null;
    private CustomAlert alert;
    public void SetProfile(ActionEvent actionEvent) throws IOException, SQLException {
        if(dataValidation(actionEvent)) {
            utilities.gotoHere("../UI/SetProfilePhoto.fxml",actionEvent);
        }
    }
    public boolean dataValidation(ActionEvent actionEvent) throws SQLException, IOException {
        String name = userName.getText(),passWord = password.getText(),phone = phoneID.getText();
        name = utilities.prepareString(name, 20);
        passWord = utilities.prepareString(passWord, 40);
        phone = utilities.prepareString(phone, 20);
        uSignup = new User(-1,name ,passWord , phone);
           int choice = app.userDataValidation(uSignup);
           if(choice == 0) {
                return true;
           }
           else if(choice == 1) {
               alert = new CustomAlert("Signup Failed","Username exists");
           }
           else if(choice == 2) {
               alert = new CustomAlert("Signup Failed","Phone number exists");
           }
           else if(choice == 3) {
               alert = new CustomAlert("Signup Failed","Phone number can contains numbers only");
           }
           else if(choice == -1) {
               alert = new CustomAlert("Signup Failed","Empty field(s)");
           }
            alert.openAlert();
           return false;
    }
    public void switchToLogin(ActionEvent actionEvent) throws IOException {
        utilities.gotoLogin(actionEvent);
    }
}
