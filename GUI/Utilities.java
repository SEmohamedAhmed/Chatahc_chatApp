package GUI;

import Chatahc.ChatRoom;
import Chatahc.Message;
import Chatahc.User;
import cells.UserCustomCell;
import cells.UserListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import static GUI.HomeController.HomeUser;
import static GUI.Main.app;

public class Utilities {
    public void gotoHere(String link, Event event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(link)));
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }
    public void gotoHome(Event event) throws IOException{
        gotoHere("../UI/home_view.fxml",event);
    }
    public void gotoSignup(Event event) throws IOException{
        gotoHere("../UI/SignUp.fxml",event);
    }
    public void gotoLogin(Event event) throws IOException{
        gotoHere("../UI/Login.fxml",event);
    }
    public void gotoStoryView(Event event) throws IOException{
        gotoHere("../UI/story_page.fxml",event);
    }
    public void gotoOptions(Event event) throws IOException{
        gotoHere("../UI/Options _change_Profile_Description_Scene.fxml",event);
    }
    public boolean checkPhoneNum(String phoneNum) {
        String phoneDigits = "0123456789";
        for (int i = 0; i < phoneNum.length(); ++i) {
            String digit = String.valueOf(phoneNum.charAt(i));      // or use CharSequence DT
            if (!phoneDigits.contains(digit))
                return false;
        }
        //else is true phone num
        return true;
    }
    public String getTime(String time,int end) {
        if (time == null)
            return "";
        return time.substring(0, end);
    }
    public File uploadImage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Image");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null && isImage(selectedFile.getAbsolutePath()))        // 1st cond. is a must
            return selectedFile;
        return null;
    }
    public boolean isImage(String imgLink) {
        String fileFormat = imgLink.substring(imgLink.length() - 3);
        if(fileFormat.equals("jpg") || fileFormat.equals("png"))
            return true;
        return false;
    }
    public void closeRequest(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Chatahc");
        alert.setContentText("Are you sure to close the chatahc ?");
        if(alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
    public Stage getCurrentStage(Event event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }
    public void prepareFriendListGUI(ListView<UserListCell> listView) throws SQLException, IOException {
        LinkedList<User> userArrayList = app.getFriendList(HomeUser.getId());
        LinkedList<UserListCell> arrayList = new LinkedList<>();
        for (User u:userArrayList) {
            UserListCell userListCell = new UserListCell(u);
            userListCell.setCurFriend(u);
            userListCell.editListCellPrivacy();     //apply visibility rules
            arrayList.addLast(userListCell);
        }
        ObservableList<UserListCell> userListCellObservableList= FXCollections.observableArrayList(arrayList);
        listView.setItems(userListCellObservableList);
        listView.setCellFactory(lv -> new UserCustomCell());
    }
    public User getFriendUser(ChatRoom chatRoom) throws SQLException {
        ArrayList<User> arrayList = chatRoom.getUserList();
        for (User u:arrayList)
        {
            if (u.getId()!=HomeUser.getId())
                return app.getUser(u.getId());
        }
        return null;
    }
    public String prepareString(String s, int maxLength){
        s = s.trim();       // erase leading & railing spaces
        s = s.substring(0, Math.min(s.length(), maxLength));
        return s;
    }
    public Image getUserDefaultImage() {
        return new Image("C:\\Users\\future\\IdeaProjects\\FinalWhatsApp\\src\\resources\\img\\userDefaultImage.png");
    }
}