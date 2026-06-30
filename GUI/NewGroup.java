package GUI;

import Chatahc.ChatRoom;
import Chatahc.User;
import cells.UserCustomCell;
import cells.UserListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import static GUI.HomeController.HomeUser;
import static GUI.Main.app;
import static GUI.Main.utilities;

public class NewGroup extends OptionsController implements Initializable {
    @FXML private TextField groupName;
    @FXML private ImageView groupImg;
    @FXML private ListView<UserListCell>contactList;
    private CustomAlert alert;
    private String imageLink = "resources/img/Group.png";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            utilities.prepareFriendListGUI(contactList);
            contactList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            System.out.println("NewGroup Failed");
            e.printStackTrace();
        }
    }
    public void uploadGroupPhoto(ActionEvent actionEvent) {
        File gPhoto = utilities.uploadImage(utilities.getCurrentStage(actionEvent));
        if(gPhoto!=null)
           imageLink = gPhoto.getAbsolutePath();
        groupImg.setImage(new Image(imageLink));
    }
    public void createGroup(ActionEvent actionEvent) throws SQLException, IOException {
        String gName = groupName.getText();
        if(gName.equals("")) {
            alert = new CustomAlert("Creation Failed", "Name the group");
            alert.openAlert();
            return;
        }
        gName = utilities.prepareString(gName, 15);
        ObservableList<UserListCell> groupMembers = contactList.getSelectionModel().getSelectedItems();
        if(groupMembers.isEmpty()) {
            alert = new CustomAlert("Creation Failed", "choose at least one friend");
            alert.openAlert();
            return;
        }
        List<User> list = new LinkedList<>();
        list.add(app.getUser(HomeUser.getId()));
        for (UserListCell u:groupMembers) {
            User user = app.getUser(u.getCurFriend().getId());
            list.add(user);
        }
        try {
            app.createGroup(gName,list,imageLink);
            alert = new CustomAlert("Successfully Created", "You have created a group");
        }catch (Exception exception)
        {
            alert = new CustomAlert("Creation Failed", "Exception! Creation Failed");
        }
        alert.openAlert();
    }
}
