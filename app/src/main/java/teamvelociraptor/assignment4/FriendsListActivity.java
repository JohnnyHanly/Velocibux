package teamvelociraptor.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamvelociraptor.assignment4.models.User;


public class FriendsListActivity extends AppCompatActivity {

    static final int ADD_FRIEND_REQUEST = 2;

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
                startActivityForResult(intent, ADD_FRIEND_REQUEST);
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
                TextView uuid = v.findViewById(R.id.uuid);
                ImageView profileImage = v.findViewById(R.id.profileImage);

                username.setText(model.getDisplayName());
                uuid.setText(model.getUuid());
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
                    mRootRef.child("users").child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User newFriend = dataSnapshot.getValue(User.class);
                            userObj.getFriends().add(newFriend);
                            mUserRef.setValue(userObj);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (resultCode == QRCameraActivity.RESULT_CANCELED) {
                    //Write your code if there's no result
                }
            }
    }

    private void addFriends() {
        User f1 = new User();
        User f2 = new User();
        f1.setDisplayName("velociraptor");
        f2.setDisplayName("bucks");
        f1.setUuid("dino");
        f2.setUuid("money");
        f1.setImageUrl("http://toys-zoom.worldwideshoppingmall.co.uk/schleich-velociraptor-2012.jpg");
        f2.setImageUrl("http://www-images.theonering.org/torwp/wp-content/uploads/2009/09/money.jpg");
        List<User> friends = new ArrayList<>();
        friends.add(f1);
        friends.add(f2);
        mFriendsRef.setValue(friends);
    }



}
