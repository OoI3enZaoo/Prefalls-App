package Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
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
import DataResponse.LatLongResponse;
import DataResponse.MemberResponse;
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
    public String TAG = "FeedMapFragment";
    private ProgressDialog dialog;
    public FeedMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed_map, container, false);
        dialog = new ProgressDialog(getActivity());

        startDialog();
// Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);


        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());


        Log.i(TAG,"PID: " +PID);
        new LatLongTask().execute(PID);

        return v;
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
    public void startDialog() {
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void stopDialog() {
        dialog.dismiss();
    }

    private class LatLongTask  extends AsyncTask<String,Void,String>{
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
                Log.i(TAG, "S: " + result);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<LatLongResponse>>() {
                }.getType();
                Collection<LatLongResponse> enums = gson.fromJson(result, collectionType);
                LatLongResponse[] result1 = enums.toArray(new LatLongResponse[enums.size()]);

                    Log.i(TAG,"Result1: " + result1);
                    mLat = Double.parseDouble(result1[0].getLat());
                    mLong = Double.parseDouble(result1[0].getLng());
                     mTstamp = result1[0].getTstamp();



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(TAG, "S: " + result);
            // Updates the location and zoom of the MapView
            LatLng coordinates = new LatLng(mLat,mLong);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates, 10);
            map.animateCamera(cameraUpdate);
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(coordinates)
                    .title("San Francisco")
                    .snippet("" + mTstamp));
            stopDialog();
        }
    }
}
