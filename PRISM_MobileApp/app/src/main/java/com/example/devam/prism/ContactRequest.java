package com.example.devam.prism;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by devam on 3/29/2017.
 */
public class ContactRequest extends StringRequest {
    public static final String REGISTER_REQUEST_URL="http://10.42.0.253:8080/prism/rest/track/contacts";
    public Map<String,String> params;

    public ContactRequest(String contacts, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params=new HashMap<>();
        params.put("contacts",contacts);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
