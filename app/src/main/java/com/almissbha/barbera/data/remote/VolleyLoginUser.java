package com.almissbha.barbera.data.remote;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.almissbha.barbera.R;
import com.almissbha.barbera.ui.LoginActivity;
import com.almissbha.barbera.ui.MainActivity;
import com.almissbha.barbera.model.User;
import com.almissbha.barbera.utils.MyUtilities;
import com.almissbha.barbera.data.local.SharedPrefManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mohamed on 12/4/2017.
 */

public class VolleyLoginUser {
    private ProgressDialog progress;
    private CallBack callBack;
    public VolleyLoginUser(Context mCtx, String username, String password,String token,CallBack callBack) {
        this.callBack=callBack;
        progress= new ProgressDialog(mCtx);
        progress.setMessage(mCtx.getString(R.string.please_wait));
        progress.setCancelable(false);
        progress.show();

        if(token==null){token="no token";}
        String query="?username="+ MyUtilities.getEncodedString(username)+
                "&password="+ MyUtilities.getEncodedString(password)+
                "&token="+ MyUtilities.getEncodedString(token) ;
        HttpRequest(mCtx,ServerAPIs.getLogin_url()+query);

    }

     private void HttpRequest(final Context mCtx, String url){
        RequestQueue queue = Volley.newRequestQueue(mCtx);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        JSONObject json= null;
                        try {
                            json = new JSONObject(response);
                        if( json.getString("success").equals("1")){
                            User user =new User();
                            user.setId(json.getInt("user_id"));
                            user.setLogged(true);
                            user.setDeviceName(json.getString("device_name"));
                            user.setUserName(json.getString("user_name"));

                            callBack.onSuccess(user);

                        }else
                        {
                            callBack.onFail(json.getString("message"));
                        }
                        } catch (JSONException e) {
                            callBack.onFail(mCtx.getString(R.string.networkErr));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                callBack.onFail(mCtx.getString(R.string.networkErr));


            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
}

public interface CallBack{
        void onSuccess(User user);
        void onFail(String msg);
}
}
