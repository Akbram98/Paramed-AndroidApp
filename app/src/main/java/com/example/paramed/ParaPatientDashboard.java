package com.example.paramed;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.TelephonyCallback;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ParaPatientDashboard extends AppCompatActivity {
    private TextView phoneN, paramedic_name, patientName, age, location, gender, temp, heartRate, glucoseLevel, oxygenLevel;
    private AddressConfig address;
    private Button emerg_end;
    private String key;
    private ArrayList<String> pData;
    private ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_patient_dashboard);

        if(pData == null) {
            Intent intent = getIntent();
            pData = intent.getStringArrayListExtra("ParamedicData");
        }

        phoneN = (TextView) findViewById(R.id.patient_contact);
        patientName = (TextView)findViewById(R.id.patient_name);
        age = (TextView)findViewById(R.id.age);
        location = (TextView)findViewById(R.id.location);
        gender = (TextView)findViewById(R.id.gender);
        temp = (TextView)findViewById(R.id.temp);
        heartRate = (TextView)findViewById(R.id.heart);
        glucoseLevel = (TextView)findViewById(R.id.glucose);
        oxygenLevel = (TextView)findViewById(R.id.oxygen);
        emerg_end = (Button)findViewById(R.id.end_emerg);
        paramedic_name = (TextView)findViewById(R.id.paramedic_name);
        menu = (ImageView)findViewById(R.id.imageView3);

        paramedic_name.setText("Hello "+pData.get(0));

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        emerg_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(key != null){
                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child(key);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("status", "inactive");

                    usersRef.updateChildren(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data updated successfully
                                    Log.d("TAG", "Data updated successfully");
                                    Intent intent = new Intent(ParaPatientDashboard.this, patientNumLookup.class);
                                    pData.remove(4);
                                    intent.putStringArrayListExtra("ParamedicData", pData);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to update data
                                    Log.e("TAG", "Failed to update data", e);
                                }
                            });
                }
            }
        });

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                setFields(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(ParaPatientDashboard.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.nav_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.About:
                        Toast.makeText(getApplicationContext(), "You have clicked Help option!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        Toast.makeText(getApplicationContext(), "Can't logout. Patient emergency ongoing!", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void setFields(DataSnapshot dataSnap){

        if(pData == null) {
            Intent intent = getIntent();
            pData = intent.getStringArrayListExtra("ParamedicData");
        }

        String contact = pData.get(4);

        for(DataSnapshot data: dataSnap.getChildren()){
            if(!String.valueOf(data.child("contact").getValue(Long.class)).equals(contact))
                continue;
            if(!data.child("status").getValue(String.class).equals("active"))
                continue;

            key = data.getKey();

            String firstname = data.child("firstname").getValue(String.class);
            String lastname = data.child("lastname").getValue(String.class);
            phoneN.setText(contact);
            patientName.setText(firstname + " " + lastname);
            age.setText(String.valueOf(data.child("age").getValue(Long.class)));
            gender.setText(data.child("gender").getValue(String.class));
            heartRate.setText(String.valueOf(data.child("heart_rate").getValue(Double.class)) + "%");
            oxygenLevel.setText(String.valueOf(data.child("oxygen_level").getValue(Double.class)) + "%");
            glucoseLevel.setText(String.valueOf(data.child("glucose_level").getValue(Double.class)) + "%");
            temp.setText(String.valueOf(data.child("temperature").getValue(Double.class)) + "°C");

            if(address == null){
                address = new AddressConfig();

                address.setCity(data.child("city").getValue(String.class));
                address.setPostal(data.child("postal").getValue(String.class));
                address.setStreet_address(data.child("street_address").getValue(String.class));
                address.setProvince(data.child("province").getValue(String.class));
            }

            break;
        }
    }
}