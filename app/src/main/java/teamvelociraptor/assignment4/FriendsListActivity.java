package teamvelociraptor.assignment4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamvelociraptor.assignment4.models.User;

public class FriendsListActivity extends AppCompatActivity {
    private static FirebaseDatabase database;
    private static DatabaseReference usersRef;
    private static ListView friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        friendsList = findViewById(R.id.friends_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                database = FirebaseDatabase.getInstance();
                usersRef = database.getReference("users");
                Map<String, User> users = new HashMap<>();
                users.put("1", new User("1", "test"));
                users.put("2", new User("2", "asdf"));
                User.makeFriends(users.get("1"), users.get("2"));
                usersRef.setValue(users);
            }
        }).start();


    }

}
