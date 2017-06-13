package Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import SQLite.DBPetient;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppCompatActivity {

    private IntentFilter mIntentFilter;
    public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
    public static final String mBroadcastIntegerAction = "com.truiton.broadcast.integer";
    public String TAG = "NotificationActivity";
    ArrayList<String> mNameUserArray = new ArrayList<>();
    ArrayList<String> mNameTypeArray = new ArrayList<>();
    ArrayList<String> mImagePathArray = new ArrayList<>();
    ArrayList<String> mTimeArray= new ArrayList<>();
    public static CardViewAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
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
            public void onButton1Click(View view, int position) {
                Toast.makeText(getApplicationContext(), "Clicked Button1 in " + mNameUserArray.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onButton2Click(View view, int position) {
                Toast.makeText(getApplicationContext(), "Clicked Button2 in " + mNameUserArray.get(position), Toast.LENGTH_SHORT).show();
            }
        };
        mAdapter = new CardViewAdapter(mNameUserArray, itemTouchListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_alertlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();



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
            String fullname = intent.getStringExtra("fullname").toString();
            String typename = intent.getStringExtra("typename").toString();

            DBPetient dbPetient = new DBPetient(getApplicationContext());
            String imagepath = dbPetient.getImagepath(pid);


            Log.i(TAG, "pid: " + pid);
            Log.i(TAG, "fullname: " + fullname);
            Log.i(TAG, "typename: " + typename);
            Log.i(TAG, "imagepath: " + imagepath);

            mNameUserArray.add(fullname);
            mNameTypeArray.add(typename);
            mImagePathArray.add(imagepath);
            mTimeArray.add("time");
            mAdapter.notifyDataSetChanged();
        }
    };
    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
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
            viewHolder.txtStatus.setText("Status: normal");
            viewHolder.txtTstamp.setText("time");
            viewHolder.txtStatus.setText(mNameTypeArray.get(i));
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
            private TextView txtTstamp;
            private ConstraintLayout constraintLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                txtName = (TextView) itemView.findViewById(R.id.txtname);
                imgProfile = (CircleImageView) itemView.findViewById(R.id.img_profile);
                txtStatus = (TextView) itemView.findViewById(R.id.txtstatus);
                constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintBackground);
                txtTstamp = (TextView)itemView.findViewById(R.id.tstamp);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemTouchListener.onCardViewTap(v, getLayoutPosition());
                    }
                });
            }
        }
    }
    interface OnItemTouchListener {

        void onCardViewTap(View view, int position);

        void onButton1Click(View view, int position);

        void onButton2Click(View view, int position);
    }
}
