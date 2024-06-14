package com.example.paramed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

public class login extends AppCompatActivity {
    private Button login;
    private EditText username;
    private EditText password;
    private TextView errMsg;
    private ArrayList<String> paramed_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        errMsg = (TextView) findViewById(R.id.errMsg);

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(errMsg.getVisibility() == View.VISIBLE) {
                    errMsg.setVisibility(View.INVISIBLE);
                    username.setBackgroundTintList(ContextCompat.getColorStateList(login.this, R.color.login_field));
                    password.setBackgroundTintList(ContextCompat.getColorStateList(login.this, R.color.login_field));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals(""))
                    username.setError(getResources().getString(R.string.userError));
                if(password.getText().toString().equals(""))
                    password.setError(getResources().getString(R.string.passError));
                else
                    //NOTE: localhost must be changed to raw IPv4
                    getJSON("http://192.168.2.12/getLoginData.php");
            }
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() { super.onPreExecute(); };

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONArray ja = new JSONArray(s);
                    for(int i = 0; i < ja.length(); i++){
                        JSONObject obj = ja.getJSONObject(i);

                        if(obj.getString("username").equals(username.getText().toString()) &&
                            obj.getString("password").equals(password.getText().toString())){
                            paramed_data = new ArrayList<>();

                            paramed_data.add(obj.getString("firstname"));
                            paramed_data.add(obj.getString("lastname"));
                            paramed_data.add(username.getText().toString());
                            paramed_data.add(password.getText().toString());

                            break;
                        }
                    }

                    if(paramed_data == null) {
                        errMsg.setVisibility(View.VISIBLE);
                        username.setBackgroundTintList(ContextCompat.getColorStateList(login.this, R.color.error));
                        password.setBackgroundTintList(ContextCompat.getColorStateList(login.this, R.color.error));
                    }
                    else {
                        Intent intent = new Intent(login.this, patientNumLookup.class);
                        intent.putExtra("ParamedicData", paramed_data);
                        startActivity(intent);
                       // Toast.makeText(login.this, paramed_data.toString(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(Void... voids) {

                try{
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;

                    while((json = bf.readLine()) != null){
                        sb.append(json + "\n");
                    }

                    con.disconnect();
                    return sb.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
}