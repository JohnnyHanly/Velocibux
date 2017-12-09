package teamvelociraptor.assignment4.models;



public class Conversation {
    private String senderID;
    private String senderUsername;
    private String text;
    private long timestamp;

public Conversation(String senderID, String senderUsername, String text, long timestamp){
    this.senderID= senderID;
    this.senderUsername= senderUsername;
    this.text=text;
    this.timestamp= timestamp;
}
    public Conversation(){}

    public String setSenderID(){
        return  this.senderID= senderID;
    }
    public String setSenderusername(){
        return this.senderUsername=senderUsername;
    }
    public String setText(){
        return this.text= text;
    }

    public String getSenderID(){
        return senderID;
    }
    public String getSenderUsername(){
        return senderUsername;
    }
    public String getText(){
        return text;
    }
    public long getTimestamp(){
        return timestamp;
    }

}
