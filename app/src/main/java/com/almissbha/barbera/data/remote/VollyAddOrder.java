package com.almissbha.barbera.data.remote;

import android.app.ProgressDialog;

import com.almissbha.barbera.R;
import com.almissbha.barbera.model.Order;
import com.almissbha.barbera.ui.MainActivity;
import com.almissbha.barbera.utils.MyUtilities;
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

public class VollyAddOrder {

    public interface CallBack{
        void onSuccess(Order order);
        void onFail(String string);
    }
    MainActivity mCtx;
    ProgressDialog progress;
    CallBack callBack;
    
    public VollyAddOrder(MainActivity mCtx, AddOrderRequest addOrderRequest, CallBack callBack) {
        this.callBack=callBack;
        this.mCtx=mCtx;
        progress= new ProgressDialog(mCtx);
        progress.setMessage(mCtx.getString(R.string.please_wait));
        progress.setCancelable(false);
        progress.show();

        String query="?user_id=2&admin_id="+ addOrderRequest.getUser().getId()+
                "&customer_phone="+ MyUtilities.getEncodedString(addOrderRequest.getCostumerPhone())+
                "&balance_time="+ MyUtilities.getEncodedString(addOrderRequest.getBalanceTime()) ;
        HttpRequest(ServerAPIs.getAdd_order_url()+query);

    }

     private void HttpRequest(String url){
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
                            Order order=new Order();
                        if( json.getString("success").equals("1")){

                            order.setUserId(json.getInt("user_id"));
                            order.setCostumerPhone(json.getString("customer_phone"));
                            order.setAdminId(json.getInt("admin_id"));
                            order.setBalanceTime(json.getInt("balance_time"));
                            order.setRequested(true);
                            callBack.onSuccess(order);

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
}
