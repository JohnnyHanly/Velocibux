package teamvelociraptor.assignment4;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth Auth;
    private FirebaseUser User;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth.AuthStateListener AuthListener;
    private GoogleApiClient GoogleApiClient;
    private static final int RC_SIGN_IN = 123; // Needed for auth




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                );

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchMessagingActivity();
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


               //Launch Messanging Activity
               Intent intent= new Intent(MainActivity.this, MessagingActivity.class);
                startActivity(intent);

           }
       });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == ResultCodes.OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed, check response for error code
                // ... TODO
            }
        }
    }





}
