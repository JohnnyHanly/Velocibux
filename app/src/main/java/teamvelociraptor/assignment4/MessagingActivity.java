package teamvelociraptor.assignment4;

import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import teamvelociraptor.assignment4.models.Message;
import teamvelociraptor.assignment4.models.User;

public class MessagingActivity extends AppCompatActivity {
    FloatingActionButton sendMessage;
    FloatingActionButton paymentMessage;
    //private FirebaseAuth mAuth;
    RelativeLayout activity_messaging;
    private EditText input;
    private FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef = ref.child("users").child(mUser.getUid());
    DatabaseReference mMessageRef= mUserRef.child("messages");
    Message messageObj;
    User userObj;


    //meow
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        activity_messaging = findViewById(R.id.activity_messaging);
        sendMessage = findViewById(R.id.sendButton);


        paymentMessage = findViewById(R.id.paymentButton);
       // getIntent().getIntExtra("uuid",-1);
        input= (EditText)findViewById(R.id.payment_input);
        //mUser= mAuth.getCurrentUser();
    }
    @Override
            protected void onStart() {
        super.onStart();

mMessageRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        messageObj= dataSnapshot.getValue(Message.class);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
mUserRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        userObj=dataSnapshot.getValue(User.class);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessagingActivity.this, "You pressed the send button", Toast.LENGTH_SHORT).show();
                String test="hello dad";
                Message message= new Message(userObj.getDisplayName(),userObj.getUuid(),test);
                send(message);
                input.setText("");

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
        Query messageQuery = FirebaseDatabase.getInstance().getReference();

        FirebaseListOptions<Message> messageOptions = new FirebaseListOptions.Builder<Message>().setLayout(R.layout.message_list)
                .setQuery(messageQuery, Message.class).build();


        FirebaseListAdapter<Message> messageAdapter = new FirebaseListAdapter<Message>(messageOptions) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView text = findViewById(R.id.messageView);
                TextView username = findViewById(R.id.contactView);
                TextView timestamp = findViewById(R.id.timestampView);
                text.setText(model.getText());
                username.setText(model.getDisplayName());
                timestamp.setText(DateFormat.format("dd (HH:mm:ss)", model.getTimestamp()));

            }
        };

        ListView readMessageList = findViewById(R.id.list_of_messages);
        readMessageList.setAdapter(messageAdapter);


    }
    public void send(Message message){

mUserRef.child("messages").push().setValue(message);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return AppUtils.dropDownChangeActivity(item,MessagingActivity.this);
    }


}


