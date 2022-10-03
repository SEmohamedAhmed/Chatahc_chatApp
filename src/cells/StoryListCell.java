package cells;
import Chatahc.Story;
import Chatahc.User;
import java.util.ArrayList;
import java.util.LinkedList;

public class StoryListCell {
    private User user;
    private String userName;
    private String userImage;
    private LinkedList<Story> storiesUserList;
    public StoryListCell(User user,String contactName) {
        userName = contactName;
        this.user=user;
        this.userImage = user.getUserImageLink();
    }
    public StoryListCell(User user) {
        this.user=user;
        this.userImage = user.getUserImageLink();
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserImage() {
        return userImage;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
    public LinkedList<Story> getStoriesUserList() {
        return storiesUserList;
    }
    public void setStoriesUserList(LinkedList<Story> storiesUserList) {
        this.storiesUserList = storiesUserList;
    }
}
