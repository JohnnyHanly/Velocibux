package teamvelociraptor.assignment4.models;
import java.util.Date;

public class Message {
    private String username;
    private String id;
    private long timestamp;
    private String text;
    private String name;



public Message(String name, String id, String text){
    this.name=name;
    this.id=id;
    this.text=text;
}
    public Message(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
