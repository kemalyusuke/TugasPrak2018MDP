package com.example.emzo.aplikasimodul2kel27;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsernameLogin, etPasswdLogin;
    private Button btnSignUpLogin, btnSignInLogin;
    String username, password;
    public static User user;
    public static DatabaseHandler presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initData();
        login();
        register();
    }

    private void initView() {
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswdLogin = findViewById(R.id.etPasswdLogin);
        btnSignInLogin = findViewById(R.id.btnSignInLogin);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);
    }

    private void register() {
        btnSignUpLogin.setOnClickListener(V -> {
            Intent reg = new Intent(this, RegisterActivity.class);
            startActivity(reg);
            finish();}
        );
    }

    private void initData() {
        user = new User();
        presenter = new DatabaseHandler(this);
    }

    public static User getUser(){
        return user;
    }

    public static DatabaseHandler getPresenter(){
        return presenter;
    }
    private Boolean validation() {
        username = etUsernameLogin.getText().toString();
        password = etPasswdLogin.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Isikan username", Toast.LENGTH_SHORT).show();
            Log.e("Validation","false");
            return false;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Isikan Password", Toast.LENGTH_SHORT).show();
            Log.e("Validation","false");
            return false;
        }

        Log.e("Validation","true");
        return true;
    }

    private void login() {
        btnSignInLogin.setOnClickListener(v -> actLogin());
    }

    private void actLogin(){
        if(validation()){
            if(loginData()){
                Log.e("actLogin","true");
                initPreference();
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
            } else {
                Log.e("actLogin","false");
                Toast.makeText(this, "Login gagal", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean loginData(){
        user = presenter.getLogin(username);

        if (password.equals(user.getPassword())){
            Log.e("loginData", "true");
            return true;
        } else {
            Log.e("loginData", "false");
            return false;
        }
    }

    private void initPreference() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.putString("username", username);
        editor.commit();
        editor.apply();
    }
}
