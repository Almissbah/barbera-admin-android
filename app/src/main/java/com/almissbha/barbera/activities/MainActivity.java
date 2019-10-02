package com.almissbha.barbera.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.almissbha.barbera.R;
import com.almissbha.barbera.firebase.MyFirebaseMessagingService;
import com.almissbha.barbera.internet.VollyAddOrder;
import com.almissbha.barbera.model.Order;
import com.almissbha.barbera.model.User;
import com.almissbha.barbera.utils.Log;
import com.almissbha.barbera.utils.MyGsonManager;
import com.almissbha.barbera.utils.MyUtilities;

public class MainActivity extends BaseActivity {
    public Button btn_call;
    public EditText edt_costumer_phone, edt_balance_time;
    public User user;
    public Order order;
    private MainActivity mCtx;
    public TextView tv_info,tv_device_name;
    LinearLayout lin_main;
    String TAG="MainActivity";
public ProgressBar pb_waiting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCtx = MainActivity.this;


        pb_waiting= (ProgressBar) findViewById(R.id.pb_waiting);
        btn_call = (Button) findViewById(R.id.btn_call);
        edt_costumer_phone = (EditText) findViewById(R.id.edt_costumer_phone);
        edt_balance_time = (EditText) findViewById(R.id.edt_balance_time);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_device_name= (TextView) findViewById(R.id.tv_device_name);
        lin_main = (LinearLayout) findViewById(R.id.lin_main);
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
        } else {
            user = new MyGsonManager(mCtx).getUserObjectClass();
        }
        if (getIntent().hasExtra("order")) {
            order = (Order) getIntent().getSerializableExtra("order");
        } else {
            order = new MyGsonManager(mCtx).getOrderObjectClass();
        }
        tv_device_name.setText(user.getDeviceName());
        if (order.isRequested()) {
            mCtx.btn_call.setBackgroundResource(R.drawable.rounded_button_red);
            lin_main.setEnabled(false);
            mCtx.tv_info.setText("Waiting for acceptance !");
            pb_waiting.setVisibility(View.VISIBLE);
        } else {
            mCtx.btn_call.setBackgroundResource(R.drawable.rounded_button_green);
            lin_main.setEnabled(true);
            pb_waiting.setVisibility(View.GONE);
        }
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!order.isRequested()) {
                    String costumerPhone = edt_costumer_phone.getText().toString();
                    String balanceTime = edt_balance_time.getText().toString();
                    if(balanceTime.equals("")){MyUtilities.showCustomToast(mCtx, getString(R.string.plz_insert_balance));}
                    else if(edt_costumer_phone.equals("")){
                        MyUtilities.showCustomToast(mCtx, getString(R.string.invalid_phone));}
                    else{new VollyAddOrder(mCtx, costumerPhone, balanceTime);}
                } else {
                    MyUtilities.showCustomToast(mCtx, getString(R.string.order_fail));

                }
            }
        });

        registerReceiver(br, new IntentFilter(MyFirebaseMessagingService.BarberaBroadCast));
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(br);
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.m_logout:
                new AlertDialog.Builder(mCtx)
                        .setMessage(getString(R.string.logout_notify))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Whatever...
                                new MyGsonManager(mCtx).clear();
                                Intent i = new Intent(mCtx, LoginActivity.class);
                                startActivity(i);
                                mCtx.finish();
                            }
                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();

                break;
            case R.id.m_exit:
                mCtx.finish();
                break;

        }
        return true;
    }


    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   updateGUI(intent); // or whatever method used to update your GUI fields
          /*  if (intent.getExtras() != null) {
                long millisUntilFinished = intent.getLongExtra("countdown", 0);
                Log.i(TAG, "Countdown seconds remaining: " +  millisUntilFinished / 1000);
            }*/

            if (intent.getExtras() != null) {
                String action = intent.getStringExtra("action");
                Log.i(TAG,action);
                switch (action){
                    case "order_accepted":
                        mCtx.btn_call.setBackgroundResource(R.drawable.rounded_button_green);
                        pb_waiting.setVisibility(View.GONE);
                        tv_info.setText("--");
                        order =new Order();
                        break;


                }}

        }
    };

}
