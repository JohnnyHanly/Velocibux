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
        Intent intent = new Intent();
        intent.putExtra("uuid", "qskOpwQr1hfSDoMwd9evdIDkVpx2");
        this.setResult(RESULT_OK, intent);
        finish();
    }
}
