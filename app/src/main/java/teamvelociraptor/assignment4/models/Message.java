package teamvelociraptor.assignment4.models;

import java.util.Date;

public class Message {
    private String uid;
    private Date timestamp;
    private String text;
    private String displayName;

    public Message(String displayName, String uid, String text) {
        this.displayName=displayName;
        this.uid=uid;
        this.text=text;
        this.timestamp= new Date();
    }

    public Message(){

    }

    public String getUid() {
        return uid;
    }
    public String getDisplayName(){
        return displayName;
    }

    public void setId(String id) {
        this.uid = uid;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
