package Fragments;


import android.database.Cursor;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedPatientInfoFragment extends Fragment {

    private String TAG = "FeedPatientInfoFragment";
    TextView mSex;
    TextView mBirthday;
    TextView mAddress;

    public FeedPatientInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_patient_info, container, false);
        mSex = (TextView)v.findViewById(R.id.sex);
        mBirthday = (TextView)v.findViewById(R.id.birthday);
        mAddress = (TextView)v.findViewById(R.id.address);

        DBPetient dbPetient = new DBPetient(getActivity());
        Cursor res = dbPetient.getAllDataEach(PID);
        if (res.getCount() == 0) {
            Log.i(TAG, "Nothing found");
        } else {
            while (res.moveToNext()) {
                String sex = res.getString(4);
                String birthday = res.getString(5);
                String address = res.getString(6);
                mSex.setText(sex);
                mBirthday.setText(birthday);
                mAddress.setText(address);

            }
        }
        return v;
    }

}
