package com.almissbha.barbera.data.remote;

import android.app.ProgressDialog;
import android.view.View;

import com.almissbha.barbera.R;
import com.almissbha.barbera.ui.MainActivity;
import com.almissbha.barbera.model.User;
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
    MainActivity mCtx;
    String costumerPhone,balanceTime;
    ProgressDialog progress;
    User user;
    public VollyAddOrder(MainActivity mCtx, String...data) {
        costumerPhone= data[0];
        balanceTime= data[1];
        this.mCtx=mCtx;
        user =mCtx.user;
        progress= new ProgressDialog(mCtx);
        progress.setMessage(mCtx.getString(R.string.please_wait));
        progress.setCancelable(false);
        progress.show();

        String query="?user_id=2&admin_id="+ user.getId()+
                "&customer_phone="+ MyUtilities.getEncodedString(costumerPhone)+
                "&balance_time="+ MyUtilities.getEncodedString(balanceTime) ;
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
                        if( json.getString("success").equals("1")){

                            mCtx.order.setUserId(json.getInt("user_id"));
                            mCtx.order.setCostumerPhone(json.getString("customer_phone"));
                            mCtx.order.setAdminId(json.getInt("admin_id"));
                            mCtx.order.setBalanceTime(json.getInt("balance_time"));
                            mCtx.order.setRequested(true);
                            new MyGsonManager(mCtx).saveOrderObjectClass( mCtx.order);
                            mCtx.btnCall.setBackgroundResource(R.drawable.rounded_button_red);
                            mCtx.pb_waiting.setVisibility(View.VISIBLE);
                            mCtx.edtCostumerPhone.setText("");
                            mCtx.edtBalanceTime.setText("");
                            mCtx.tvInfo.setText("Waiting for acceptance !");
                        }else
                        {
                            MyUtilities.showErrorDialog(mCtx,json.getString("message"));
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                MyUtilities.showCustomToast(mCtx,mCtx.getString(R.string.networkErr));

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
}
}
