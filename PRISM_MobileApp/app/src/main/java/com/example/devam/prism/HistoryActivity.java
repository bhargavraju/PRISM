package com.example.devam.prism;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        TextView view = (TextView) findViewById(R.id.textView3);
        String[] projection = new String[] {
                BookmarkColumns.TITLE
                , BookmarkColumns.URL
        };
        Cursor mCur = managedQuery(BookmarkColumns.BOOKMARKS_URI,
                projection, null, null, null
        );
        mCur.moveToFirst();
        int titleIdx = mCur.getColumnIndex(BookmarkColumns.TITLE);
        int urlIdx = mCur.getColumnIndex(BookmarkColumns.URL);
        while (mCur.isAfterLast() == false) {
            view.append("n" + mCur.getString(titleIdx));
            view.append("n" + mCur.getString(urlIdx));
            mCur.moveToNext();
        }

    }

    /*private void requestBrowserPermission() {
        String permission = com.android.chrome.;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }*/

}
