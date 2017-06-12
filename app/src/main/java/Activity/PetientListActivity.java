package Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DataResponse.AlertTypeResponse;
import DataResponse.PatientResponse;
import SQLite.DBAlertType;
import SQLite.DBPetient;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.lang.reflect.Type;

public class PetientListActivity extends AppCompatActivity {

    public static CardViewAdapter mAdapter;
    public static ArrayList<String> pidArray = new ArrayList<>();
    public static ArrayList<String> nameArray = new ArrayList<String>();
    public static ArrayList<String> imgArray = new ArrayList<String>();
    public static String TAG = "PetientActivity";
    private IntentFilter mIntentFilter;
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
    public static final String mBroadcastIntegerAction = "com.truiton.broadcast.integer";
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petientlist);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastStringAction);
        mIntentFilter.addAction(mBroadcastIntegerAction);
        dialog = new ProgressDialog(PetientListActivity.this);
        startDialog();
        OnItemTouchListener itemTouchListener = new OnItemTouchListener() {
            @Override
            public void onCardViewTap(View view, int position) {
                Toast.makeText(getApplicationContext(), "Tapped " + pidArray.get(position) + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pid",pidArray.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onButton1Click(View view, int position) {
                Toast.makeText(getApplicationContext(), "Clicked Button1 in " + nameArray.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButton2Click(View view, int position) {
                Toast.makeText(getApplicationContext(), "Clicked Button2 in " + nameArray.get(position), Toast.LENGTH_SHORT).show();
            }
        };
        mAdapter = new CardViewAdapter(nameArray, itemTouchListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_petientlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);

        nameArray.clear();


        mAdapter.notifyDataSetChanged();
        DBPetient dbPetient = new DBPetient(getApplicationContext());
        Cursor res = dbPetient.getAllData();

        if (res.getCount() == 0) {
            new LoadAlertTask().execute();
            new getPatientTask().execute();
            Log.i(TAG, "get from Mysql");
        } else {
            while (res.moveToNext()) {
                String name = res.getString(1) + " " + res.getString(2);
                String imagePath = res.getString(7);
                String pid = res.getString(0);
                pidArray.add(pid);
                nameArray.add(name);
                imgArray.add(imagePath);
                stopDialog();
            }
            Log.i(TAG, "get from SQlite");
        }

        Intent intent = new Intent(getApplicationContext(), Service.getAlertMessage.class);
        startService(intent);

    }

    interface OnItemTouchListener {

        void onCardViewTap(View view, int position);

        void onButton1Click(View view, int position);

        void onButton2Click(View view, int position);
    }

    public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
        private List<String> cards;
        private OnItemTouchListener onItemTouchListener;

        public CardViewAdapter(List<String> cards, OnItemTouchListener onItemTouchListener) {
            this.cards = cards;
            this.onItemTouchListener = onItemTouchListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_petient, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            //  viewHolder.sname.setText(cards.get(i));

            Picasso.with(getApplicationContext()).load("http://sysnet.utcc.ac.th/prefalls/images/patients/" + imgArray.get(i)).into(viewHolder.imgProfile);
            viewHolder.txtName.setText(nameArray.get(i));
            viewHolder.txtStatus.setText("Status: normal");

            viewHolder.constraintLayout.setBackgroundColor(Color.parseColor("#b3e0ff"));


        }

        @Override
        public int getItemCount() {
            return cards == null ? 0 : cards.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            //   private TextView sname;
            private TextView txtName;
            private CircleImageView imgProfile;
            private TextView txtStatus;
            private ConstraintLayout constraintLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                txtName = (TextView) itemView.findViewById(R.id.txtname);
                imgProfile = (CircleImageView) itemView.findViewById(R.id.img_profile);
                txtStatus = (TextView) itemView.findViewById(R.id.txtstatus);
                constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintBackground);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemTouchListener.onCardViewTap(v, getLayoutPosition());
                    }
                });
            }
        }
    }

    private class getPatientTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://sysnet.utcc.ac.th/prefalls/api/patients.jsp")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                Log.i(TAG, "S: " + result);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<PatientResponse>>() {
                }.getType();
                Collection<PatientResponse> enums = gson.fromJson(result, collectionType);
                PatientResponse[] res = enums.toArray(new PatientResponse[enums.size()]);

                Log.i(TAG, "" + res.length);
                for (int i = 0; i < res.length; i++) {
                    Log.i(TAG, "firstname" + res[i].getFirstname());
                    DBPetient dbPetient = new DBPetient(getApplicationContext());
                    nameArray.add(res[i].getFirstname() + " " + res[i].getLastname());
                    imgArray.add(res[i].getImgPath());
                    pidArray.add(res[i].getSSSN());
                    dbPetient.insertData(res[i].getSSSN(), res[i].getFirstname(), res[i].getLastname(), res[i].getNickname(), res[i].getSex(), res[i].getBirthday(), res[i].getAddress(), res[i].getImgPath(), res[i].getWeight(), res[i].getHeight(), res[i].getApparent(), res[i].getDiseases(), res[i].getMedicine(), res[i].getAllergicMed(), res[i].getAllergicFood(), res[i].getDoctorName(), res[i].getDoctorPhone(), res[i].getHospitalName(), res[i].getCousinName1(), res[i].getCousinPhone1(), res[i].getCousinRelation1(), res[i].getCousinName2(), res[i].getCousinPhone2(), res[i].getCousinRelation2(), res[i].getCousinName3(), res[i].getCousinPhone3(), res[i].getCousinRelation3());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mAdapter.notifyDataSetChanged();
            stopDialog();
        }
    }


    private class LoadAlertTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url("http://sysnet.utcc.ac.th/prefalls/api/alerttype.jsp")
                    .post(formBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                Log.i(TAG, "S: " + result);
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<AlertTypeResponse>>() {
                }.getType();
                Collection<AlertTypeResponse> enums = gson.fromJson(result, collectionType);
                AlertTypeResponse[] res = enums.toArray(new AlertTypeResponse[enums.size()]);

                Log.i(TAG, "alert_type: " + res.length);
                for (int i = 0; i < res.length; i++) {
                    DBAlertType dbAlertType = new DBAlertType(getApplicationContext());
                    dbAlertType.insertData(res[i].getAlertType(),res[i].getAlertName());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            stopDialog();
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String pid = intent.getStringExtra("pid").toString();
            int type = intent.getIntExtra("type", 0);
            Log.i(TAG, "getPIDFromService: " + pid);
            Log.i(TAG, "gettypeFromService: " + type);
        }
    };
    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_logout:
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}




