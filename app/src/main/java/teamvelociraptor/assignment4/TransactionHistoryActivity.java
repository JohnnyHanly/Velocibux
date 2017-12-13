package teamvelociraptor.assignment4;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

import teamvelociraptor.assignment4.models.Transaction;
import teamvelociraptor.assignment4.models.User;

public class TransactionHistoryActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());
    DatabaseReference mTransactionsRef = mUserRef.child("transactions");

    User userObj;

    ListView transactionHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }

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

        transactionHistory = findViewById(R.id.transaction_history);
        addTransaction();
        displayTransactionHistory();

    }

    protected void displayTransactionHistory() {
        FirebaseListOptions<Transaction> options = new FirebaseListOptions.Builder<Transaction>()
                .setQuery(mTransactionsRef, Transaction.class)
                .setLayout(R.layout.transaction_history)
                .build();
        FirebaseListAdapter<Transaction> adapter = new FirebaseListAdapter<Transaction>(options) {
            @Override
            protected void populateView(View v, final Transaction model, int position) {
                TextView sender = v.findViewById(R.id.transaction_sender);
                TextView receiver = v.findViewById(R.id.transaction_receiver);
                TextView amount = v.findViewById(R.id.transaction_amount);

                sender.setText("Sender: " + model.getSender());
                receiver.setText("Receiver: " + model.getReceiver());
                amount.setText("Amount: " + Double.toString(model.getAmount()));

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TransactionHistoryActivity.this, MapsActivity.class);
                        intent.putExtra("transaction", model);
                        startActivity(intent);
                    }
                });
            }
        };
        transactionHistory.setAdapter(adapter);
        adapter.startListening();
    }

    protected void addTransaction() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location;

        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch(SecurityException e){
            location = null;
        }

        transaction1.setSender("user1");
        transaction1.setReceiver("user2");
        transaction1.setAmount(12.52);
        transaction1.setTimestamp(new Date());
        transaction1.setId("1");
        transaction1.setLat(32);
        transaction1.setLon(32);

        transaction2.setSender("user2");
        transaction2.setReceiver("user1");
        transaction2.setAmount(567.90);
        transaction2.setTimestamp(new Date());
        transaction2.setId("2");
        transaction2.setLat(42);
        transaction2.setLon(42);

        transactions.add(transaction1);
        transactions.add(transaction2);
        mTransactionsRef.setValue(transactions);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return AppUtils.dropDownChangeActivity(item, this);
    }
}
