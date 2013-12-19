package com.cwat.nades;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.cwat.util.FriendsDataDistributor;
import com.cwat.util.UniversalHTTP;
import com.cwat.util.UserDAO;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by alexthornburg on 11/13/13.
 */
public class GCMIntentService extends IntentService {

    private NotificationManager mNotificationManager;
    static final String senderId = "717235139560";
    static final String server = "http://nades-game.elasticbeanstalk.com/";
    static Location gLocation;
    public static final int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;

    public GCMIntentService(){
        super(senderId);
    }


    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("error", "Received message");
        String message = intent.getExtras().getString("message");
        if(message.startsWith("FRIEND_LOCATION")){
            Log.e("Location", message);
            String[] splitMessage = message.split(" ");
            String location = splitMessage[1]+ " " +splitMessage[2];
            String username = splitMessage[3];
            Editor edit = getSharedPreferences("Location",0).edit();
            edit.putString(username, location);
            edit.commit();
        }else{
            String[] splitMessage = message.split(" ",2);
            if(splitMessage[1].equals("has sent you a friend request! Click here to respond.")){
                Editor edit = getSharedPreferences("messages",0).edit();
                edit.putString("message", message);
                edit.commit();
                sendNotification(message);
            }if(splitMessage[1].equals("has accepted your friend request.")){
                sendNotification(message);
                FriendsDataDistributor database = new FriendsDataDistributor(getApplicationContext());

                UniversalHTTP getID = new UniversalHTTP();
                try{
                   UserDAO id = getID.execute(server+"getID/"+splitMessage[0]+"/").get();
                    database.open();
                    if(!database.containsFriend(splitMessage[0])){
                        database.createFriend(splitMessage[0],id.getUsername());
                        database.close();
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Already Friends", Toast.LENGTH_LONG);
                        toast.show();
                        database.close();
                    }
                }catch(Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Couldn't get ID",Toast.LENGTH_LONG);
                    toast.show();
                }
            }if(splitMessage[0].equals("LOCATION")){
                LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                String provider = manager.getBestProvider(new Criteria(),true);
                gLocation = manager.getLastKnownLocation(provider);
                LocationListener listener = new LocationListener(){

                    @Override
                    public void onLocationChanged(Location location) {
                        gLocation = location;

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Toast.makeText(getApplicationContext(), "Provider Disabled", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onStatusChanged(String provider, int status,
                                                Bundle extras) {
                        // TODO Auto-generated method stub

                    }

                };
                manager.requestLocationUpdates(provider, 0, 10000, listener);
                ArrayList<NameValuePair> sParam = new ArrayList<NameValuePair>();
                sParam.add(new BasicNameValuePair("regID",splitMessage[1]));
                sParam.add(new BasicNameValuePair("message","FRIEND_LOCATION "+gLocation.getLongitude()
                        + " " + gLocation.getLatitude() + " "+getSharedPreferences("Login",1).getString("username", "")));
               UniversalHTTP sendRequest = new UniversalHTTP();
                try{
                    UserDAO success = sendRequest.execute(server + "send_message/"+splitMessage[1]+"/"+"FRIEND_LOCATION "+gLocation.getLongitude()
                            + " " + gLocation.getLatitude() + " "+getSharedPreferences("Login",1).getString("username", "")+"/").get();
                    Toast toast = Toast.makeText(getApplicationContext(),"REPLY"+success,Toast.LENGTH_LONG);
                    toast.show();
                }catch(Exception e){
                    Log.i("Request error","Request not sent");
                }

            }
        }

    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private void sendNotification(String message) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_drawer)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message))
                        .setContentText(message);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }

}
