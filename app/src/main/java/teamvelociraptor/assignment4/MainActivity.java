package teamvelociraptor.assignment4;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private FirebaseUser User;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth.AuthStateListener AuthListener;
    private GoogleApiClient GoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth = FirebaseAuth.getInstance();
        User = Auth.getCurrentUser();

        launchMessagingActivity();
        findViewById(R.id.friends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Friends activity launched", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FriendsListActivity.class);
                startActivity(intent);
            }
        });
    }
    /*this method launches the Messaging Activty after the user presses a button on the main activity.
I did not know what our plans are for the startup screen so I decided to create a different activty for
messaging. This is a temporary way to launch messaging until we know how we are actually
going to stucture the UI
*/
    private void launchMessagingActivity(){
        //button that switches to MessagingActivity
        Button bmessageSwitchButton = (Button) findViewById(R.id.switcher);
       bmessageSwitchButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //creates a popup that confirms button press
               Toast.makeText(MainActivity.this, "Clicked Launch Messaging", Toast.LENGTH_SHORT)
                       .show();

//test commit
               //Launch Messanging Activity
               Intent intent= new Intent(MainActivity.this, MessagingActivity.class);
                startActivity(intent);

           }
       });

    }




}
