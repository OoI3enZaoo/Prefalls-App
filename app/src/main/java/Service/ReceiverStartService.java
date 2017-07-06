package Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ben on 6/7/2560.
 */

public class ReceiverStartService extends BroadcastReceiver {

    private String TAG = "ReceiverStartService";
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent myIntent = new Intent(context, getAlertMessage.class);
        context.startService(myIntent);
        Log.i(TAG,"startServiceNa");


    }
}