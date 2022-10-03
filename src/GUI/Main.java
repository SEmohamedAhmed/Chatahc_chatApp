package GUI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import Chatahc.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static Utilities utilities;
    public static App app;
    static {
        try {
            utilities = new Utilities();
            app = new App();
        } catch (Exception e) {
            System.out.println("static Main failed");
            e.getMessage();
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Parent loginScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../UI/Login.fxml")));
        prepareFirstScene(stage, loginScene);
    }
    public static void main(String[] args) throws IOException, SQLException {
        launch(args);
    }
    private void prepareFirstScene(Stage stage, Parent firstScene) {
        Scene scene = new Scene(firstScene, 1538, 785);
        stage.getIcons().add(new Image("resources/img/Chatahc.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(winEvent->{
            winEvent.consume();
            utilities.closeRequest(stage);
        });
    }
}
