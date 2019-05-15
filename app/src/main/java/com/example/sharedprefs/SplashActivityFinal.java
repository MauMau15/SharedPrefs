package com.example.sharedprefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import org.w3c.dom.Text;

public class SplashActivityFinal extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashScreen);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_final);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentMain = new Intent(SplashActivityFinal.this,MainActivity.class);
                Intent intentLogin = new Intent(SplashActivityFinal.this,LoginActivity.class);
                if(getUserLogStatus()){
                    startActivity(intentMain);
                }else{
                    startActivity(intentLogin);
                }
                finish();
            }
        },2000);
    }

    private String getUserMailPrefs(){
        return  prefs.getString("email","");
    }

    private String getUserPassPrefs(){
        return  prefs.getString("pass","");
    }

    private boolean getUserLogStatus(){
        return prefs.getBoolean("isLogged",false);
    }
}
