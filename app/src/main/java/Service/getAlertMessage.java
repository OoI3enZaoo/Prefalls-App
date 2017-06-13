package Service;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import net.sf.xenqtt.client.AsyncClientListener;
import net.sf.xenqtt.client.AsyncMqttClient;
import net.sf.xenqtt.client.MqttClient;
import net.sf.xenqtt.client.PublishMessage;
import net.sf.xenqtt.client.Subscription;
import net.sf.xenqtt.message.ConnectReturnCode;
import net.sf.xenqtt.message.QoS;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import Activity.NotificationActivity;
import Activity.PetientListActivity;
import Activity.R;
import DataResponse.AlertEvent;
import SQLite.DBAlertType;
import SQLite.DBPetient;

/**
 * Created by Ben on 11/6/2560.
 */

public class getAlertMessage extends Service {

    private String TAG = "getAlertMessage";
    private String mqttBrokerURL = "tcp://sysnet.utcc.ac.th:1883";
    private String mqttUser = "admin";
    private String mqttPwd = "admin";
    private Hashtable<String, GetMqttThread> mqttThreadHT = new Hashtable<String, GetMqttThread>();
    private String sssn = "admin";
    //private String topic = "RFG2D3T6ET_alert";
    private ArrayList<String> sssnArray = new ArrayList<>();
    private int vibratePeriod = 800;
    int countalert = 1;
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG,"onCreate");
        CreateTopic();


    }

    private void CreateTopic() {

        Log.i(TAG,"CreateTopic");
        DBPetient dbPetient = new DBPetient(getApplication());
        Cursor res = dbPetient.getAllData();
        Log.i(TAG,"res: " + res.getCount());
        while (res.moveToNext()) {
            String topic = res.getString(0)+"_alert";//sssn
            Log.i(TAG,"Topic: " + topic);
            GetMqttThread mqttThread = createMQTTThread(sssn, topic);
            mqttThread.start();
            mqttThreadHT.put(sssn, mqttThread);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i(TAG, "Level memory: " + level);

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "In onTaskRemoved");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private GetMqttThread createMQTTThread(final String sssn, final String topic) {
        return new GetMqttThread() {
            @Override
            public void createListener() {
                Log.i(TAG, "createListener");

                // TODO Auto-generated method stub
                final CountDownLatch connectLatch = new CountDownLatch(1);
                final AtomicReference<ConnectReturnCode> connectReturnCode = new AtomicReference<ConnectReturnCode>();
                mqttListener = new AsyncClientListener() {
                    @Override
                    public void publishReceived(MqttClient client, final PublishMessage message) {

                        final PublishMessage msg = message;

                        DataResponse.AlertEvent event = parseXML(msg.getPayloadString());
                        String pid =  event.getPid();
                        int type = event.getType();
                        Log.i(TAG,"get start: "+event.getStart());
                        Log.i(TAG,"get end: "+event.getEnd());
                        Log.i(TAG,"get pid: "+event.getPid());
                        Log.i(TAG,"get type: " + event.getType());
                        //notification

                        if(type == 3 || type == 4 || type == 7 || type == 8 || type ==9){
                            Notification(pid,type);
                            SendBrodcast(pid,type);
                        }




                        message.ack();
                    }

                    @Override
                    public void disconnected(MqttClient client, Throwable cause, boolean reconnecting) {

                        if (cause != null) {
                            Log.i(TAG, "Disconnected from the broker due to an exception - " + cause);
                        } else {

                            Log.i(TAG, "Disconnected from the broker.");
                        }
                        if (reconnecting) {

                            Log.i(TAG, "Attempting to reconnect to the broker.");
                        }
                    }

                    @Override
                    public void connected(MqttClient client, ConnectReturnCode returnCode) {
                        Log.i(TAG, "connected");
                        connectReturnCode.set(returnCode);
                        connectLatch.countDown();
                    }

                    @Override
                    public void published(MqttClient arg0, PublishMessage arg1) {
                        // TODO Auto-generated method stub
                        Log.i(TAG, "published");

                    }

                    @Override
                    public void subscribed(MqttClient arg0,
                                           Subscription[] arg1, Subscription[] arg2,
                                           boolean arg3) {
                        // TODO Auto-generated method stub
                        Log.i("ben", "subscribed");

                    }

                    @Override
                    public void unsubscribed(MqttClient arg0, String[] arg1) {
                        // TODO Auto-generated method stub
                        Log.i(TAG, "unsubscribed");

                    }
                };
            }//end createListener

            public void createClient() {
                // TODO Auto-generated method stub
                Log.i(TAG, "createClient");
                mqttClient = new AsyncMqttClient(mqttBrokerURL, mqttListener, mqttHandlerThreadPoolSize);
                try {
                    Log.i(TAG, "createClient in try");
                    mqttClient.connect(topic, true, mqttUser, mqttPwd);
                    List<Subscription> subscriptions = new ArrayList<Subscription>();
                    subscriptions.add(new Subscription(topic, QoS.AT_MOST_ONCE));
                    mqttClient.subscribe(subscriptions);

                } catch (Exception e) {
                    Log.i(TAG, "An exception prevented the publishing of the full catalog." + e);
                }
            }//end createClient

        };//end new SetMqttThread

    }//end createMQTTThread()
    private AlertEvent parseXML(String xmlText) {
        Log.i(TAG, "parseXML" );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;
        AlertEvent event = new AlertEvent();
        try {
            builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlText));
            doc =  builder.parse(is);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            Log.i(TAG, "intry: ");

            XPathExpression expr = xpath.compile("/alert-event/@start");
            String start = ((String) expr.evaluate(doc, XPathConstants.STRING)).trim();
            event.setStart(Long.parseLong(start));


            expr = xpath.compile("/alert-event/@end");
            String end = ((String) expr.evaluate(doc, XPathConstants.STRING)).trim();
            event.setEnd(Long.parseLong(end));

            expr = xpath.compile("/alert-event/@pid");
            String sssn = ((String) expr.evaluate(doc, XPathConstants.STRING)).trim();
            event.setPid(sssn);


            expr = xpath.compile("/alert-event/@type");
            String alert_type = ((String) expr.evaluate(doc, XPathConstants.STRING)).trim();
            event.setType(Integer.parseInt(alert_type));
        } catch (Exception e) {System.out.println("Exception in parseXML:"+e);}

        return event;
    }
    public void Notification(String pid , int type){

        DBPetient dbpetient = new DBPetient(getApplicationContext());
        String fullname = dbpetient.getFullName(pid);
            Log.i(TAG,"TYPE: " + type);
        DBAlertType dbAlertType = new DBAlertType(getApplicationContext());
        String typename = dbAlertType.getAlertType(type);
        String message = fullname + " " + typename;
        String title = "Prefalls Notification";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_alert)
                .setTicker(title)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
        .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
        .setStyle(new NotificationCompat.BigTextStyle().bigText(message));


        Intent resultIntent = new Intent(this, PetientListActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int mId =1;
        mId++;
        mNotificationManager.notify(mId, mBuilder.build());
        //xxxxx add vibration xxxxx
        Vibrator v2 = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        v2.vibrate(vibratePeriod);

    }
    public void SendBrodcast(String pid , int type){
        DBAlertType dbAlertType = new DBAlertType(getApplicationContext());
        String typename = dbAlertType.getAlertType(type);
        DBPetient dbpetient = new DBPetient(getApplicationContext());
        String fullname = dbpetient.getFullName(pid);
        String imagepath = dbpetient.getImagepath(pid);

        //--send broadcast
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(PetientListActivity.mBroadcastStringAction);
        broadcastIntent.putExtra("pid", pid);
        broadcastIntent.putExtra("typename", typename);
        sendBroadcast(broadcastIntent);



        Intent broadcastIntent2 = new Intent();
        broadcastIntent2.setAction(NotificationActivity.mBroadcastStringAction);
        broadcastIntent2.putExtra("pid", pid);
        broadcastIntent2.putExtra("typename", typename);
        broadcastIntent2.putExtra("fullname", fullname);
        broadcastIntent2.putExtra("imagepath", imagepath);
        sendBroadcast(broadcastIntent2);

    }
}