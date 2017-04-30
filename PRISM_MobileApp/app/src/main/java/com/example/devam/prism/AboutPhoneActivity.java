package com.example.devam.prism;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class AboutPhoneActivity extends AppCompatActivity {


    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_phone);
        t1=(TextView)findViewById(R.id.textView4);
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);


        String info="";
        info+="IMEI:"+telephonyManager.getDeviceId();
        info+=" OS Version: " + System.getProperty("os.version");
        info+="\n Brand: "+ Build.BRAND;
        info += "\n Android Version: "+android.os.Build.VERSION.RELEASE + "("+android.os.Build.VERSION.SDK_INT+")";
        info += "\n Device: " + android.os.Build.DEVICE;
        info += "\n Model: " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";

        t1.setText(info);
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


}
