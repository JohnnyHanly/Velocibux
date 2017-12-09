package teamvelociraptor.assignment4.models;

import java.util.List;

/**
 * Created by ericgroom on 12/8/17.
 */

class Conversation {

    private String id;
    private List<Message> messages;

    public Conversation() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
