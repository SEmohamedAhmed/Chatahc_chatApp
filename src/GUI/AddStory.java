package GUI;

import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import static GUI.HomeController.HomeUser;
import static GUI.Main.app;
import static GUI.Main.utilities;

public class AddStory {

    @FXML private JFXTextArea storyJFXTextArea;
    @FXML private ImageView storyPreview;
    @FXML private Button sendButton;
    private File storyImage;
    private CustomAlert alert;
    private String photoPath;
    {
        try {
            alert = new CustomAlert("Story Alert", "Story Was Added");
        } catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }
    public void addTextStory(Event actionEvent) throws SQLException, IOException {
        String newStoryText = storyJFXTextArea.getText();
        if(!newStoryText.equals("")) {
            newStoryText = utilities.prepareString(newStoryText, 240);
            app.addStory(HomeUser.getId(),newStoryText,false);
            alert.openAlert();
            storyJFXTextArea.clear();
        }
    }
    public void addImageStory(Event actionEvent) throws SQLException, IOException {
        if(storyImage!=null) {
            app.addStory(HomeUser.getId(), photoPath,true);
            alert.openAlert();
            utilities.gotoHere("../UI/story_page.fxml",actionEvent);
        }
    }
    public void backToStoryPage(Event mouseEvent) throws IOException {
        utilities.gotoStoryView(mouseEvent);
    }
    public void choosePhoto(Event actionEvent) {
        storyImage = utilities.uploadImage(utilities.getCurrentStage(actionEvent));
        if(storyImage != null)
        {
            photoPath = storyImage.getAbsolutePath();
            storyPreview.setImage(new Image(photoPath));
            sendButton.setDisable(false);   //enable it
        }
    }
}
