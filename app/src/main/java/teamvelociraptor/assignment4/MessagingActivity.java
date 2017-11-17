package teamvelociraptor.assignment4;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;
import com.google.firebase.database.Query;
import android.text.format.DateFormat;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MessagingActivity extends AppCompatActivity {
FloatingActionButton sendMessage;
FloatingActionButton paymentMessage;
private FirebaseAuth firebaseAuth;
RelativeLayout activity_messaging;
private EditText input;
private FirebaseUser firebaseUser;
    private static DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
       // firebaseUser= firebaseAuth.getCurrentUser();
        activity_messaging=(RelativeLayout)findViewById(R.id.activity_messaging);
        sendMessage=(FloatingActionButton)findViewById(R.id.sendButton);
        paymentMessage=(FloatingActionButton)findViewById(R.id.paymentButton);
        input=(EditText)findViewById(R.id.input);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MessagingActivity.this,"You press the send button",Toast.LENGTH_SHORT).show();



                /*
                EditText input=(EditText)findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser()
                        .getDisplayName(),FirebaseAuth.getInstance().getCurrentUser().getUid(),input.getText().toString()));
                input.setText("");*/


                //Message message= new Message(firebaseUser.getDisplayName(),firebaseUser.getUid(),input.getText().toString())


            }

        });
        paymentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessagingActivity.this,"You press the payment button",Toast.LENGTH_SHORT).show();
            }
        });
        diplayMessages();

    }
    







private void displayContactList(){
        Query conversationQuery= FirebaseDatabase.getInstance().getReference();

        FirebaseListOptions<Message> convoOptions= new FirebaseListOptions.Builder<Message>().setLayout(R.layout.conversation_list)
                .setQuery(conversationQuery,Message.class).build();


        FirebaseListAdapter<Message> convoAdapter= new FirebaseListAdapter<Message>(convoOptions) {
            @Override
            protected void populateView(View v, Message model, int position) {

          TextView newestText;
          TextView contactName;
          TextView timestamp;


            }
        };

}

private void diplayMessages(){
    Query messageQuery= FirebaseDatabase.getInstance().getReference();


    FirebaseListOptions<Message> messageOptions= new FirebaseListOptions.Builder<Message>().setLayout(R.layout.message_list)
            .setQuery(messageQuery,Message.class).build();


FirebaseListAdapter<Message>messageAdapter= new FirebaseListAdapter<Message>(messageOptions) {
    @Override
    protected void populateView(View v, Message model, int position) {
        TextView text;
        TextView user;
        TextView timestamp;
        text=(TextView)findViewById(R.id.messageView);
        user=(TextView)findViewById(R.id.contactView);
        timestamp=(TextView)findViewById(R.id.timestampView);

        text.setText(model.getText());
        user.setText(model.getName());
        timestamp.setText(DateFormat.format("dd (HH:mm:ss)", model.getTimestamp()));
    }
};

ListView readMessageList= findViewById(R.id.list_of_messages);
readMessageList.setAdapter(messageAdapter);


}


}

//TODO
/*
make the main activty of the program into the NavigationDrawer

connect users to the firebase server so we canner access their usernames/ids/messages.(Account/Login guys job)

populate the main screen of the app with conversations that the user has had





 */
