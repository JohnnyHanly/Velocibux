package teamvelociraptor.assignment4.models;

import java.util.List;

/**
 * Created by ericgroom on 12/8/17.
 */

public class Conversation {

    private String id;
    private List<Message> messageList;

    public Conversation(String id,List<Message>messageList) {
        this.id=id;
        this.messageList=messageList;

    }
    public Conversation(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messageList;
    }
    public void addMessages(Message message){
        this.messageList.add(message);
    }

    public void setMessages(List<Message> messageList) {
        this.messageList = messageList;
    }
}
