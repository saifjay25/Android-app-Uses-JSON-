package com.datechnologies.androidtest.login;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by saifjame on 1/11/19.
 */

public class singleton {
    private static singleton instance;
    private RequestQueue request;
    private static Context cont;
    //constructor for this class
    private singleton(Context context){
        cont=context;
        request=returnRequest();

    }
    public RequestQueue returnRequest () {
        if(request==null){
            //initialize request queue
            request= Volley.newRequestQueue(cont.getApplicationContext());

        }
        return request;
    }

    public static singleton getInstance(Context context){
        if(instance==null){
            instance = new singleton(context);
        }
        return instance;
    }
    public <T>void addRequest(Request<T> reque){
        request.add(reque);
    }
}
