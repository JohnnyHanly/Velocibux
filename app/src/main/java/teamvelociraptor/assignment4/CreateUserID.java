package teamvelociraptor.assignment4;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class CreateUserID extends AppCompatActivity {

    TextView tvUIDS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_id);

        tvUIDS = (TextView) findViewById(R.id.tv_uids);

        StringBuilder sb = new StringBuilder();

        TelephonyManager telMan = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        sb.append("Android ID: " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        tvUIDS.setText(sb.toString());
    }

}