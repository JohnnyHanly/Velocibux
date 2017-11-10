package teamvelociraptor.assignment4;
import java.util.Date;

public class Message {
    private String username;
    private String id;
    private long timestamp;
    private String text;
    private String name;


public Message(){

}
public Message(String name, String id, String text, long timestamp){
    this.name=name;
    this.id=id;
    this.text=text;
    this.timestamp= new Date().getTime();
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
