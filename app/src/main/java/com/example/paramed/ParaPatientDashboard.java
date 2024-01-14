package com.example.paramed;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.*;



public class ParaPatientDashboard extends AppCompatActivity {
    private ImageView openMenu;
    private EditText phoneN;
    private TextView patientName, age, location, gender, temp, heartRate, glucoseLevel, oxygenLevel;
    private AddressConfig address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_patient_dashboard);

        openMenu = (ImageView) findViewById(R.id.menu);
        phoneN = (EditText) findViewById(R.id.patient_contact);
        patientName = (TextView)findViewById(R.id.patient_name);
        age = (TextView)findViewById(R.id.age);
        location = (TextView)findViewById(R.id.location);
        gender = (TextView)findViewById(R.id.gender);
        temp = (TextView)findViewById(R.id.temp);
        heartRate = (TextView)findViewById(R.id.heart);
        glucoseLevel = (TextView)findViewById(R.id.glucose);
        oxygenLevel = (TextView)findViewById(R.id.oxygen);


        phoneN.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ParaPatientDashboard.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.nav_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return false;
                    }
                });
              popupMenu.show();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                setFields(dataSnapshot);
              //  String value = dataSnapshot.getValue(String.class);
               // Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    private void setFields(DataSnapshot dataSnap){
        String contact = phoneN.getText().toString();

        for(DataSnapshot data: dataSnap.getChildren()){
            if(!data.child("contact").getValue(String.class).equals(contact))
                continue;

            String firstname = data.child("firstname").getValue(String.class);
            String lastname = data.child("lastname").getValue(String.class);

            patientName.setText(firstname + " " + lastname);
            age.setText(data.child("age").getValue(String.class));
            gender.setText(data.child("gender").getValue(String.class));
            heartRate.setText(data.child("heart_rate").getValue(String.class));
            oxygenLevel.setText(data.child("oxygen_level").getValue(String.class));
            glucoseLevel.setText(data.child("glucose_level").getValue(String.class));
            temp.setText(data.child("temparature").getValue(String.class));

            if(address == null){
                address = new AddressConfig();

                address.setCity(data.child("city").getValue(String.class));
                address.setPostal(data.child("postal").getValue(String.class));
                address.setStreet_address(data.child("street_address").getValue(String.class));
                address.setProvince(data.child("province").getValue(String.class));
            }
        }
    }
}