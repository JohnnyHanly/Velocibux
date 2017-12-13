package teamvelociraptor.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import teamvelociraptor.assignment4.models.Conversation;
import teamvelociraptor.assignment4.models.Message;
import teamvelociraptor.assignment4.models.User;

public class MessagingActivity extends AppCompatActivity {
    FloatingActionButton sendMessage;
    FloatingActionButton paymentMessage;
    RelativeLayout activity_messaging;
    private EditText input;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mMessageRef;

    String recipientID;

    DatabaseReference mUserRef = ref.child("users").child(mUser.getUid());
    DatabaseReference mConvoRef = mUserRef.child("conversations");
    DatabaseReference mRecipientRef;
    Message messageObj;
    List<Message> messageList;
    User userObj;
    User recipientObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        activity_messaging = findViewById(R.id.activity_messaging);
        sendMessage = findViewById(R.id.sendButton);
        paymentMessage = findViewById(R.id.paymentButton);
        input = (EditText) findViewById(R.id.message_input);

    }

    @Override
    protected void onStart() {
        super.onStart();
      recipientID= getIntent().getStringExtra("uuid");
      mMessageRef= mConvoRef.child(recipientID).child("messages");
      mRecipientRef=ref.child("users").child(recipientID);

        ref.child("users").child(recipientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipientObj = dataSnapshot.getValue(User.class);
                setTitle(recipientObj.getDisplayName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userObj = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    GenericTypeIndicator<List<Message>>t= new GenericTypeIndicator<List<Message>>(){};
                    messageList = dataSnapshot.getValue(t);
                    if (messageList==null){
                        messageList= new ArrayList<>();

                    }
                } else {

                    messageList= new ArrayList<>();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessagingActivity.this, "Check the firebase database, this finally works!", Toast.LENGTH_SHORT).show();
                Message message = new Message(userObj.getDisplayName(), userObj.getUuid(), input.getText().toString());
                send(message);
                input.setText("");
                // diplayMessages();
            }

        });
        paymentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessagingActivity.this, "You pressed the payment button", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MessagingActivity.this, PaymentActivity.class));
            }
        });
        diplayMessages();
    }


    private void diplayMessages() {


        FirebaseListOptions<Message> messageOptions = new FirebaseListOptions.Builder<Message>()
                .setLayout(R.layout.message_list)
                .setQuery(mMessageRef, Message.class).build();

        ListView readMessageList = (ListView) findViewById(R.id.list_of_messages);

        FirebaseListAdapter<Message> messageAdapter = new FirebaseListAdapter<Message>(messageOptions) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView text = (TextView) v.findViewById(R.id.messageView);
                TextView username = (TextView) v.findViewById(R.id.contactView);
                TextView timestamp = (TextView) v.findViewById(R.id.timestampView);

                text.setText(model.getText());
                username.setText(model.getDisplayName());
                timestamp.setText(DateFormat.format("dd (HH:mm:ss)", model.getTimestamp()));

            }
        };

        readMessageList.setAdapter(messageAdapter);
        messageAdapter.startListening();

    }

    public void send(Message message){
        messageList.add(message);

        DatabaseReference ref1= mUserRef.child("conversations").child(recipientObj.getUuid()).child("messages");
        DatabaseReference ref2= mRecipientRef.child("conversations").child(userObj.getUuid()).child("messages");
        ref1.setValue(messageList);
        ref2.setValue(messageList);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return AppUtils.dropDownChangeActivity(item, MessagingActivity.this);
    }


}


