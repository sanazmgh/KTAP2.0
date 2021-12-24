package apps.page.messenger.chatPane.message.model;

import db.Context;
import models.Model;

import java.io.File;

public class Message extends Model {
    private final int senderId;
    private String text;
    private final String extraInfo;
    private final boolean forwarded;
    private String imagePath;

    public Message(int userId , String text , String imagePath , boolean forwarded , String extraInfo)
    {
        this.senderId = userId;
        this.text = text;
        this.forwarded = forwarded;
        this.extraInfo = extraInfo;
        this.imagePath = imagePath;
        this.deleted = false;
        this.id = Context.getContext().messagesDB.lastID()+1;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public boolean isForwarded() {
        return forwarded;
    }

    public String getImagePath() {
        if(imagePath.equals(""))
            return imagePath;

        File file = new File(imagePath);
        if(!file.exists())
            return "";

        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
