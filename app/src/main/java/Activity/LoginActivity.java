package Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import Activity.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import DataResponse.MemberResponse;
import SQLite.DBUser;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class LoginActivity extends AppCompatActivity {
    EditText editUser;
    EditText editPwd;
    String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUser = (EditText) findViewById(R.id.editUser);
        editPwd = (EditText) findViewById(R.id.editPwd);
    }

    public void CheckLogin(View v) {
        if (!editUser.getText().equals("") && !editPwd.getText().equals("")) {

            new CheckLoginTask(getApplicationContext()).execute(editUser.getText().toString().trim(), editPwd.getText().toString().trim());
        }

    }
}

class CheckLoginTask extends AsyncTask<String, Void, String> {
    private Context mContext;

    CheckLoginTask(Context Context){
        mContext = Context;
    }
    String TAG = "LoginActivity";
    @Override
    protected String doInBackground(String... params) {
        String user = params[0];
        String pwd = params[1];
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("user_email", user)
                .add("user_pwd", pwd)
                .build();
        Request request = new Request.Builder()
                .url("http://sysnet.utcc.ac.th/prefalls/api/checkLogin2.jsp")
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            Log.i(TAG,"S: " + result);
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<MemberResponse>>() {
            }.getType();
            Collection<MemberResponse> enums = gson.fromJson(result, collectionType);
            MemberResponse[] result1 = enums.toArray(new MemberResponse[enums.size()]);
            if (result1[0].getCnt().equals("0")) {
                return "0";
            } else {
               String fullname = result1[0].getFname() + " " + result1[0].getLname();

                DBUser db = new DBUser(mContext);
                db.updateStatus(1);
               /* db.updateName(fullname);*/

                return "1";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i(TAG,"S: " + result);
        if(result.equals("1")){
            Intent intent2 = new Intent(mContext,HomeActivity.class);
            mContext.startActivity(intent2);
            Toast.makeText(mContext, "Successful Login", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(mContext, "Username or Password Incorrect. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
