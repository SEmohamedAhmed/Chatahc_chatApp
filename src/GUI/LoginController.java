package GUI;

import Chatahc.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLException;
import static GUI.Main.app;
import static GUI.Main.utilities;

public class LoginController
{
@FXML private TextField userName;
@FXML private PasswordField password;
private CustomAlert alert;
public static User uLogin=null;
public void switchToHome(Event actionEvent) throws SQLException, IOException {
    String userNameDB = userName.getText();
    String passwordDB = password.getText();
    uLogin = new User(-1, userNameDB, passwordDB);      // not valid user yet   [1]
    int loginAlert = app.loginValidation(uLogin);// 0 -> Alert  ,1 -> home
    checkLoginAlert(loginAlert,userNameDB,passwordDB,actionEvent);
}
private void checkLoginAlert(int loginAlert,String userNameDB,String passwordDB,Event actionEvent) throws SQLException, IOException {
    if (loginAlert==1) {
        uLogin = app.getUser(app.getUserIdFromUsername(userNameDB)); //valid user login   [2] see[1] !
        utilities.gotoHome(actionEvent);
       // homeController = new HomeController();       // useless & make errors also ?
    }   //else case : there is an error
    else if(loginAlert==0) {
        doAlertAndOpen("Login Failed","Empty field(s)");
    }
    else if(loginAlert==2) {
        doAlertAndOpen("Login Failed","User Not Found");
    }
    else if(loginAlert==3) {
        doAlertAndOpen("Login Failed","Password is wrong");
    }
}
private void doAlertAndOpen(String head,String body) throws IOException {
    alert = new CustomAlert(head,body);
    uLogin = null;
    alert.openAlert();
}
 public void switchToSignup(ActionEvent actionEvent) throws IOException {
     utilities.gotoSignup(actionEvent);
 }
}