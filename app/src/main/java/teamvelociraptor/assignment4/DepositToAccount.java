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

import teamvelociraptor.assignment4.models.Transaction;
import teamvelociraptor.assignment4.models.User;

public class DepositToAccount extends AppCompatActivity {
    FloatingActionButton sendMoney;
    Button send;
    User userObj;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());
    DatabaseReference mTransactionRef = mUserRef.child("transactions");
    List<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        send= findViewById(R.id.paybutton);
        sendMoney = findViewById(R.id.paymentButton);
        setTitle("Deposit to Account");
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
        mTransactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    GenericTypeIndicator<List<Transaction>> t = new GenericTypeIndicator<List<Transaction>>(){};
                    transactionList = dataSnapshot.getValue(t);
                    if (transactionList == null){
                        transactionList = new ArrayList<>();
                    }
                }
                else{
                    transactionList = new ArrayList<>();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transaction = new Transaction();
                EditText amount = findViewById(R.id.payment_input);
                try {
                    double inputAmount = Double.parseDouble(amount.getText().toString());
                    inputAmount = inputAmount * 100;
                    inputAmount = Math.round(inputAmount);
                    inputAmount = inputAmount / 100;
                    transaction.setAmount(inputAmount);
                    transaction.setSender(userObj.getDisplayName());
                    transaction.setReceiver(userObj.getDisplayName());
                    transaction.setTimestamp(new Date());
                    transaction.setLat(37.7232346);
                    transaction.setLon(-122.4771284);
                    transaction.setId("Deposit to account");
                    DecimalFormat format = new DecimalFormat("0.00");
                    String formattedAmount = format.format(inputAmount);
                    Toast.makeText(DepositToAccount.this, "You've deposited : $" + formattedAmount, Toast.LENGTH_SHORT).show();

                    userObj.setBalance(userObj.getBalance() + inputAmount);
                    mUserRef.setValue(userObj);

                    sendTransaction(transaction);
                    Intent accountBalance = new Intent(DepositToAccount.this, AccountBalance.class);
                    startActivity(accountBalance);
                } catch (NumberFormatException e) {
                    Toast.makeText(DepositToAccount.this, "You must enter an amount", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void sendTransaction(Transaction transaction) {
        transactionList.add(transaction);
        DatabaseReference reference1 = mUserRef.child("transactions");
        reference1.setValue(transactionList);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return AppUtils.dropDownChangeActivity(item, DepositToAccount.this);
    }




}
