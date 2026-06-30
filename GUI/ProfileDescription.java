package GUI;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;

public class ProfileDescription {
    protected Stage stage = new Stage();
    protected AnchorPane anchor;        //for a child also
    public ProfileDescription(String userImageLink,String userName, String profileDescription) throws IOException {
        anchor = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../UI/Profile_Description.fxml")));
        ObservableList<Node>  vBoxe = anchor.getChildren();       // vbox has 4 children
        VBox vBox = ((VBox)vBoxe.get(0));
        ObservableList<Node> allElements = vBox.getChildren();
        ((ImageView)allElements.get(0)).setImage(new Image(userImageLink));
        ((Label)allElements.get(1)).setText(userName);
        ((Label)allElements.get(2)).setText(profileDescription);
        ((Button)allElements.get(3)).setOnAction(b->closeProfile());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(anchor, null));
    }
    public void openProfile() {
        stage.show();
    }
    public void closeProfile() {
        stage.close();
    }
}
