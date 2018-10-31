package com.reiyan.movie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername, edtPassword, edtEmail;
    private TextView login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = findViewById(R.id.username);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        login = findViewById(R.id.textblue);
        signup = findViewById(R.id.button2);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                SharedPreferences sp = getSharedPreferences("users", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                String username = edtUsername.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    edtEmail.setError("You Must Fill This Field");
                    edtUsername.setError("You Must Fill This Field");
                    edtPassword.setError("You Must Fill This Field");
                } else {
                    editor.putString("username",username);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putBoolean("activity_signup", true);
                    editor.apply();
                    startActivity(new Intent( this, MainActivity.class));
                    finish();
                }
                break;
            case R.id.textblue:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }
}
