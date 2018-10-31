package com.reiyan.movie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {






    //GIVE ME TOOLBAR REIYAN!!!!!!


    private EditText edtEmail, edtPassword;
    Button login;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        login = findViewById(R.id.button2);
        signup = findViewById(R.id.signup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                SharedPreferences sp = getSharedPreferences("users", Context.MODE_PRIVATE);
                String email = sp.getString("email", null);
                String password = sp.getString("password", null);

                if (TextUtils.isEmpty(edtEmail.getText().toString()) || TextUtils.isEmpty(edtPassword.getText().toString())){
                    edtPassword.setError("You Must Fill This Field");
                    edtEmail.setError("You Must Fill This Field");
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("activity_signup", true);
                    editor.apply();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
            case R.id.signup:
                startActivity(new Intent(this, SignUpActivity.class));
                finish();
                break;
        }

    }
}
