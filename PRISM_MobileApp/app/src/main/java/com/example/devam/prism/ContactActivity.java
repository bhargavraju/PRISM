package com.example.devam.prism;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    ListView listView ;
    ArrayList<String> StoreContacts ;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor ;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = (ListView)findViewById(R.id.listView2);
        StoreContacts = new ArrayList<String>();

        EnableRuntimePermission();

        GetContactsIntoArrayList();

        arrayAdapter = new ArrayAdapter<String>(
                ContactActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                StoreContacts
        );

        listView.setAdapter(arrayAdapter);


    }

    public void GetContactsIntoArrayList(){
        String str="";

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            StoreContacts.add(name + " "  + "\n" + " " + phonenumber);

            str+=name+ "~"+phonenumber+"\n";

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
                        Toast.makeText(ContactActivity.this, "Couldn't register, retry!! ",
                                Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ContactRequest obj=new ContactRequest(str,responseListener);
        RequestQueue queue= Volley.newRequestQueue(ContactActivity.this);
        queue.add(obj);

        cursor.close();

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ContactActivity.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(ContactActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ContactActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {



                } else {

                    Toast.makeText(ContactActivity.this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }



}
