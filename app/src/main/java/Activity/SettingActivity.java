package Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import SQLite.DBUser;

public class SettingActivity extends AppCompatActivity {

    CheckBox mAlert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Setting");

        mAlert = (CheckBox)findViewById(R.id.chkNotification);
        DBUser dbUser = new DBUser(getApplicationContext());
        int alert = dbUser.getAlert();
        if(alert == 1){
            mAlert.setChecked(true);
        }else{
            mAlert.setChecked(false);
        }
        mAlert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DBUser dbUser = new DBUser(getApplicationContext());
                    dbUser.updateAlert(1);
                }else{
                    DBUser dbUser = new DBUser(getApplicationContext());
                    dbUser.updateAlert(0);
                }
            }
        });
    }
}
