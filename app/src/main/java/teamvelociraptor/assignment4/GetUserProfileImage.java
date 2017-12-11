package teamvelociraptor.assignment4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import teamvelociraptor.assignment4.models.User;

/**
 * Created by ericgroom on 12/10/17.
 */

class GetUserProfileImage extends AsyncTask<Void, Void, Bitmap> {

    ImageView view;
    User user;
    ResponseBody responseBody;
    InputStream inputStream;

    GetUserProfileImage(User user, ImageView view) {
        this.view = view;
        this.user = user;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        String uri = user.getImageUrl();
        try {
            URL url = new URL(uri);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            inputStream = responseBody.byteStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        try {
            responseBody.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            view.setImageBitmap(result);
        }
    }

}