package com.example.sharedprefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private TextView emailTxt;
    private TextView pwdTxt;
    private Switch remSw;
    private Button logInBtn;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        editor = prefs.edit();
        setCredentialsIfExist();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();
                String pwd = pwdTxt.getText().toString();

                if(login(email,pwd)){
                    goToMain();
                    saveOnPreferences(email,pwd);
                }
            }
        });
    }

    private void setCredentialsIfExist(){
        String email = getUserMailPrefs();
        String pass = getUserPassPrefs();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            emailTxt.setText(email);
            pwdTxt.setText(pass);
        }
    }

    private void bindUI(){
        this.emailTxt = findViewById(R.id.email);
        this.pwdTxt = findViewById(R.id.password);
        this.remSw = findViewById(R.id.remember);
        this.logInBtn = findViewById(R.id.login);
    }

    private boolean isValidMail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length() > 4;
    }

    private boolean login(String email, String password){
        if(!isValidMail(email)){
            Toast.makeText(this,"Email is not valid",Toast.LENGTH_LONG).show();
            return false;
        } else if (!isValidPassword(password)) {
            Toast.makeText(this,"Password is not valid",Toast.LENGTH_LONG).show();
            return false;
        }else {
            editor.putBoolean("isLogged",true);
            return true;
        }
    }

    private void goToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void saveOnPreferences(String email, String pwd){
        if(this.remSw.isChecked()){
            editor.putString("email",email);
            editor.putString("pass",pwd);
            //editor.commit();
            editor.apply();
        }
    }

    private String getUserMailPrefs(){
        return  prefs.getString("email","");
    }

    private String getUserPassPrefs(){
        return  prefs.getString("pass","");
    }
}