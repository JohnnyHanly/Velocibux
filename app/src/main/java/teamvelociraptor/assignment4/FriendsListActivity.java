package teamvelociraptor.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import teamvelociraptor.assignment4.models.Friend;
import teamvelociraptor.assignment4.models.Message;
import teamvelociraptor.assignment4.models.User;


public class FriendsListActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());
    DatabaseReference mFriendsRef = mUserRef.child("friends");

    User userObj;
    ListView friendsList;
    private FloatingActionButton addFriendsFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
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
        addFriendsFab = findViewById(R.id.addFriendsFab);
        addFriendsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FriendsListActivity.this, QRCameraActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        friendsList = findViewById(R.id.friends_list);
        // addFriends();
        displayFriends();
    }

    public void displayFriends() {
        FirebaseListOptions<User> options = new FirebaseListOptions.Builder<User>()
                .setQuery(mFriendsRef, User.class)
                .setLayout(R.layout.friends_list)
                .build();
        FirebaseListAdapter<User> adapter = new FirebaseListAdapter<User>(options) {
            @Override
            protected void populateView(View v, final User model, int position) {
                TextView username = v.findViewById(R.id.username);
                final TextView message = v.findViewById(R.id.message);
                ImageView profileImage = v.findViewById(R.id.profileImage);

                username.setText(model.getDisplayName());
                DatabaseReference messagesRef = mUserRef.child("conversations").child(model.getUuid()).child("messages");
                messagesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            GenericTypeIndicator<List<Message>> t = new GenericTypeIndicator<List<Message>>() {};
                            List<Message> messages= dataSnapshot.getValue(t);
                            message.setText(messages.get(messages.size() - 1).getText());
                        } else {
                            message.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                new GetUserProfileImage(model, profileImage).execute();
//                profileImage.setImageBitmap(image.execute(params));
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(FriendsListActivity.this, MessagingActivity.class);
                        intent.putExtra("uuid", model.getUuid());
                        startActivity(intent);
                    }
                });
            }
        };
        friendsList.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return AppUtils.dropDownChangeActivity(item, FriendsListActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
                if (resultCode == QRCameraActivity.RESULT_OK) {
                    String uuid = data.getStringExtra("result");
                    Toast.makeText(FriendsListActivity.this, uuid, Toast.LENGTH_LONG);
                    final DatabaseReference friendRef = mRootRef.child("users").child(uuid);
                    friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User otherUser = dataSnapshot.getValue(User.class);
                            Friend newFriend = new Friend(otherUser);
                            if (userObj.getFriends() == null) userObj.setFriends(new ArrayList<Friend>());
                            userObj.getFriends().add(newFriend);
                            mUserRef.setValue(userObj);
                            if (otherUser.getFriends() == null) otherUser.setFriends(new ArrayList<Friend>());
                            otherUser.getFriends().add(new Friend(userObj));
                            friendRef.setValue(otherUser);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(FriendsListActivity.this, "Error adding user", Toast.LENGTH_LONG);
                        }
                    });
                }
                if (resultCode == QRCameraActivity.RESULT_CANCELED) {
                    Toast.makeText(FriendsListActivity.this, "QR Code Scanner Error", Toast.LENGTH_LONG);
                }
            }
    }

}
