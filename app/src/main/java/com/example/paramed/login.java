package com.example.paramed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {
    private Button login;
    private EditText username;
    private EditText password;
    private TextView errMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        errMsg = (TextView) findViewById(R.id.errMsg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals(""))
                    username.setError(getResources().getString(R.string.userError));
                if(password.getText().toString().equals(""))
                    password.setError(getResources().getString(R.string.passError));
                else
                    errMsg.setVisibility(View.VISIBLE);
            }
        });
    }
}