package teamvelociraptor.assignment4.models;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ericgroom on 11/26/17.
 */

public class User {
    private String uuid;
    private String username;
    private List<String> friends;

    public User() {}

    public User(String uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.friends = new ArrayList<>();
    }

    public void addFriend(String uuid) {
        friends.add(uuid);
    }

    public void addFriend(User user) {
        friends.add(user.uuid);
    }



    public static void makeFriends(User user1, User user2) {
        user1.friends.add(user2.uuid);
        user2.friends.add(user1.uuid);
    }

    public String getUuid() {
        return uuid;
    }


    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}

