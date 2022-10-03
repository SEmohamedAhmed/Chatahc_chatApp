package Chatahc;

import java.sql.Timestamp;

import static GUI.Main.utilities;

public class Message {
    private  int id, senderId, chatId;
    private  String messageText;
    private String date, time;
    private Timestamp dateTime =Timestamp.valueOf("2000-01-01 00:00:00");
    private boolean seenStatus;
    public Message(){
        messageText="";
        time="";
        date="";
    }
    public Message(int id, int senderId, int chatId, String messageText, String date, String time, boolean seenStatus) {
        this.id = id;
        this.senderId = senderId;
        this.chatId = chatId;
        this.messageText = messageText;
        this.date = date;
        this.time = time;
        this.seenStatus = seenStatus;
        this.dateTime = dateTime;
    }
    public Message(int id, int senderId, int chatId, String messageText, String date, String time, boolean seenStatus, Timestamp dateTime) {
        this.id = id;
        this.senderId = senderId;
        this.chatId = chatId;
        this.messageText = messageText;
        this.date = date;
        this.time = time;
        this.seenStatus = seenStatus;
        this.dateTime = dateTime;
    }
    public Message(int senderId, String messageText, String time){
        this.senderId = senderId;
        this.messageText = messageText;
        this.time = time;
    }
    public int getId() {
        return id;
    }
    public int getSenderId() {
        return senderId;
    }
    public int getChatId() {
        return chatId;
    }
    public String getMessageText() {
        return messageText;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return utilities.getTime(time,5);
    }
    public boolean isSeenStatus() {
        return seenStatus;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setSeenStatus(boolean seenStatus) {
        this.seenStatus = seenStatus;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
    public Timestamp getDateTime() {
        return dateTime;
    }
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}