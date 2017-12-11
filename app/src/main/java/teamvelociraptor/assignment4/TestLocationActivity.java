package teamvelociraptor.assignment4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import teamvelociraptor.assignment4.models.Transaction;

public class TestLocationActivity extends AppCompatActivity {

    Transaction transaction;
    TextView test_sender;
    TextView test_receiver;
    TextView test_amount;
    TextView test_timestamp;
    TextView test_lat;
    TextView test_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_location);
        transaction = (Transaction)getIntent().getSerializableExtra("transaction");

        test_sender = findViewById(R.id.test_sender);
        test_receiver = findViewById(R.id.test_receiver);
        test_amount = findViewById(R.id.test_amount);
        test_timestamp = findViewById(R.id.test_timestamp);
        test_lat = findViewById(R.id.test_lat);
        test_lon = findViewById(R.id.test_long);
    }

    protected void onStart(){
        super.onStart();

        //test_sender.setText(transaction.getSender());
        //test_receiver.setText(transaction.getReceiver());
        //test_amount.setText(transaction.getReceiver());
        test_timestamp.setText(transaction.getTimestamp().toString());
        //test_lat.setText(Double.toString(transaction.getLat()));
        //test_lon.setText(Double.toString(transaction.getLon()));
    }
}
