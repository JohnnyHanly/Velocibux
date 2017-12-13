package teamvelociraptor.assignment4.models;

/**
 * Created by ericgroom on 12/12/17.
 *
 */

public class Friend {
    private String uuid;
    private String displayName;
    private String email;
    private String imageUrl;

    public Friend() {
    }

    public Friend(User user) {
        uuid = user.getUuid();
        displayName = user.getDisplayName();
        email = user.getEmail();
        imageUrl = user.getImageUrl();
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
