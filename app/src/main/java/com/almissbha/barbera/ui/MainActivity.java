package com.almissbha.barbera.ui;

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
import com.almissbha.barbera.data.local.SharedPrefManager;
import com.almissbha.barbera.firebase.MyFirebaseMessagingService;
import com.almissbha.barbera.data.remote.VollyAddOrder;
import com.almissbha.barbera.model.Order;
import com.almissbha.barbera.model.User;
import com.almissbha.barbera.utils.Log;
import com.almissbha.barbera.utils.MyUtilities;

public class MainActivity extends BaseActivity {
    private Button btnCall;
    private EditText edtCostumerPhone, edtBalanceTime;
    private User user;
    private Order order;
    private MainActivity mCtx;
    private TextView tvInfo, tvDeviceName;
    private LinearLayout mainLinearLayout;
    private String TAG=MainActivity.class.getName();
    private ProgressBar pb_waiting;
    SharedPrefManager mSharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCtx = MainActivity.this;
        mSharedPrefManager=SharedPrefManager.getInstance(mCtx);


        getIntentData();
        initUI();

        registerReceiver(br, new IntentFilter(MyFirebaseMessagingService.BarberaBroadCast));
    }
    void getIntentData(){

        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getSerializableExtra("user");
        } else {
            user = mSharedPrefManager.getUser();
        }
        if (getIntent().hasExtra("order")) {
            order = (Order) getIntent().getSerializableExtra("order");
        } else {
            order = mSharedPrefManager.getOrder();
        }

    }
    void initUI(){
        pb_waiting= (ProgressBar) findViewById(R.id.pb_waiting);
        btnCall = (Button) findViewById(R.id.btn_call);
        edtCostumerPhone = (EditText) findViewById(R.id.edt_costumer_phone);
        edtBalanceTime = (EditText) findViewById(R.id.edt_balance_time);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvDeviceName = (TextView) findViewById(R.id.tv_device_name);
        mainLinearLayout = (LinearLayout) findViewById(R.id.lin_main);

        tvDeviceName.setText(user.getDeviceName());

        if (order.isRequested()) {
            mCtx.btnCall.setBackgroundResource(R.drawable.rounded_button_red);
            mainLinearLayout.setEnabled(false);
            mCtx.tvInfo.setText("Waiting for acceptance !");
            pb_waiting.setVisibility(View.VISIBLE);
        } else {
            mCtx.btnCall.setBackgroundResource(R.drawable.rounded_button_green);
            mainLinearLayout.setEnabled(true);
            pb_waiting.setVisibility(View.GONE);
        }
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!order.isRequested()) {

                    String costumerPhone = edtCostumerPhone.getText().toString();
                    String balanceTime = edtBalanceTime.getText().toString();

                    if(validateInput( costumerPhone, balanceTime)){
                        new VollyAddOrder(mCtx, costumerPhone, balanceTime);}
                } else {
                    MyUtilities.showCustomToast(mCtx, getString(R.string.order_fail));

                }
            }
        });
    }


    boolean validateInput(String costumerPhone,String balanceTime){
        if(balanceTime.equals("")){
            MyUtilities.showCustomToast(mCtx, getString(R.string.plz_insert_balance));
            return false;}
        else if(edtCostumerPhone.equals("")){
            MyUtilities.showCustomToast(mCtx, getString(R.string.invalid_phone));
            return false;}
        else return true;
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
                                mSharedPrefManager.clear();
                                startLoginActivity();
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
    void startLoginActivity(){
        Intent i = new Intent(mCtx, LoginActivity.class);
        startActivity(i);
        mCtx.finish();
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
                        mCtx.btnCall.setBackgroundResource(R.drawable.rounded_button_green);
                        pb_waiting.setVisibility(View.GONE);
                        tvInfo.setText("--");
                        order =new Order();
                        break;


                }}

        }
    };

}
