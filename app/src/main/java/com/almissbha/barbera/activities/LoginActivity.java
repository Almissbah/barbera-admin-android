package com.almissbha.barbera.activities;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.almissbha.barbera.R;
import com.almissbha.barbera.internet.VollyLoginUser;
import com.almissbha.barbera.utils.MyUtilities;

import static com.almissbha.barbera.R.id.btn_login;
import static com.almissbha.barbera.R.id.edt_password;

public class LoginActivity extends BaseActivity {
    Button btn_login;
    EditText edt_username, edt_password;
    LoginActivity mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCtx = LoginActivity.this;


        btn_login = (Button) findViewById(R.id.btn_login);
        edt_username = (EditText) findViewById(R.id.edt_username);
        ;
        edt_password = (EditText) findViewById(R.id.edt_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edt_username.getText().toString();
                String password = edt_password.getText().toString();
                if (username.equals("") || password.equals("")) {
                    MyUtilities.showCustomToast(mCtx, "Please do not leave an empty field !");
                } else new VollyLoginUser(mCtx, username, password);
            }
        });
    }
}
