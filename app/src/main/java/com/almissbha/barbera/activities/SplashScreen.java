package com.almissbha.barbera.activities;

import android.content.Intent;
import android.os.Bundle;

import com.almissbha.barbera.model.User;

public class SplashScreen extends BaseActivity {
    SplashScreen mCtx;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Thread waiting= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    mCtx.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=null;
                            if(user !=null&& user.isLogged()){
                                i=new Intent(mCtx,MainActivity.class);
                                i.putExtra("user", user);
                            }else {
                                i=new Intent(mCtx,LoginActivity.class);
                            }
                            if(i!=null&&mCtx!=null) {
                                mCtx.startActivity(i);
                                mCtx.finish();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waiting.start();
    }
}
