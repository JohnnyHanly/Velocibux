package teamvelociraptor.assignment4;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

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
            case R.id.activity_qr_code_item:
                context.startActivity(new Intent(context, QRActivity.class));
                return true;
            case R.id.activity_account_item:
                context.startActivity(new Intent(context, AccountBalance.class));
                return true;
            default:
                return true;

        }
    }
}