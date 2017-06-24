package Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import Fragments.FeedContactFragment;
import Fragments.FeedHealthInfoFragment;
import Fragments.FeedPatientInfoFragment;
import SQLite.DBPetient;
import de.hdodenhof.circleimageview.CircleImageView;

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
    CircleImageView mImgPath;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mFirstname = (TextView) findViewById(R.id.firstname);
        mLastname = (TextView) findViewById(R.id.lastname);
        mImgPath = (CircleImageView) findViewById(R.id.img_profile);
        tabLayout.addTab(tabLayout.newTab().setText("Patient information"));
        tabLayout.addTab(tabLayout.newTab().setText("Health information"));
        tabLayout.addTab(tabLayout.newTab().setText("Contact"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        dbPetient = new DBPetient(getApplicationContext());
        Intent intent = getIntent();
        PID = intent.getStringExtra("pid");
        Cursor res = dbPetient.getAllDataEach(PID);
        if (res.getCount() == 0) {
            Log.i(TAG, "Nothing found");
        } else {
            while (res.moveToNext()) {
                String firstname = res.getString(1);
                String lastname = res.getString(2);
                String imgPath = res.getString(7);
                mFirstname.setText(firstname);
                mLastname.setText(lastname);
                Picasso.with(getApplicationContext()).load("http://sysnet.utcc.ac.th/prefalls/images/patients/" + imgPath).into(mImgPath);
            }
        }
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new FeedPatientInfoFragment();
                case 1:
                    return new FeedHealthInfoFragment();
                case 2:
                    return new FeedContactFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
