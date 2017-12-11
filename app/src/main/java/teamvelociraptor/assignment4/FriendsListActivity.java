package teamvelociraptor.assignment4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import teamvelociraptor.assignment4.models.User;



public class FriendsListActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());
    DatabaseReference mFriendsRef = mUserRef.child("friends");

    User userObj;
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
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userObj = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        friendsList = findViewById(R.id.friends_list);
        addFriends();
        displayFriends();
    }

    public void displayFriends() {
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setQuery(mFriendsRef, User.class)
                .setLayout(R.layout.friends_list)
                .build();
        FirebaseListAdapter<User> adapter = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(View v, User model, int position) {
                TextView username = v.findViewById(R.id.username);
                TextView uuid = v.findViewById(R.id.uuid);
                username.setText(model.getDisplayName());
                uuid.setText(model.getUuid());
            }
        };
        friendsList.setAdapter(adapter);
        adapter.startListening();
    }

    private void addFriends() {
        User f1 = new User();
        User f2 = new User();
        f1.setDisplayName("friend 1");
        f2.setDisplayName("friend 2");
        f1.setUuid("uuid1");
        f2.setUuid("uuid2");
        List<User> friends = new ArrayList<>();
        friends.add(f1);
        friends.add(f2);
        mFriendsRef.setValue(friends);
    }

}
