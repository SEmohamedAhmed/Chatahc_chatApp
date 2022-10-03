package GUI;

import cells.StoryCustomCell;
import cells.StoryListCell;
import Chatahc.Story;
import Chatahc.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static GUI.HomeController.HomeUser;
import static GUI.Main.app;
import static GUI.Main.utilities;
public class StoryView implements Initializable {

    @FXML private Label storyRoomName;
    @FXML private Label storyTime;
    @FXML private ImageView userImage;
    @FXML private ImageView storyPhoto;
    @FXML private ImageView left;
    @FXML private ImageView right;
    @FXML private Label currentStoryText;
    @FXML private ListView<StoryListCell>userListView;
    private static LinkedList<Story>currentStoryList;
    private static int currentStoryIndex;
    private ObservableList<StoryListCell> usersList;
    public StoryView(){}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            prepareStories();
        } catch (Exception e) {
            System.out.println("StoryInitialization failed");
            e.getMessage();
        }
    }
    private void prepareStories() throws SQLException {
        LinkedList<User> friendsList = app.getFriendList(HomeUser.getId()); //to be moved
        LinkedList<StoryListCell>allListCells= new LinkedList<>();
        // add current user stories first
        LinkedList<Story>homeStories=app.getStoryList(HomeUser.getId());
        if(!homeStories.isEmpty()) {
            StoryListCell curUser=new StoryListCell(HomeUser);
            curUser.setStoriesUserList(homeStories);
            curUser.setUserName("My Stories");
            allListCells.add(curUser);
        }
        for(User user:friendsList) {
            if(user!=null) {
                String hName = app.getFriendName(user.getId(), HomeUser.getId());
                String fName = app.getFriendName(HomeUser.getId(),user.getId());
                if(hName != null) {
                    LinkedList<Story> userStories = app.getStoryList(user.getId());
                    if (!userStories.isEmpty())        //this friend has stories
                    {
                        StoryListCell storyListCell = new StoryListCell(user,fName);
                        storyListCell.setStoriesUserList(app.getStoryList(user.getId()));
                        allListCells.add(storyListCell);
                    }
                }
            }
        }
        usersList = FXCollections.observableArrayList(allListCells);
        userListView.setItems(usersList);
        prepareStoriesGUI();
    }
    private void prepareStoriesGUI() throws SQLException {
        userImage.setImage(new Image(app.getUser(HomeUser.getId()).getUserImageLink()));
        userListView.setCellFactory(lv -> new StoryCustomCell() {});
        userListView.getSelectionModel().selectedItemProperty().addListener(s -> {
            prepareStoriesFirstView();
        });
    }
    private void prepareStoriesFirstView() {
        StoryListCell storyListCell = userListView.getSelectionModel().getSelectedItem();
        currentStoryList = storyListCell.getStoriesUserList();
        currentStoryIndex = 0;
        setStory(currentStoryList.get(currentStoryIndex));
        storyRoomName.setText(storyListCell.getUserName());
        setVisibility();
    }
    public void prevStory(Event actionEvent) {
        if(currentStoryIndex-1 > -1)
        {
            currentStoryIndex--;
            Story currentStory=currentStoryList.get(currentStoryIndex);
            setStory(currentStory);
        }
    }
    public void nextStory(Event actionEvent) {
        if(currentStoryIndex+1 < currentStoryList.size())
        {
            currentStoryIndex++;
            Story currentStory=currentStoryList.get(currentStoryIndex);
            setStory(currentStory);
        }
    }
    private void setStory(Story currentStory) {
        if(currentStory.isImage())
            prepareStoryImage(currentStory);
        else
            prepareStoryText(currentStory);
        storyTime.setText("Published at : " + utilities.getTime(String.valueOf(currentStory.getStoryUploadedTime()),5));
    }
    private void prepareStoryImage(Story story) {
        editVbox(480, 575, true, false);
        storyPhoto.setImage(new Image(story.getStoryText()));
    }
    private void prepareStoryText(Story story) {
        editVbox(1, 1, false, true);
        currentStoryText.setText(story.getStoryText());
    }
    private void editVbox(double height,double width,boolean isImage,boolean isText) {
        storyPhoto.setFitHeight(height);
        storyPhoto.setFitWidth(width);
        storyPhoto.setVisible(isImage);
        currentStoryText.setVisible(isText);
    }
    private void setVisibility() {
        storyTime.setText("Published at : "+utilities.getTime(String.valueOf(currentStoryList.get(0).getStoryUploadedTime()),5));
        storyRoomName.setVisible(true);
        storyTime.setVisible(true);
        left.setVisible(true);
        right.setVisible(true);
    }
    public void gotoHome(MouseEvent mouseEvent) throws IOException {
        utilities.gotoHome(mouseEvent);
    }
    public void gotoTypeStory(Event actionEvent) throws IOException {
        utilities.gotoHere("../UI/type_story.fxml",actionEvent);
    }
    public void gotoUploadStory(Event actionEvent) throws IOException {
        utilities.gotoHere("../UI/uploadStoryPhoto.fxml",actionEvent);
    }
}
