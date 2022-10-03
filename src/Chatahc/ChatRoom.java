package Chatahc;

import java.util.ArrayList;
import java.util.LinkedList;

public class ChatRoom implements Comparable<ChatRoom>{
    private final int id;
    private String name;
    private ArrayList<User> userList;
    private LinkedList<Message> messageList;
    private Message lastMessageSent;
    private String chatroomImageLink;
    private String dateOfGroupCreation;
    private String timeOfGroupCreation;
    private int numberOfUnreadMessagesForCurrentUser;
    public boolean flag = false;
    public ChatRoom(int id, String name) {
        this.id = id;
        this.name = name;
        userList = new ArrayList<User>();
    }
    public ChatRoom(int id, String name, String dateOfGroupCreation, String timeOfGroupCreation,
                    String chatroomImageLink){
        this.id = id;
        this.name=  name;
        this.dateOfGroupCreation = dateOfGroupCreation;
        this.timeOfGroupCreation = timeOfGroupCreation;
        this.chatroomImageLink = chatroomImageLink;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public Message getLastMessageSent() {
        return lastMessageSent;
    }

    public void setLastMessageSent(Message lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }

    public LinkedList<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(LinkedList<Message> messageList) {
        this.messageList = messageList;
    }

    public String getChatroomImageLink() {
        return chatroomImageLink;
    }

    public void setChatroomImageLink(String chatroomImageLink) {
        this.chatroomImageLink = chatroomImageLink;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getDateOfGroupCreation() {
        return dateOfGroupCreation;
    }

    public void setDateOfGroupCreation(String dateOfGroupCreation) {
        this.dateOfGroupCreation = dateOfGroupCreation;
    }

    public String getTimeOfGroupCreation() {
        return timeOfGroupCreation;
    }

    public void setTimeOfGroupCreation(String timeOfGroupCreation) {
        this.timeOfGroupCreation = timeOfGroupCreation;
    }

    public int getNumberOfUnreadMessagesForCurrentUser() {
        return numberOfUnreadMessagesForCurrentUser;
    }

    public void setNumberOfUnreadMessagesForCurrentUser(int numberOfUnreadMessagesForCurrentUser) {
        this.numberOfUnreadMessagesForCurrentUser = numberOfUnreadMessagesForCurrentUser;
    }
    @Override
    public int compareTo(ChatRoom o) {
        if (this.lastMessageSent.getDateTime().after(o.lastMessageSent.getDateTime()))
            return -1;
        else if ((this.lastMessageSent.getDateTime().before(o.lastMessageSent.getDateTime())))
            return 1;
        else
            return 0;
    }
}