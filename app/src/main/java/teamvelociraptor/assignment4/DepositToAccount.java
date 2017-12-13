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

import teamvelociraptor.assignment4.models.User;

public class DepositToAccount extends AppCompatActivity {
    FloatingActionButton sendMoney;
    User userObj;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());

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
                Toast.makeText(DepositToAccount.this, "You've sent : $" + formattedAmount, Toast.LENGTH_SHORT).show();

                userObj.setBalance(userObj.getBalance() + inputAmount);
                mUserRef.setValue(userObj);

                Intent accountBalance = new Intent(DepositToAccount.this, AccountBalance.class);
                startActivity(accountBalance);

            }
        });
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
