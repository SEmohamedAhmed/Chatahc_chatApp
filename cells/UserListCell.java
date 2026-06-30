package cells;

import Chatahc.ChatRoom;
import Chatahc.Message;
import Chatahc.User;
import GUI.ProfileDescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;
import static GUI.HomeController.HomeUser;
import static GUI.Main.app;
import static GUI.Main.utilities;

public class UserListCell {
    private String userName;
    private String lastMessage;
    private String time;
    private String notificationsNumber;
    private Image userImage;
    private ObservableList<Message> messagesList;
    private ChatRoom chatRoom;
    private User curFriend;
    private ProfileDescription userProfileDescription;

    public UserListCell(User user, ChatRoom chatRoom) throws SQLException {
        this.userName = user.getUsername();
        this.lastMessage = chatRoom.getLastMessageSent().getMessageText();
        this.time = chatRoom.getLastMessageSent().getTime();
        this.userImage = new Image(user.getUserImageLink());
        this.notificationsNumber = String.valueOf(chatRoom.getNumberOfUnreadMessagesForCurrentUser());
        this.messagesList = FXCollections.observableArrayList(chatRoom.getMessageList());
        this.chatRoom=chatRoom;
    }
    public UserListCell(ChatRoom chatRoom,User user) throws SQLException {
        this.userName = user.getUsername();
        this.lastMessage = "";
        this.time = "";
        this.notificationsNumber ="0";
        this.userImage = new Image(user.getUserImageLink());   //Default Image For Any User
        this.messagesList = FXCollections.observableArrayList();
        this.chatRoom=chatRoom;
    }
    public UserListCell(ChatRoom chatRoom,boolean notEmpty) throws SQLException {
        this.userName = chatRoom.getName();
        lastMessage = chatRoom.getLastMessageSent().getMessageText();
        notificationsNumber = "0";
        time = chatRoom.getLastMessageSent().getTime();
        userImage = new Image(chatRoom.getChatroomImageLink());
        this.messagesList = FXCollections.observableArrayList(chatRoom.getMessageList());
        this.chatRoom = chatRoom;
    }
    public UserListCell(ChatRoom chatRoom) {
        userName = chatRoom.getName();
        lastMessage="";
        notificationsNumber="0";
        time="";
        userImage=new Image(chatRoom.getChatroomImageLink());
        this.messagesList = FXCollections.observableArrayList();
        this.chatRoom = chatRoom;
    }
    public UserListCell(User user) {
        userName = user.getUsername();
        this.lastMessage = "";
        this.time = "";
        this.userImage = new Image(user.getUserImageLink());
        this.notificationsNumber = "0";
    }

    public ProfileDescription getUserProfileDescription() {
        return userProfileDescription;
    }
    public void setUserProfileDescription(ProfileDescription userProfileDescription) {
        this.userProfileDescription = userProfileDescription;
    }
    public Image getUserImage() {
        return userImage;
    }
    public void setUserImage(Image userImage) {
        this.userImage = userImage;
    }
    public ChatRoom getChatRoom() {
        return chatRoom;
    }
    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
    public ObservableList<Message> getMessagesList() {
        return messagesList;
    }
    public void setMessagesList(ObservableList<Message> messagesList) {
        this.messagesList = messagesList;
    }
    public String getNotificationsNumber() {
        return notificationsNumber;
    }
    public void setNotificationsNumber(String notificationsNumber) {
        this.notificationsNumber=notificationsNumber;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Image getAvatarImage() {
        return userImage;
    }
    public void setAvatarImage(Image avatarImage) {
        this.userImage = avatarImage;
    }
    public boolean isPrivate() throws SQLException {
       return app.isPrivate(chatRoom.getId());
    }
    public User getCurFriend() {
        return curFriend;
    }
    public void setCurFriend(User curFriend) {
        this.curFriend = curFriend;
    }
    public void profileVisibility() throws SQLException, IOException {
        if(isPrivate()) {
            editListCellPrivacy();
        }
        else {
            userProfileDescription = new ProfileDescription(getChatRoom().getChatroomImageLink(), getUserName(), "");
        }
    }
    public void editListCellPrivacy() throws SQLException, IOException {
        if(curFriend == null)
            curFriend = utilities.getFriendUser(getChatRoom());
        String testIsFriend = app.getFriendName(HomeUser.getId(), curFriend.getId());
        String testIsFriendV2 = app.getFriendName(curFriend.getId(),HomeUser.getId());
        userName = (testIsFriend == null ? curFriend.getPhoneNumber():testIsFriend);
        if(curFriend.isProfileVisibility()) {
            userProfileDescription = new ProfileDescription(curFriend.getUserImageLink(), userName,curFriend.getProfileDesc());
        }
        else {
            if(testIsFriendV2==null) {
                userImage = utilities.getUserDefaultImage();
                userProfileDescription = new ProfileDescription(userImage.getUrl(),userName,"");
            }
            else {
                userProfileDescription = new ProfileDescription(curFriend.getUserImageLink(), userName,curFriend.getProfileDesc());
            }
        }
    }
}