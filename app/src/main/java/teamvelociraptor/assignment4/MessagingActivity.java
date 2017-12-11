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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import teamvelociraptor.assignment4.models.Message;

public class MessagingActivity extends AppCompatActivity {
    FloatingActionButton sendMessage;
    FloatingActionButton paymentMessage;
    private FirebaseAuth firebaseAuth;
    RelativeLayout activity_messaging;
    private EditText input;
    private FirebaseUser firebaseUser;
    private static DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    //meow
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        activity_messaging = findViewById(R.id.activity_messaging);
        sendMessage = findViewById(R.id.sendButton);
        paymentMessage = findViewById(R.id.paymentButton);
        input = findViewById(R.id.payment_input);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MessagingActivity.this, "You press the send button", Toast.LENGTH_SHORT).show();


                EditText input = (EditText) findViewById(R.id.payment_input);
                FirebaseDatabase.getInstance().getReference().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser()
                        .getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getUid(), input.getText().toString()));
                input.setText("");


                Message message = new Message(firebaseUser.getDisplayName(), firebaseUser.getUid(), input.getText().toString());


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
    public static void send(Message message){



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


