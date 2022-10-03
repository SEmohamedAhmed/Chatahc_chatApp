package Chatahc;
import java.util.ArrayList;

public class User {
    private  int id;
    private String username, phoneNumber, password, profileDesc = "default";
    private boolean profileVisibility;
    private int currentChatId;
    private String userImageLink;
    private String lastDateOpened,lastTimeOpened;
    private ArrayList<Story>userStories;
    private ArrayList<User>friendsList = new ArrayList<>();
    public User(){}
    public User(int id, String username, String phoneNumber, String password, String profileDesc, boolean profileVisiblilty,String userImageLink) {
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profileDesc = profileDesc;
        this.profileVisibility = profileVisiblilty;
        this.userImageLink = userImageLink;
    }
    public User(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;

    }
    public User(int id, String username, String password, String phoneNumber){
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public User(int id, String date, String time, int ff){
        this.id = id;
        this.lastDateOpened = date;
        this.lastTimeOpened = time;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public String getProfileDesc() {
        return profileDesc;
    }
    public boolean isProfileVisibility() {
        return profileVisibility;
    }
    public int getCurrentChatId() {
        return currentChatId;
    }
    public String getUserImageLink() {
        return userImageLink;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }
    public void setProfileVisibility(boolean profileVisibility) {
        this.profileVisibility = profileVisibility;
    }
    public void setCurrentChatId(int currentChatId) {
        this.currentChatId = currentChatId;
    }
    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
    }
    public String getLastDateOpened() {
        return lastDateOpened;
    }
    public void setLastDateOpened(String lastDateOpened) {
        this.lastDateOpened = lastDateOpened;
    }
    public String getLastTimeOpened() {
        return lastTimeOpened;
    }
    public void setLastTimeOpened(String lastTimeOpened) {
        this.lastTimeOpened = lastTimeOpened;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<User> getFriendsList() {
        return friendsList;
    }
    public void setFriendsList(ArrayList<User> friendsList) {
        this.friendsList = friendsList;
    }
    public ArrayList<Story> getUserStories() {
        return userStories;
    }
    public void setUserStories(ArrayList<Story> userStories) {
        this.userStories = userStories;
    }
}