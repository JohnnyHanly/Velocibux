package teamvelociraptor.assignment4;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import teamvelociraptor.assignment4.models.User;

public class AccountBalance extends AppCompatActivity {

    TextView accountbalancetext = (TextView) findViewById(R.id.account_balance);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);
        accountbalancetext.setText(Double.toString(User.getBalance()));


            }

}
