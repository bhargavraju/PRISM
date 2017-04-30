package com.example.devam.prism;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by devam on 4/18/2017.
 */
public class PhoneStateReceiver extends BroadcastReceiver {
    static AudioRecorder obj=new AudioRecorder("recorded_audio");
    static boolean flag=false;
    static byte[] audio;

    public static void  setNull()
    {
        audio=null;
    }
    public static byte[] getStringFromFile (String filePath) throws Exception {
        File file = new File(filePath);
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return bytesArray;
    }

    public void onReceive(Context context, Intent intent) {

        try {
            System.out.println("Receiver start");
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                System.out.println("audio file in string:");
                Toast.makeText(context,"Ringing state",Toast.LENGTH_SHORT).show();
               // flag=true;
                System.out.println("id"+obj.toString());
                obj.start();
                /*Thread.sleep(20000);
                obj.stop();*/

            }
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){
                Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
                System.out.println("id"+obj.toString());
                obj.stop();
                audio=getStringFromFile("/storage/emulated/0/recorded_audio.3gp");

                System.out.println("audio file in string:"+audio);
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
