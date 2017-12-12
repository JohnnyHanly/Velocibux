package teamvelociraptor.assignment4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class AppUtils {

    private AppUtils(){
    }

    static boolean dropDownChangeActivity(MenuItem item, Context context){

        switch (item.getItemId()){
            case R.id.activity_main_item:
                context.startActivity(new Intent(context, MainActivity.class ));
                return true;
            case R.id.activity_messaging_item:
                context.startActivity(new Intent(context, MessagingActivity.class));
                return true;
            case R.id.activity_friends_item:
                context.startActivity(new Intent(context, FriendsListActivity.class));
                return true;
            case R.id.activity_payment_item:
                context.startActivity(new Intent(context,PaymentActivity.class));
                return true;
            case R.id.activity_account_item:
                context.startActivity(new Intent(context,AccountBalance.class));
                return true;
            case R.id.activity_transaction_history_item:
                context.startActivity(new Intent(context, TransactionHistoryActivity.class));
                return true;
            case R.id.activity_location_item:
                context.startActivity(new Intent(context, MapsActivity.class));
            default:
                return true;

        }
    }
}