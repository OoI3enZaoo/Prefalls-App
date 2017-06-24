package Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import Activity.R;
import SQLite.DBPetient;

import static Activity.ProfileActivity.PID;


public class FeedContactFragment extends Fragment {

    TextView mCousinName1;
    TextView mCousinPhone1;
    TextView mCousinRelation1;
    TextView mCousinName2;
    TextView mCousinPhone2;
    TextView mCousinRelation2;
    TextView mCousinName3;
    TextView mCousinPhone3;
    TextView mCousinRelation3;
    ImageButton user1;
    ImageButton user2;
    ImageButton user3;

    private String TAG = "FeedContactFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact, container, false);

        mCousinName1 = (TextView) v.findViewById(R.id.cousinName1);
        mCousinPhone1 = (TextView) v.findViewById(R.id.cousinPhone1);
        mCousinRelation1 = (TextView) v.findViewById(R.id.cousinRelation1);
        mCousinName2 = (TextView) v.findViewById(R.id.cousinName2);
        mCousinPhone2 = (TextView) v.findViewById(R.id.cousinPhone2);
        mCousinRelation2 = (TextView) v.findViewById(R.id.cousinRelation2);
        mCousinName3 = (TextView) v.findViewById(R.id.cousinName3);
        mCousinPhone3 = (TextView) v.findViewById(R.id.cousinPhone3);
        mCousinRelation3 = (TextView) v.findViewById(R.id.cousinRelation3);
        user1 = (ImageButton)v.findViewById(R.id.user1) ;
        user2 = (ImageButton)v.findViewById(R.id.user2) ;
        user3 = (ImageButton)v.findViewById(R.id.user3) ;
        DBPetient dbPetient = new DBPetient(getActivity());
        Cursor res = dbPetient.getAllDataEach(PID);
        if (res.getCount() == 0) {
            Log.i(TAG, "Nothing found");

        } else {
            while (res.moveToNext()) {

                String cousinName1 = res.getString(18);
                String cousinPhone1 = res.getString(19);
                String cousinRelation1 = res.getString(20);
                String cousinName2 = res.getString(21);
                String cousinPhone2 = res.getString(22);
                String cousinRelation2 = res.getString(23);
                String cousinName3 = res.getString(24);
                String cousinPhone3 = res.getString(25);
                String cousinRelation3 = res.getString(26);

                mCousinName1.setText(cousinName1);
                mCousinPhone1.setText(cousinPhone1);
                mCousinRelation1.setText(cousinRelation1);
                mCousinName2.setText(cousinName2);
                mCousinPhone2.setText(cousinPhone2);
                mCousinRelation2.setText(cousinRelation2);
                mCousinName3.setText(cousinName3);
                mCousinPhone3.setText(cousinPhone3);
                mCousinRelation3.setText(cousinRelation3);

            }
        }

        return v;
    }
}
