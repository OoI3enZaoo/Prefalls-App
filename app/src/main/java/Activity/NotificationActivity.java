package Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import SQLite.DBAlert;
import SQLite.DBPetient;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
    public static final String mBroadcastIntegerAction = "com.truiton.broadcast.integer";
    public String TAG = "NotificationActivity";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<String> mPidArray = new ArrayList<>();
    ArrayList<String> mNameUserArray = new ArrayList<>();
    ArrayList<String> mNameTypeArray = new ArrayList<>();
    ArrayList<String> mImagePathArray = new ArrayList<>();
    ArrayList<String> mTimeArray = new ArrayList<>();
    ArrayList<String> mLatArray = new ArrayList<>();
    ArrayList<String> mLngArray = new ArrayList<>();
    ArrayList<String> mColorArray = new ArrayList<>();
    public static CardViewAdapter mAdapter;
    public String PID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='000000'>Notification</font>"));
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout) ;

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Log.i(TAG, "TYPE: " + type);
        if (type.equals("all")) {
            PID = null;
           // Toast.makeText(getApplicationContext(), "all", Toast.LENGTH_SHORT).show();
        } else {

            PID = type;
          //  Toast.makeText(getApplicationContext(), "each", Toast.LENGTH_SHORT).show();
        }
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastStringAction);
        mIntentFilter.addAction(mBroadcastIntegerAction);
        OnItemTouchListener itemTouchListener = new OnItemTouchListener() {
            @Override
            public void onCardViewTap(View view, int position) {
                /*Toast.makeText(getApplicationContext(), "Tapped " + pidArray.get(position) + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pid",pidArray.get(position));
                intent.putExtras(bundle);
                startActivity(intent);*/

            }

            @Override
            public void onLocationClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Clicked Button1 in " + mNameUserArray.get(position), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("pid", mPidArray.get(position));
                bundle.putString("time", mTimeArray.get(position));
                Log.i(TAG, "PID: " + mPidArray.get(position));
                Log.i(TAG, "Time: " + mTimeArray.get(position));
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onAppClick(View view, int position) {
               Intent intent = new Intent(getApplicationContext(),PetientListActivity.class);
                startActivity(intent);
            }
        };
        mAdapter = new CardViewAdapter(mNameUserArray, itemTouchListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_alertlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });


    }
    public void refreshItems() {
        LoadAlert();
        onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        //registerReceiver(mReceiver, mIntentFilter);
        Log.i(TAG, "onReSume()");
       LoadAlert();
    }

    private void LoadAlert() {
        mNameUserArray.clear();
        mNameTypeArray.clear();
        mPidArray.clear();
        mImagePathArray.clear();
        mTimeArray.clear();
        mLatArray.clear();
        mLngArray.clear();
        mColorArray.clear();
        DBAlert dbAlert = new DBAlert(getApplicationContext());
        if (PID == null) {
            Log.i(TAG, "PID == null");

            Cursor res = dbAlert.getAllData();

            Log.i(TAG, "res.getCount() " + res.getCount());
            if (res.getCount() == 0) {
                Log.i(TAG, "Nothing found");
            } else {
                while (res.moveToNext()) {

                    mPidArray.add(res.getString(0));
                    mNameUserArray.add(res.getString(1));
                    mNameTypeArray.add(res.getString(2));
                    mImagePathArray.add(res.getString(3));
                    mTimeArray.add(res.getString(4));
                    mLatArray.add(res.getString(5));
                    mLngArray.add(res.getString(6));
                    mColorArray.add(res.getString(7));
                }
            }

        } else {
            Cursor res = dbAlert.getAllDataEach(PID,15);

            Log.i(TAG, "dbAlertEachOne.getCount(): " + res.getCount());
            if (res.getCount() == 0) {
                Log.i(TAG, "DBAlertEachOne == 0");
            } else {
                while (res.moveToNext()) {
                    mPidArray.add(res.getString(0));
                    mNameUserArray.add(res.getString(1));
                    mNameTypeArray.add(res.getString(2));
                    mImagePathArray.add(res.getString(3));
                    mTimeArray.add(res.getString(4));
                    mLatArray.add(res.getString(5));
                    mLngArray.add(res.getString(6));
                    mColorArray.add(res.getString(7));
                }
            }
            Toast.makeText(this, "PID:" + PID, Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String pid = intent.getStringExtra("pid").toString();
            String fullname = intent.getStringExtra("fullname").toString();
            String typename = intent.getStringExtra("typename").toString();
            String timestart = intent.getStringExtra("timestart").toString();
            DBPetient dbPetient = new DBPetient(getApplicationContext());
            String imagepath = dbPetient.getImagepath(pid);


            Log.i(TAG, "pid: " + pid);
            Log.i(TAG, "fullname: " + fullname);
            Log.i(TAG, "typename: " + typename);
            Log.i(TAG, "imagepath: " + imagepath);
            Log.i(TAG, "timestart: " + timestart);


        }
    };

    @Override
    protected void onPause() {
//        unregisterReceiver(mReceiver);
        super.onPause();
    }


    public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
        private List<String> cards;
        private OnItemTouchListener onItemTouchListener;

        public CardViewAdapter(List<String> cards, OnItemTouchListener onItemTouchListener) {
            this.cards = cards;
            this.onItemTouchListener = onItemTouchListener;
        }

        @Override
        public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alert, viewGroup, false);
            return new CardViewAdapter.ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(CardViewAdapter.ViewHolder viewHolder, int i) {
            //  viewHolder.sname.setText(cards.get(i));

            Picasso.with(getApplicationContext()).load("http://sysnet.utcc.ac.th/prefalls/images/patients/" + mImagePathArray.get(i)).into(viewHolder.imgProfile);
            viewHolder.txtName.setText(mNameUserArray.get(i));

            /*Long time = Long.parseLong(mTimeArray.get(i));
            Date date = new Date(time*1000L); // *1000 is to convert seconds to milliseconds
            String sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date);*/

            viewHolder.txtTstamp.setText(mTimeArray.get(i));
            Log.i(TAG,"getString "+mTimeArray.get(i));

            viewHolder.txtStatus.setEllipsize(TextUtils.TruncateAt.END);
            viewHolder.txtStatus.setMaxLines(2);

            if(mNameTypeArray.get(i).length() >= 45){
                // statusArray.add(alertName.substring(0,30) + "\n" + "HELLO");
                viewHolder.txtStatus.setText(mNameTypeArray.get(i).substring(0,41) + "\n" + mNameTypeArray.get(i).substring(41,mNameTypeArray.get(i).length()));
            }else{
                viewHolder.txtStatus.setText(mNameTypeArray.get(i));
            }

            viewHolder.constraintLayout.setBackgroundColor(Color.parseColor(mColorArray.get(i)));


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
            private TextView txtTstamp;
            private ConstraintLayout constraintLayout;
            private ImageButton btnLocation;
            private ImageButton btnApp;

            public ViewHolder(View itemView) {
                super(itemView);
                txtName = (TextView) itemView.findViewById(R.id.txtname);
                imgProfile = (CircleImageView) itemView.findViewById(R.id.img_profile);
                txtStatus = (TextView) itemView.findViewById(R.id.txtstatus);
                constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintBackground);
                txtTstamp = (TextView) itemView.findViewById(R.id.tstamp);
                btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);
                btnApp = (ImageButton)itemView.findViewById(R.id.btnApp);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemTouchListener.onCardViewTap(v, getLayoutPosition());
                    }
                });
                btnLocation.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        /*int position=(Integer)v.getTag();
                        Log.i(TAG,"position: " + position);*/
                        onItemTouchListener.onLocationClick(v, getLayoutPosition());

                    }
                });

                btnApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemTouchListener.onAppClick(v, getLayoutPosition());
                    }
                });
            }
        }
    }

    interface OnItemTouchListener {

        void onCardViewTap(View view, int position);

        void onLocationClick(View view, int position);

        void onAppClick(View view, int position);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_bin:

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(NotificationActivity.this);
                builder.setMessage("Are you sure you want to remove all notification?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(),
                                "Remove", Toast.LENGTH_SHORT).show();
                        mPidArray.clear();
                        mNameUserArray.clear();
                        mNameTypeArray.clear();
                        mImagePathArray.clear();
                        mTimeArray.clear();
                        mLatArray.clear();
                        mLngArray.clear();
                        mColorArray.clear();
                        mAdapter.notifyDataSetChanged();
                        DBAlert dbAlert = new DBAlert(getApplicationContext());
                        if(PID == null){
                            dbAlert.deleteData();
                        }else{

                            dbAlert.deleteDataEach(PID);
                        }





                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }
}
