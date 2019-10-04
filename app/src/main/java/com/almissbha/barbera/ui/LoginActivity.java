package com.almissbha.barbera.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.almissbha.barbera.R;
import com.almissbha.barbera.data.remote.VollyLoginUser;
import com.almissbha.barbera.utils.MyUtilities;

public class LoginActivity extends BaseActivity {
    Button btnLogin;
    EditText edtUsername, edtPassword;
    LoginActivity mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCtx = LoginActivity.this;


        btnLogin = (Button) findViewById(R.id.btn_login);
        edtUsername = (EditText) findViewById(R.id.edt_username);

        edtPassword = (EditText) findViewById(R.id.edt_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.equals("") || password.equals("")) {
                    MyUtilities.showCustomToast(mCtx, "Please do not leave an empty field !");
                } else new VollyLoginUser(mCtx, username, password);
            }
        });
    }
}
