package teamvelociraptor.assignment4.models;

import java.util.Date;

public class Message {
    private String id;
    private Date timestamp;
    private String text;

    public Message(String displayName, String uid, String s) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
