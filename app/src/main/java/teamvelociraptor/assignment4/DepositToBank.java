package teamvelociraptor.assignment4;

import android.annotation.SuppressLint;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class DepositToBank extends AppCompatActivity {
    FloatingActionButton sendMoney;
    private static final int REQUEST_CODE_FINE_GPS = 1;
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
//        int currentapiVersion = Build.VERSION.SDK_INT;
//        if (currentapiVersion >= Build.VERSION_CODES.M){
//            if (checkPermission()){
//                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_SHORT);
//            }
//            else{
//                requestPermission();
//            }
//        }


        sendMoney = findViewById(R.id.paymentButton);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transaction = new Transaction();

//                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                EditText amount = findViewById(R.id.payment_input);
                double inputAmount = Double.parseDouble(amount.getText().toString());
                inputAmount = inputAmount * 100;
                inputAmount = Math.round(inputAmount);
                inputAmount = inputAmount / 100;
                if(inputAmount <= userObj.getBalance()) {
                    transaction.setAmount(inputAmount);
                    transaction.setSender(userObj.getDisplayName());
                    transaction.setReceiver("Deposit");
                    transaction.setTimestamp(new Date());
                    transaction.setLat(37.7232346);
                    transaction.setLon(-122.4771284);
                    DecimalFormat format = new DecimalFormat("0.00");
                    String formattedAmount = format.format(inputAmount);
                    Toast.makeText(DepositToBank.this, "You've sent : $" + formattedAmount, Toast.LENGTH_SHORT).show();

                    userObj.setBalance(userObj.getBalance() - inputAmount);
                    mUserRef.setValue(userObj);

                    sendTransaction(transaction);

                }
                else{
                    Toast.makeText(DepositToBank.this, "Amount exceeds balance.", Toast.LENGTH_SHORT).show();
                }

                Intent accountBalance = new Intent(DepositToBank.this, AccountBalance.class);
                startActivity(accountBalance);

            }
        });
    }

    private void sendTransaction(Transaction transaction) {
        transactionList.add(transaction);
        DatabaseReference reference1 = mUserRef.child("transactions");
        reference1.setValue(transactionList);

    }

    private boolean checkPermission(){

        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_FINE_GPS);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_FINE_GPS:
                if (grantResults.length > 0) {

                    boolean gpsAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (gpsAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                            REQUEST_CODE_FINE_GPS);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }

            break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(DepositToBank.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
                    else{
                        transactionList = new ArrayList<>();
                    }
                }
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
        return AppUtils.dropDownChangeActivity(item, DepositToBank.this);
    }




}
