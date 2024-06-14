package com.example.paramed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class patientNumLookup extends AppCompatActivity {
    private Button lookUp;
    private EditText pContact;
    private TextView paramedic_name;
    private ArrayList<String> myList;
    private ImageView menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_num_lookup);

        Intent intent = getIntent();
        myList = intent.getStringArrayListExtra("ParamedicData");

        pContact = (EditText) findViewById(R.id.patient_num);
        lookUp = (Button) findViewById(R.id.lookup);
        paramedic_name = (TextView) findViewById(R.id.paramedic_name2);
        menu = (ImageView)findViewById(R.id.imageView4);

        paramedic_name.setText("Hello "+myList.get(0));


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        lookUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pContact.getText().toString().equals("") || pContact.getText().toString().length() != 10){
                    Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_SHORT).show();
                }
                else{

                    myList.add(pContact.getText().toString());

                    Intent intent = new Intent(patientNumLookup.this, ParaPatientDashboard.class);
                    intent.putStringArrayListExtra("ParamedicData", myList);
                    startActivity(intent);
                }
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(patientNumLookup.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.nav_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.About:
                        Toast.makeText(getApplicationContext(), "You have clicked Help option!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        Intent intent = new Intent(patientNumLookup.this, login.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }
}