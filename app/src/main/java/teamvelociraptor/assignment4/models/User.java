package teamvelociraptor.assignment4.models;

import java.util.List;
import java.util.Map;

/**
 * Created by ericgroom on 11/26/17.
 */

public class User {
    private String uuid;
    private String displayName;
    private String email;
    private List<Friend> friends;
    private double balance;
    private String imageUrl;
    private List<Transaction> transactions;
    private Map<String, Conversation> conversations;

    public User() {}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<String, Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Map<String, Conversation> conversations) {
        this.conversations = conversations;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        balance = balance * 100;
        balance = Math.round(balance);
        balance = balance / 100;
        this.balance = balance;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
