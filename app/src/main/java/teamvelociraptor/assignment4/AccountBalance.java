package teamvelociraptor.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import teamvelociraptor.assignment4.models.*;

public class AccountBalance extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mUserRef = mRootRef.child("users").child(user.getUid());

    User userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);
        Button transferToBankButton = findViewById(R.id.deposit_to_bank);
        Button transferToAccountButton = findViewById(R.id.transfer_to_account);
        Button QR_Code = findViewById(R.id.QR_Code);

        setTitle("Account");
        transferToBankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent depositToBank = new Intent(AccountBalance.this, DepositToBank.class);
                startActivity(depositToBank);

            }


        });

        transferToAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent depositToAccount = new Intent(AccountBalance.this, DepositToAccount.class);
                startActivity(depositToAccount);
            }
        });


        QR_Code.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                generateQR();
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

                getBalance();
                displayProfPic();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayProfPic() {
        ImageView profileImage = findViewById(R.id.profileImage);
        new GetUserProfileImage(userObj, profileImage).execute();


    }

    private void generateQR() {
        Intent intent = new Intent(this, QRActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return AppUtils.dropDownChangeActivity(item, this);
    }


    public void addToBalance(double inputAmount) {
        userObj.setBalance(inputAmount);
        mUserRef.setValue(userObj);
    }

    private void getBalance() {

        TextView accountbalancetext = (TextView) findViewById(R.id.account_balance);
        accountbalancetext.setText(Double.toString(userObj.getBalance()));

    }

}
