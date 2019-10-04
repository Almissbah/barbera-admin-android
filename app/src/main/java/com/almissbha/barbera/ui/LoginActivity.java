package com.almissbha.barbera.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.almissbha.barbera.R;
import com.almissbha.barbera.data.local.SharedPrefManager;
import com.almissbha.barbera.data.remote.VolleyLoginUser;
import com.almissbha.barbera.model.User;
import com.almissbha.barbera.utils.MyUtilities;

public class LoginActivity extends BaseActivity {
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private LoginActivity mCtx;
    private SharedPrefManager sharedPrefManager;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mCtx = LoginActivity.this;

        sharedPrefManager=SharedPrefManager.getInstance(mCtx);


        btnLogin = (Button) findViewById(R.id.btn_login);
        edtUsername = (EditText) findViewById(R.id.edt_username);

        edtPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                 if (validateInput(username,password)){
                     new VolleyLoginUser(mCtx, username, password, SharedPrefManager.getInstance(mCtx).getDeviceToken(), new VolleyLoginUser.CallBack() {
                    @Override
                    public void onSuccess(User user) {
                        user.setToken(sharedPrefManager.getDeviceToken());
                        sharedPrefManager.saveUser(user);
                        mUser=user;
                        startMainActivity();
                    }

                    @Override
                    public void onFail(String msg) {
                        MyUtilities.showErrorDialog(mCtx,msg);
                    }
                });}
            }
        });
    }
    boolean validateInput(String username, String password ){
        if (username.equals("") || password.equals("")) {
            MyUtilities.showCustomToast(mCtx, "Please do not leave an empty field !");
            return false;
        }else return true;
    }

    void startMainActivity(){
        Intent i=new Intent(mCtx, MainActivity.class);
        i.putExtra("user", mUser);
        mCtx.startActivity(i);
        mCtx.finish();
    }
}
