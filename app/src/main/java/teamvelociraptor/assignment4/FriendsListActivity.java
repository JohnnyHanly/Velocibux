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
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamvelociraptor.assignment4.models.User;

public class FriendsListActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUsersRef = mRootRef.child("users");
    ListView friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        friendsList = findViewById(R.id.friends_list);
        // fillTestData();
        displayUsers();
    }

    public void displayUsers() {
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setQuery(mUsersRef, User.class)
                .setLayout(R.layout.friends_list)
                .build();
        FirebaseListAdapter<User> adapter = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(View v, User model, int position) {
                TextView username = v.findViewById(R.id.username);
                TextView uuid = v.findViewById(R.id.uuid);
                username.setText(model.getUsername());
                uuid.setText(model.getUuid());
            }
        };
        friendsList.setAdapter(adapter);
        adapter.startListening();
    }

    public void fillTestData() {
        Map<String, User> users = new HashMap<>();
        users.put("1", new User("1", "test", 0));
        users.put("2", new User("2", "asdf", 0));
        users.put("3", new User("3", "fdsa", 0));
        User.makeFriends(users.get("1"), users.get("2"));
        User.makeFriends(users.get("2"), users.get("3"));
        mUsersRef.setValue(users);
    }

}
