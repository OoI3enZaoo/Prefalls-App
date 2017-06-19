package Fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import Activity.LoginActivity;
import Activity.PetientListActivity;
import Activity.R;
import DataResponse.DataMapEvent;
import DataResponse.LatLongResponse;
import DataResponse.MemberResponse;
import SQLite.DBPetient;
import SQLite.DBUser;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static Activity.HomeActivity.PID;


public class FeedMapFragment extends Fragment {

    MapView mapView;
    GoogleMap map;
    public static Double mLat;
    public static Double mLong;
    public static String mTstamp;
    public static String stab;
    public static String sym;
    public static String spd;
    public String TAG = "FeedMapFragment";
    public Marker mMarker;
    private IntentFilter mIntentFilter;
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string.home";


    public FeedMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed_map, container, false);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastStringAction);

// Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());


        Log.i(TAG, "PID: " + PID);
        new LatLongTask().execute(PID);



        return v;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
        getActivity().registerReceiver(mReceiver, mIntentFilter);
    }
    @Override
    public void onPause() {
        getActivity().unregisterReceiver(mReceiver);
        super.onPause();
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

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String pid = intent.getStringExtra("pid").toString();
            Double lat = intent.getDoubleExtra("lat",0);
            Double lng = intent.getDoubleExtra("lng",0);
            String stab = intent.getStringExtra("stab");
            String sym = intent.getStringExtra("sym");
            String spd = intent.getStringExtra("spd");
            Long ts = intent.getLongExtra("ts",0);

            DBPetient dbPetient = new DBPetient(getActivity());
            String sPName = "Patient Name: " + dbPetient.getFullName(pid);
            String sStab = "Stability index: " + stab + "\n";
            String sSym = "Symmetry index: " + sym + "\n";
            String sSpd = "avg speed: " + spd + " m/s";
            String res = sStab + sSym + sSpd;



            if(PID.equals(pid) ){
                if(lat != null && lng != null){
                    LatLng latlng = new LatLng(lat, lng);
                    String title = sPName;
                    String snippet = res;
                    animateMarker(mMarker,latlng,false,title,snippet);
                    Log.i(TAG,"PID == PID ");
                }

            }
            Log.i(TAG,"pid: " +pid + " lat: " + lat + " long: " + lng + " stab: " +stab + " sym: " + sym + " spd: " + spd + " ts: " + ts);

           /* DataMapEvent dataMapEvent = new DataMapEvent();
            dataMapEvent.setPid(pid);
            dataMapEvent.setLat(lat);
            dataMapEvent.setLng(lng);
            dataMapEvent.setStab(stab);
            dataMapEvent.setSym(sym);
            dataMapEvent.setSpd(spd);
            dataMapEvent.setTs(ts);*/




        }
    };


    private class LatLongTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String pid = params[0];
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("pid", pid)
                    .build();
            Request request = new Request.Builder()
                    .url("http://sysnet.utcc.ac.th/prefalls/api/latlong.jsp")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                Log.i(TAG, "S: " + result.trim());
                if (!result.trim().equals("[]")) {

                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<LatLongResponse>>() {
                    }.getType();
                    Collection<LatLongResponse> enums = gson.fromJson(result, collectionType);
                    LatLongResponse[] result1 = enums.toArray(new LatLongResponse[enums.size()]);

                    Log.i(TAG, "Result1: " + result1);
                    mLat = Double.parseDouble(result1[0].getLat());
                    mLong = Double.parseDouble(result1[0].getLng());
                    mTstamp = result1[0].getTstamp();
                    stab = result1[0].getStab();
                    sym = result1[0].getSym();
                    spd = result1[0].getSpd();
                } else {

                    return "0";
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return pid;
        }

        @Override
        protected void onPostExecute(String pid) {
            super.onPostExecute(pid);
            if (!pid.equals("0")) {
                DBPetient dbUser = new DBPetient(getActivity());
                String sPName = "Patient Name: " + dbUser.getFullName(pid);
                String sStab = "Stability index: " + stab + "\n";
                String sSym = "Symmetry index: " + sym + "\n";
                String sSpd = "avg speed: " + spd + " m/s";
                String res = sStab + sSym + sSpd;
                // Updates the location and zoom of the MapView
                //LatLng coordinates = new LatLng(mLat, mLong);
                LatLng coordinates = new LatLng(mLat, mLong);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates, 17);
                map.animateCamera(cameraUpdate);
                mMarker = map.addMarker(new MarkerOptions()
                        .position(coordinates)
                        .title(sPName)
                        .snippet(res));
                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        LinearLayout info = new LinearLayout(getActivity());
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(getActivity());
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(getActivity());
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });

            }else{
                Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker, final String title , final String snipped) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = map.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                //marker.hideInfoWindow();
                marker.setPosition(new LatLng(lat, lng));
                marker.setTitle(title);
                marker.setSnippet(snipped);
               // marker.showInfoWindow();


                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

}
