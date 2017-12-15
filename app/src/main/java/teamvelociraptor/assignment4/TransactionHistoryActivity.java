package teamvelociraptor.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
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

import java.text.DecimalFormat;

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
        setTitle("Transaction History");
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
                TextView timeStamp = v.findViewById(R.id.transaction_timestamp);
                TextView sender = v.findViewById(R.id.transaction_sender);
                TextView receiver = v.findViewById(R.id.transaction_receiver);
                TextView amount = v.findViewById(R.id.transaction_amount);
                TextView transactionId = v.findViewById(R.id.transaction_id);
                DecimalFormat format = new DecimalFormat("0.00");

                timeStamp.setText(DateFormat.format("E MM/dd/yy K:mm aa", model.getTimestamp()));
                sender.setText("Sender: " + model.getSender());
                receiver.setText("Receiver: " + model.getReceiver());
                amount.setText("$" + format.format(model.getAmount()));
                transactionId.setText("Transaction Type: " + model.getId());

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
