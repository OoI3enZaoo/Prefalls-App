package Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import SQLite.DBPetient;

public class ProfileActivity extends AppCompatActivity {

    public static String PID = "";
    public static String TAG = "ProfileActivity";
    DBPetient dbPetient;
    TextView mFirstname;
    TextView mLastname;
    TextView mNickname2;
    TextView mSex;
    TextView mBirthday;
    TextView mAddress;
    TextView mImgPath;
    TextView mWeight;
    TextView mHeight;
    TextView mApparent;
    TextView mDiseases;
    TextView mMedicine;
    TextView mAllergicMed;
    TextView mAllergicFood;
    TextView mDoctorName;
    TextView mDoctorPhone;
    TextView mHospitalName;
    TextView mCousinName1;
    TextView mCousinPhone1;
    TextView mCousinRelation1;
    TextView mCousinName2;
    TextView mCousinPhone2;
    TextView mCousinRelation2;
    TextView mCousinName3;
    TextView mCousinPhone3;
    TextView mCousinRelation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbPetient = new DBPetient(getApplicationContext());
        Intent intent = getIntent();
        PID = intent.getStringExtra("pid");
        String fullname = dbPetient.getFullName(PID);
        String nicknam = dbPetient.getNickName(PID);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Profile of: " + fullname + "(" + nicknam + ")</font>"));
        Cursor res = dbPetient.getAllDataEach(PID);
        if (res.getCount() == 0) {
            Log.i(TAG, "Nothing found");
        } else {
            while (res.moveToNext()) {
                String firstname = res.getString(1);
                String lastname = res.getString(2);
                String nickname2 = res.getString(3);
                String sex = res.getString(4);
                String birthday = res.getString(5);
                String address = res.getString(6);
                String imgPath = res.getString(7);
                String weight = res.getString(8);
                String height = res.getString(9);
                String apparent = res.getString(10);
                String diseases = res.getString(11);
                String medicine = res.getString(12);
                String AllergicMed = res.getString(13);
                String AllergicFood = res.getString(14);
                String doctorName = res.getString(15);
                String doctorPhone = res.getString(16);
                String hospitalName = res.getString(17);
                String cousinName1 = res.getString(18);
                String cousinPhone1 = res.getString(19);
                String cousinRelation1 = res.getString(20);
                String cousinName2 = res.getString(21);
                String cousinPhone2 = res.getString(22);
                String cousinRelation2 = res.getString(23);
                String cousinName3 = res.getString(24);
                String cousinPhone3 = res.getString(25);
                String cousinRelation3 = res.getString(26);
                mFirstname = (TextView) findViewById(R.id.firstname);
                mLastname = (TextView) findViewById(R.id.lastname);
                mNickname2 = (TextView) findViewById(R.id.nickname2);
                mSex = (TextView) findViewById(R.id.sex);
                mBirthday = (TextView) findViewById(R.id.birthday);
                mAddress = (TextView) findViewById(R.id.address);
                /*mImgPath = (TextView)findViewById(R.id.imgPath);
                mWeight = (TextView)findViewById(R.id.weight);
                mHeight = (TextView)findViewById(R.id.height);
                mApparent = (TextView)findViewById(R.id.apparent);
                mDiseases = (TextView)findViewById(R.id.diseases);
                mMedicine = (TextView)findViewById(R.id.medicine);
                mAllergicMed = (TextView)findViewById(R.id.AllergicMed);
                mAllergicFood = (TextView)findViewById(R.id.AllergicFood);
                mDoctorName = (TextView)findViewById(R.id.doctorName);
                mDoctorPhone = (TextView)findViewById(R.id.doctorPhone);
                mHospitalName = (TextView)findViewById(R.id.hospitalName);
                mCousinName1 = (TextView)findViewById(R.id.cousinName1);
                mCousinPhone1 = (TextView)findViewById(R.id.cousinPhone1);
                mCousinRelation1 = (TextView)findViewById(R.id.cousinRelation1);
                mCousinName2 = (TextView)findViewById(R.id.cousinName2);
                mCousinPhone2 = (TextView)findViewById(R.id.cousinPhone2);
                mCousinRelation2 = (TextView)findViewById(R.id.cousinRelation2);
                mCousinName3 = (TextView)findViewById(R.id.cousinName3);
                mCousinPhone3 = (TextView)findViewById(R.id.cousinPhone3);
                mCousinRelation3 = (TextView)findViewById(R.id.cousinRelation3);*/

                mFirstname.setText(firstname);
                mLastname.setText(lastname);
                mNickname2.setText(nickname2);
                mSex.setText(sex);
                mBirthday.setText(birthday);
                mAddress.setText(address);
            }
        }


    }
}
