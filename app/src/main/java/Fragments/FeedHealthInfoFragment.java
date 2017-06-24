package Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import Activity.R;
import SQLite.DBPetient;

import static Activity.ProfileActivity.PID;


public class FeedHealthInfoFragment extends Fragment {
    TextView mHeight;
    TextView mWeight;
    TextView mDiseases;
    TextView mMedicine;
    TextView mAllergicMed;
    TextView mAllergicFood;
    TextView mHospitalName;
    private String TAG = "FEEDHealthInfoFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed_health_info, container, false);


        mDiseases = (TextView) v.findViewById(R.id.diseases);
        mMedicine = (TextView) v.findViewById(R.id.medicine);
        mAllergicMed = (TextView) v.findViewById(R.id.AllergicMed);
        mAllergicFood = (TextView) v.findViewById(R.id.AllergicFood);
        mHospitalName = (TextView) v.findViewById(R.id.hospitalName);
        mHeight = (TextView) v.findViewById(R.id.height);
        mWeight = (TextView) v.findViewById(R.id.weight);
        DBPetient dbPetient = new DBPetient(getActivity());
        Cursor res = dbPetient.getAllDataEach(PID);
        if (res.getCount() == 0) {
            Log.i(TAG, "Nothing found");
        } else {
            while (res.moveToNext()) {
                String weight = res.getString(8);
                String height = res.getString(9);
                String diseases = res.getString(11);
                String medicine = res.getString(12);
                String AllergicMed = res.getString(13);
                String AllergicFood = res.getString(14);
                String hospitalName = res.getString(17);
                mHeight.setText(weight);
                mWeight.setText(height);
                mDiseases.setText(diseases);
                mMedicine.setText(medicine);
                mAllergicMed.setText(AllergicMed);
                mAllergicFood.setText(AllergicFood);
                mHospitalName.setText(hospitalName);
            }
        }


        return v;
    }


}
