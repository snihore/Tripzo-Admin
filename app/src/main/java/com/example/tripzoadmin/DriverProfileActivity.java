package com.example.tripzoadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DriverProfileActivity extends AppCompatActivity {

    private TextView name, dob, gender, email, mobile, emergencyMobile, perAddr, curAddr, v1, v2, v3, v4, title, status;
    private DriverData driverData;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private void progessActivate(boolean key){

        if(key){
            dialog.show();
        }else{
            dialog.cancel();
        }
    }

    private void initViews(){
        name = (TextView)findViewById(R.id.profile_driver_name);
        dob = (TextView)findViewById(R.id.profile_driver_dob);
        gender = (TextView)findViewById(R.id.profile_driver_gender);
        email = (TextView)findViewById(R.id.profile_driver_email);
        mobile = (TextView)findViewById(R.id.profile_driver_mobile);
        emergencyMobile = (TextView)findViewById(R.id.profile_driver_emer_mobile);
        perAddr = (TextView)findViewById(R.id.profile_driver_per_addr);
        curAddr = (TextView)findViewById(R.id.profile_driver_cur_addr);
        v1 = (TextView)findViewById(R.id.profile_driver_vehicle_menufacturer);
        v2 = (TextView)findViewById(R.id.profile_driver_vehicle_type);
        v3 = (TextView)findViewById(R.id.profile_driver_vehicle_category);
        v4 = (TextView)findViewById(R.id.profile_driver_vehicle_number);
        title = (TextView)findViewById(R.id.profile_driver_title);
        status = (TextView)findViewById(R.id.profile_driver_status);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        builder = new AlertDialog.Builder(DriverProfileActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        initViews();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Driver Information");

        driverData = (DriverData)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("driverProfileData")).getData();
        setDataToView();
    }

    private void setDataToView() {
        if(driverData != null){
            String fN = driverData.getFirstName();
            String lN = driverData.getLastName();

            title.setText(fN.toUpperCase());
            name.setText(fN+" "+lN);
            dob.setText(driverData.getDob());
            gender.setText(driverData.getGender());
            email.setText(driverData.getEmail());
            mobile.setText(driverData.getMobile());
            emergencyMobile.setText(driverData.getEmergencyMobile());
            perAddr.setText(driverData.getPermanentAddress());
            curAddr.setText(driverData.getCurrentAddress());
            v1.setText(driverData.getVehicleManufacturer());
            v2.setText(driverData.getVehicleType());
            v3.setText(driverData.getVehicleCategory());
            v4.setText(driverData.getVehicleNumber());
            status.setText(driverData.getActiveStatus().toUpperCase());
        }
    }

    public void deleteDriver(View view) {

        new AlertDialog.Builder(DriverProfileActivity.this)
                .setTitle("Delete Driver")
                .setMessage("Are you sure you want to delete this driver ?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        progessActivate(true);
                        Query query = myRef.orderByChild("email").equalTo(driverData.getEmail());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                    progessActivate(false);
                                    Toast.makeText(DriverProfileActivity.this, "Successfully Deleted !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(DriverProfileActivity.this, "Not Deleted !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.delete2_icon)
                .show();


    }

    public void blockDriver(View view) {
    }
}
