package teamvelociraptor.assignment4;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import teamvelociraptor.assignment4.models.Transaction;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Transaction t;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        t = (Transaction) getIntent().getSerializableExtra("transaction");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String senderInfo= "Sender: " + t.getSender();
        String receiverInfo = "Receiver: " + t.getReceiver();
        String amountInfo = "Amount: " + t.getAmount();
        String timeInfo = "Time Stamp: " + t.getTimestamp().toString();

        // Add a marker to where the Transaction occurred and move the camera
        LatLng transactionLocation = new LatLng(t.getLat(), t.getLon());
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(transactionLocation));
        mMap.addMarker(new MarkerOptions().position(transactionLocation).title(timeInfo)
                .snippet(senderInfo).snippet(receiverInfo).snippet(amountInfo)); //makes the title of that marker contain the amount

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return AppUtils.dropDownChangeActivity(item, this);
    }
}
