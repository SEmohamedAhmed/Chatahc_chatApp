package GUI;

import Chatahc.Message;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import java.io.IOException;
import java.sql.SQLException;
import static GUI.HomeController.*;
import static GUI.Main.app;

public class MessageOptions extends CustomAlert{
    private Button deleteMessage = new Button("Delete Message");
    public MessageOptions(String Header, String Content) throws IOException {
        super(Header, Content);
        super.stage.setX(830);
        super.stage.setY(300);
        ObservableList<Node>msgAnchor=super.anchor.getChildren();
        super.setContentAlignment();
        editButton(deleteMessage);
        msgAnchor.add(deleteMessage);
        editButtonsPositions(msgAnchor);
        deleteMessage.setOnAction(del-> {
            closeAlert();
            try {
                deleteMessage();
            }
            catch (Exception exception) {
                System.out.println("Message deletion failed");
                exception.getMessage();
            }});
    }
    public void deleteMessage() throws SQLException {
        Message deletedMessage = currentSelectedChatRoom.getMessagesList().remove(selectedMessage);
        int sz = currentSelectedChatRoom.getMessagesList().size();
        app.deleteMessage(deletedMessage.getId());
        if( (sz-1 > -1) && selectedMessage == sz) // update last msg in the listCell
         {
            int curChatRoom = usersListViewClone.getSelectionModel().getSelectedIndex();
            Message toBeShowed = currentSelectedChatRoom.getMessagesList().get(sz-1);
            usersListViewClone.getItems().get(curChatRoom).setLastMessage(toBeShowed.getMessageText());
            usersListViewClone.getItems().get(curChatRoom).setTime(toBeShowed.getTime());
            usersListViewClone.refresh();
        }
    }
    private void editButton(Button button) {
        button.setPrefHeight(38);
        button.setPrefWidth(130);
        button.setBackground(Background.fill(Paint.valueOf("#333333")));
        button.setTextFill(Paint.valueOf("ffffff"));
        button.setFont(new Font("arial",15));
    }
    private void editButtonsPositions(ObservableList<Node> nodes){
        nodes.get(2).setLayoutX(50);
        nodes.get(2).setLayoutY(125);
        nodes.get(3).setLayoutX(155);
        nodes.get(3).setLayoutY(125);
    }
}
