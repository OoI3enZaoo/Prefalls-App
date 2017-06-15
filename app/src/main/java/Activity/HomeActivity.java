package Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import Fragments.FeedActivityFragment;
import Fragments.FeedMapFragment;

import java.util.ArrayList;

import Fragments.FeedGaugeFragment;
import devlight.io.library.ntb.NavigationTabBar;

public class HomeActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    protected String TAG = "HomeActivity";
    public static String PID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        getSupportActionBar().setTitle("Prefalls-Alert");
        Intent intent = getIntent();
        PID = intent.getStringExtra("pid");
        Log.i(TAG,"PID:  " + PID);
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.navigateTabBar);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_gauge),
                        Color.parseColor("#F5F5F5"))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_sixth))
                        .title("Fall Risk")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_activity),
                        Color.parseColor("#F5F5F5"))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Current Acitivity")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_map),
                        Color.parseColor("#F5F5F5"))
                        // .selectedIcon(getResources().getDrawable(R.drawable.ic_seventh))
                        .title("Map")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, 0);
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);


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
                    return new FeedGaugeFragment();
                case 1:
                    return new FeedActivityFragment();
                case 2:
                    return new FeedMapFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            return 3;
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_alert:
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type",PID);
                intent.putExtras(bundle);
                startActivity(intent);

                return true;

           /* case R.id.icon_logout:
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

}
