package GUI;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class CustomAlert {
    protected Stage stage = new Stage();
    protected AnchorPane anchor;        //for a child also
    protected ObservableList<Node> anchorElements;
    public CustomAlert(String Header, String Content) throws IOException {
        anchor = FXMLLoader.load(getClass().getResource("../UI/Alert.fxml"));
        anchorElements = anchor.getChildren();
        ((Label) anchorElements.get(0)).setText(Header);
        ((Label) anchorElements.get(1)).setText(Content);
        ((Button) anchorElements.get(2)).setOnAction(e -> closeAlert());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(anchor, null));
    }
    public void setContentAlignment() {
        ((Label) anchorElements.get(1)).setAlignment(Pos.CENTER_LEFT);
    }
    public void closeAlert() {
        stage.close();
    }
    public void openAlert() {
        stage.show();
    }
}
