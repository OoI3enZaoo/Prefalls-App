package Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import SQLite.DBAlert;
import SQLite.DBPetient;

public class MapActivity extends AppCompatActivity {

    MapView mapView;
    GoogleMap map;
    public static String mName;
    public static Double mLat;
    public static Double mLong;
    public static String mTstamp;
    public static String stab;
    public static String sym;
    public static String spd;
    public String TAG = "MapActivity";
    String  pid;
    String  time;
    //private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
       // dialog = new ProgressDialog(getApplicationContext());
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        time = intent.getStringExtra("time");
        Log.i(TAG,"PID: " + pid);
        Log.i(TAG,"Time: " + time);
        DBAlert dbAlert = new DBAlert(getApplicationContext());
        Cursor res = dbAlert.getDataInTime(pid,time);
        Log.i(TAG,"row: " + res.getCount());
        if(res.getCount() == 0){
            Log.i(TAG,"Data not found");
        }else{
            while(res.moveToNext()){
                mName = res.getString(1);
                String time2 = res.getString(4);
                mLat = Double.parseDouble(res.getString(5));
                mLong = Double.parseDouble(res.getString(6));
                stab = res.getString(8);
                sym = res.getString(9);
                spd = res.getString(10);
                Log.i(TAG,"TIME1: " + time);
                Log.i(TAG,"TIME2:  " + time2);

                if(time2.equals(time)){
                    Log.i(TAG,"Time equals");
                }else{
                    Log.i(TAG,"Time Not equals");
                }
                Log.i(TAG,"Name: " + mName + " time: " + time2 + " lat: " + mLat + " lon: " + mLong + " sym:" + sym + " stab: " + stab + " spd: " + spd);
            }
        }

       // startDialog();
// Gets the MapView from the XML layout and creates it
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getApplicationContext());
        LatLng coordinates = new LatLng(mLat, mLong);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates, 17);
        map.animateCamera(cameraUpdate);
        String sPName = "Patient Name: " + mName;
        String sStab = "Stability index: " + stab + "\n";
        String sSym = "Symmetry index: " + sym + "\n";
        String sSpd = "avg speed: " + spd + " m/s";
        String result = sStab + sSym + sSpd;

        map.addMarker(new MarkerOptions()
                .position(coordinates)
                .title(sPName)
                .snippet(result));
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    /*public void startDialog() {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void stopDialog() {
        dialog.dismiss();
    }*/
}
