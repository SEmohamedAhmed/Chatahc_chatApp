package GUI;
import cells.MessageCustomCell;
import cells.UserCustomCell;
import cells.UserListCell;
import Chatahc.ChatRoom;
import Chatahc.Message;
import Chatahc.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import static GUI.LoginController.uLogin;
import static GUI.Main.app;
import static GUI.Main.utilities;
import static GUI.SignUpController.uSignup;
public class HomeController implements Initializable {
    @FXML private Label chatRoomName;
    @FXML private Label lastSeen;
    @FXML private ListView<UserListCell> usersListView;
    @FXML private ListView<Message> messagesListView;
    @FXML private TextField messageTextField;
    @FXML private ImageView userHomeImage;
    public static ListView<UserListCell> usersListViewClone;
    public static User HomeUser;
    public static int selectedMessage;
    private static Message selectedMessageObj;
    private ObservableList<UserListCell> usersList = FXCollections.observableArrayList();
    public static UserListCell currentSelectedChatRoom;         //current opened chatroom
    public HomeController() {
        if (uLogin != null)
            HomeUser = uLogin;
        else if (uSignup != null)
            HomeUser = uSignup;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            prepareHomeScene();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void prepareHomeScene() throws SQLException, IOException {
        int userId = app.getUserIdFromUsername(HomeUser.getUsername());
        List<ChatRoom> chatRooms = app.expandConnectionChats(userId);
        LinkedList<UserListCell> allUserListCell = new LinkedList<>();    //delete and insert faster than arraylist
        for (int i = 0; i < chatRooms.size(); ++i) {
            ChatRoom tempChatRoom = chatRooms.get(i);
            UserListCell userListCell = prepareChatRoomListCell(tempChatRoom);
            int cnt = app.unseenMessage(HomeUser.getId(), tempChatRoom.getId());
            userListCell.setNotificationsNumber(String.valueOf(cnt));
            allUserListCell.addLast(userListCell);
        }
        usersList.addAll(allUserListCell);  // takes any collection as a parameter
        usersListView.setItems(usersList);
        prepareHomeListsGui();
    }
    private UserListCell prepareChatRoomListCell(ChatRoom tempChatRoom) throws SQLException, IOException {
        UserListCell returnedListCell;
        boolean chatType =app.isPrivate(tempChatRoom.getId());
        if(chatType)        //privateChat
        {
            User friendCell = utilities.getFriendUser(tempChatRoom);
            if(tempChatRoom.getMessageList().isEmpty())
                returnedListCell = new UserListCell(tempChatRoom ,friendCell);
            else
                returnedListCell = new UserListCell(friendCell, tempChatRoom);
        }
        else            // groupChat
        {
            if(tempChatRoom.getMessageList().isEmpty())
                returnedListCell = new UserListCell(tempChatRoom);
            else
                returnedListCell = new UserListCell(tempChatRoom,true);
        }
        returnedListCell.profileVisibility();       // Applay visibility here
        return returnedListCell;
    }
    private void prepareHomeListsGui() throws SQLException {
        userHomeImage.setImage(new Image(app.getUser(HomeUser.getId()).getUserImageLink()));
        usersListView.setCellFactory(lv -> new UserCustomCell() {
            {
                lv.setOnMouseClicked(mouseEvent -> {
                    currentSelectedChatRoom = lv.getSelectionModel().getSelectedItem();
                });
            }
        });
        messagesListView.setCellFactory(lv -> new MessageCustomCell() {
            {
                lv.setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getClickCount() == 2) {
                        selectedMessage = lv.getSelectionModel().getSelectedIndex();
                        selectedMessageObj = lv.getSelectionModel().getSelectedItem();
                        try {
                            doubleClickEvent(selectedMessageObj);     //message status
                        } catch (SQLException dd) {
                            System.out.println(dd.getMessage());
                        } catch (Exception exception) {
                            exception.getMessage();
                        }
                    }
                });
            }
        });
        usersListView.getSelectionModel().selectedItemProperty().addListener(l -> {
            UserListCell selectedChatRoom = usersListView.getSelectionModel().getSelectedItem();
            lastSeen.setText("");       //reset label text
            if (selectedChatRoom != null) {
                messageTextField.setVisible(true);
                currentSelectedChatRoom = selectedChatRoom;
                int selectedChatId = selectedChatRoom.getChatRoom().getId();
                chatRoomName.setText(currentSelectedChatRoom.getUserName());
                chatRoomName.setVisible(true);
                lastSeen.setVisible(true);
                currentSelectedChatRoom.setNotificationsNumber("0");
                messagesListView.setItems(currentSelectedChatRoom.getMessagesList());
                messagesListView.scrollTo(currentSelectedChatRoom.getMessagesList().size());
                chatRoomName.setText(currentSelectedChatRoom.getUserName());
                try {
                    app.openChat(HomeUser, selectedChatRoom.getChatRoom().getId());
                    app.setAllMessagesSeenStatus(selectedChatId);
                    if (app.isPrivate(selectedChatId)) {
                        String lastSeenDate = app.lastOpenChatDateTime(selectedChatId, utilities.getFriendUser(selectedChatRoom.getChatRoom()).getId());
                        if (lastSeenDate != null)
                            lastSeen.setText("Last seen at : " + utilities.getTime(lastSeenDate, lastSeenDate.length() - 3));
                    }
                } catch (Exception e) {
                    System.out.println("OpenChatFailed");
                    e.printStackTrace();
                }
            }
        });
        usersListViewClone = usersListView;     // to control listview outside controllerClass
    }
    public void sendMessage(ActionEvent actionEvent) throws SQLException {
        String msgText = messageTextField.getText();
        if (!msgText.equals("")) {
            msgText = utilities.prepareString(msgText, 290);
            app.sendMessage(HomeUser.getId(), currentSelectedChatRoom.getChatRoom().getId(), msgText);
            currentSelectedChatRoom.getMessagesList().add(app.getLastMessage(currentSelectedChatRoom.getChatRoom().getId()));
            updateAndClearList();
        }
    }
    public void updateAndClearList() throws SQLException {
        Message lastMessage = app.getLastMessage(currentSelectedChatRoom.getChatRoom().getId());
        messagesListView.scrollTo(messagesListView.getItems().size());
        currentSelectedChatRoom.setLastMessage(lastMessage.getMessageText());
        currentSelectedChatRoom.setTime(lastMessage.getTime());
        UserListCell removeAndPutFirst = currentSelectedChatRoom;
        usersListView.getItems().remove(currentSelectedChatRoom);
        usersListView.getItems().add(0, removeAndPutFirst);
        messageTextField.clear();
        usersListView.getSelectionModel().selectFirst();
        usersListView.refresh();
    }
    public void doubleClickEvent(Message message) throws IOException, SQLException {
        if (message.getSenderId() == HomeUser.getId()) {
            String dt = "Message sent in: "+message.getDate() +"\tat : "+message.getTime();
            MessageOptions messageStatus;
            if (app.getSeenStatus(message.getId())) {
                messageStatus = new MessageOptions("Message Options", "Seen\n"+dt);
            } else {
                messageStatus = new MessageOptions("Message Status", "Not seen\n"+dt);
            }
            messageStatus.openAlert();
        }
    }
    public void gotoStoryPage(MouseEvent mouseEvent) throws IOException {
        utilities.gotoStoryView(mouseEvent);
    }
    public void gotoOptions(MouseEvent mouseEvent) throws IOException {
        utilities.gotoOptions(mouseEvent);
    }
    public void gotoProfileDesc(MouseEvent mouseEvent) throws IOException, SQLException {
        ProfileDescription profileDescription;
        String node = mouseEvent.getSource().getClass().getName();
        UserListCell userListCell = /*usersListView.getSelectionModel().getSelectedItem()*/currentSelectedChatRoom;
        if (node.contains("Label")) {
            profileDescription = userListCell.getUserProfileDescription();
        }
        else {
            User updateUser = app.getUser(HomeUser.getId());
            profileDescription = new ProfileDescription(updateUser.getUserImageLink(), updateUser.getUsername(), updateUser.getProfileDesc());
        }
             profileDescription.openProfile();
        }
}