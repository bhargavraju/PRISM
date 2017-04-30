package com.example.devam.prism;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by devam on 3/29/2017.
 */
public class CallRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL="http://10.42.0.253:8080/prism/rest/track/call_log";
    public Map<String,String> params;

    public CallRequest(String call_log, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("call_log",call_log);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
