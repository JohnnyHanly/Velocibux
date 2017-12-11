package teamvelociraptor.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import teamvelociraptor.assignment4.models.User;

public class AddFriendsActivity extends AppCompatActivity {

    FloatingActionButton addFriendsFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(AddFriendsActivity.this, QRCameraActivity.class);
        startActivityForResult(i, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == QRCameraActivity.RESULT_OK){
                String result=data.getStringExtra("result");
                Intent intent = new Intent();
                intent.putExtra("uuid", result);
                this.setResult(RESULT_OK, intent);
                finish();
            }
            if (resultCode == QRCameraActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
