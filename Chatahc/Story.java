package Chatahc;

import java.sql.Date;
import java.sql.Time;

public class Story {
    private final int storyUploaderId;
    private String storyText;
    private final Date storyUploadedDate;
    private final Time storyUploadedTime;
    private boolean isImage;
    public Story(int storyUploaderId, String storyText, Date storyUploadedDate, Time storyUploadedTime,boolean isImage) {
        this.storyUploaderId = storyUploaderId;
        this.storyText = storyText;
        this.storyUploadedTime = storyUploadedTime;
        this.storyUploadedDate = storyUploadedDate;
        this.isImage=isImage;
    }
    public int getStoryUploaderId() {
        return storyUploaderId;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public String getStoryText() {
        return storyText;
    }

    public Time getStoryUploadedTime() {
        return storyUploadedTime;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public Date getStoryUploadedDate() {
        return storyUploadedDate;
    }


}