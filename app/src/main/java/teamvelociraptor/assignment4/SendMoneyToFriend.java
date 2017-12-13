package teamvelociraptor.assignment4;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

import teamvelociraptor.assignment4.models.Message;
import teamvelociraptor.assignment4.models.User;

public class SendMoneyToFriend extends AppCompatActivity {
    FloatingActionButton sendMoney;

    String recipientID;

    FloatingActionButton paymentMessage;

    private EditText input;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mMessageRef;

    DatabaseReference mUserRef = ref.child("users").child(mUser.getUid());
    DatabaseReference mConvoRef = mUserRef.child("conversations");
    DatabaseReference mRecipientRef;
    List<Message> messageList;
    User userObj;
    User recipientObj;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        sendMoney = findViewById(R.id.paymentButton);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText amount = findViewById(R.id.payment_input);
                double inputAmount = Double.parseDouble(amount.getText().toString());
                inputAmount = inputAmount * 100;
                inputAmount = Math.round(inputAmount);
                inputAmount = inputAmount / 100;
                DecimalFormat format = new DecimalFormat("0.00");
                String formattedAmount = format.format(inputAmount);
                Toast.makeText(SendMoneyToFriend.this, "You've sent : $" + formattedAmount, Toast.LENGTH_SHORT).show();

                userObj.setBalance(userObj.getBalance() - inputAmount);
                mUserRef.setValue(userObj);

                recipientObj.setBalance((recipientObj.getBalance()) + inputAmount);
                mRecipientRef.setValue(recipientObj);



                Intent accountBalance = new Intent(SendMoneyToFriend.this, AccountBalance.class);
                startActivity(accountBalance);

            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        super.onStart();
        recipientID = getIntent().getStringExtra("uuid");
        mRecipientRef = ref.child("users").child(recipientID);

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
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return AppUtils.dropDownChangeActivity(item, SendMoneyToFriend.this);
    }




}
