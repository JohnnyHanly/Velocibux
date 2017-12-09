package teamvelociraptor.assignment4.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ericgroom on 11/26/17.
 */

public class User {
    private User uuid;
    private String displayName;
    private String email;
    private List<User> friends;
    private Map<String, Transaction> transactions;
    private Map<String, Conversation> conversations;

    public User() {}

    public User getUuid() {
        return uuid;
    }

    public void setUuid(User uuid) {
        this.uuid = uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public Map<String, Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<String, Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<String, Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, Conversation> conversations) {
        this.conversations = conversations;
    }
}
