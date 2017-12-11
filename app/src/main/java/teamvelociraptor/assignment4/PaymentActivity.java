package teamvelociraptor.assignment4;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class PaymentActivity extends AppCompatActivity {
    FloatingActionButton sendMoney;
    RelativeLayout activity_payment;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        sendMoney= findViewById(R.id.paymentButton);
        sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //transfer money to other user.
                Toast.makeText(PaymentActivity.this, "You pressed the payment button", Toast.LENGTH_SHORT).show();


                EditText input= findViewById(R.id.payment_input);
            }
        });

    }
}
