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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import teamvelociraptor.assignment4.models.Message;
import teamvelociraptor.assignment4.models.Transaction;
import teamvelociraptor.assignment4.models.User;

public class SendMoneyToFriend extends AppCompatActivity {
    Button send;

    String recipientID;

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    DatabaseReference mUserRef = ref.child("users").child(mUser.getUid());
    DatabaseReference mTransactionRef = mUserRef.child("transactions");
    DatabaseReference mRecipientRef;
    DatabaseReference mRecipientTransactionRef;
    List<Transaction> userTransactionList;
    List<Transaction> recipientTransactionList;

    User userObj;
    User recipientObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        recipientID = getIntent().getStringExtra("uuid");
        mRecipientRef = ref.child("users").child(recipientID);
        mRecipientTransactionRef = mRecipientRef.child("transactions");

        setTitle("Send Money");
        send = findViewById(R.id.paybutton);
    }





    @Override
    protected void onStart() {
        super.onStart();

        mRecipientRef.addValueEventListener(new ValueEventListener() {
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
        mTransactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    GenericTypeIndicator<List<Transaction>> t1 = new GenericTypeIndicator<List<Transaction>>(){
                    };
                    userTransactionList = dataSnapshot.getValue(t1);
                    if(userTransactionList == null){
                        userTransactionList = new ArrayList<>();
                    }
                }
                else{
                    userTransactionList = new ArrayList<>();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRecipientTransactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    GenericTypeIndicator<List<Transaction>> t2 = new GenericTypeIndicator<List<Transaction>>(){
                    };
                    recipientTransactionList = dataSnapshot.getValue(t2);
                    if (recipientTransactionList == null){
                        recipientTransactionList = new ArrayList<>();
                    }
                }
                else{
                    recipientTransactionList = new ArrayList<>();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction userTransaction = new Transaction();
                Transaction recipientTransaction = new Transaction();
                EditText amount = findViewById(R.id.payment_input);
                try {
                    double inputAmount = Double.parseDouble(amount.getText().toString());
                    inputAmount = inputAmount * 100;
                    inputAmount = Math.round(inputAmount);
                    inputAmount = inputAmount / 100;

                    if(inputAmount <= userObj.getBalance()) {
                        DecimalFormat format = new DecimalFormat("0.00");
                        String formattedAmount = format.format(inputAmount);
                        Toast.makeText(SendMoneyToFriend.this, "You've sent : $" + formattedAmount, Toast.LENGTH_SHORT).show();

                        userObj.setBalance(userObj.getBalance() - inputAmount);
                        mUserRef.setValue(userObj);
                        recipientObj.setBalance((recipientObj.getBalance()) + inputAmount);
                        mRecipientRef.setValue(recipientObj);

                        buildUserTransaction(userTransaction, inputAmount);
                        buildFriendTransaction(recipientTransaction, inputAmount);

                        sendTransactions(userTransaction, recipientTransaction);
                    }
                    else{
                        Toast.makeText(SendMoneyToFriend.this, "Amount exceeds balance.", Toast.LENGTH_SHORT).show();
                    }

                    Intent accountBalance = new Intent(SendMoneyToFriend.this, AccountBalance.class);
                    startActivity(accountBalance);
                } catch (NumberFormatException e) {
                    Toast.makeText(SendMoneyToFriend.this, "You must enter an amount", Toast.LENGTH_LONG);
                }



            }
        });
    }

    public void sendTransactions(Transaction t1, Transaction t2){
        userTransactionList.add(t1);
        recipientTransactionList.add(t2);
        mTransactionRef.setValue(userTransactionList);
        mRecipientTransactionRef.setValue(recipientTransactionList);
    }
    public void buildUserTransaction(Transaction t, double amount){
        t.setAmount(amount);
        t.setSender(userObj.getDisplayName());
        t.setReceiver(recipientObj.getDisplayName());
        t.setTimestamp(new Date());
        t.setLat(37.7232346);
        t.setLon(-122.4771284);
        t.setId("Sent to Friend");
    }
    public void buildFriendTransaction(Transaction t, double amount){

        t.setAmount(amount);
        t.setSender(userObj.getDisplayName());
        t.setReceiver(recipientObj.getDisplayName());
        t.setTimestamp(new Date());
        t.setLat(37.7232346);
        t.setLon(-122.4771284);
        t.setId("Received From Friend");
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
