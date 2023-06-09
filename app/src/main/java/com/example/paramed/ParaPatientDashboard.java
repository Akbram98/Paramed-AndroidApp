package com.example.paramed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class ParaPatientDashboard extends AppCompatActivity {
    ImageView openMenu;
    EditText phoneN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_patient_dashboard);

        openMenu = (ImageView) findViewById(R.id.menu);
        phoneN = (EditText) findViewById(R.id.patient_contact);

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
    }
}