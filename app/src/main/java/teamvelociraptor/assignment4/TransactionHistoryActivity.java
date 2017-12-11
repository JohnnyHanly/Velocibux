package teamvelociraptor.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import teamvelociraptor.assignment4.models.Transaction;
import teamvelociraptor.assignment4.models.User;

public class TransactionHistoryActivity extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());
    DatabaseReference mTransactionsRef = mUserRef.child("transactions");

    User userObj;
    Map<String, Transaction> transactions;
    ListView transactionHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
    }

    protected void onStart(){
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

        transactionHistory = findViewById(R.id.transactionDisplayLayout);
        displayTransactionHistory();
        addTransaction();
    }

    protected void displayTransactionHistory(){

    }

    protected void addTransaction(){
        transactions = userObj.getTransactions();
    }
}
