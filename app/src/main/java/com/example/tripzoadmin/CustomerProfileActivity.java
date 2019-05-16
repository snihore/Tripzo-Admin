package com.example.tripzoadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfileActivity extends AppCompatActivity {

    private TextView name, dob, gender, email, mobile, emergencyMobile, perAddr, curAddr, title;
    private CustomerData customerData;

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
        name = (TextView)findViewById(R.id.profile_cus_name);
        dob = (TextView)findViewById(R.id.profile_cus_dob);
        gender = (TextView)findViewById(R.id.profile_cus_gender);
        email = (TextView)findViewById(R.id.profile_cus_email);
        mobile = (TextView)findViewById(R.id.profile_cus_mobile);
        emergencyMobile = (TextView)findViewById(R.id.profile_cus_emer_mobile);
        perAddr = (TextView)findViewById(R.id.profile_cus_per_addr);
        curAddr = (TextView)findViewById(R.id.profile_cus_cur_addr);
        title = (TextView)findViewById(R.id.profile_cus_title);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        builder = new AlertDialog.Builder(CustomerProfileActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();

        initViews();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Customer Information");

        customerData = (CustomerData)((ObjectWrapperForBinder)getIntent().getExtras().getBinder("customerProfileData")).getData();
        setDataToView();
    }

    private void setDataToView() {
        if(customerData != null){
            String fN = customerData.getFirstName();
            String lN = customerData.getLastName();

            title.setText(fN.toUpperCase());
            name.setText(fN+" "+lN);
            dob.setText(customerData.getDob());
            gender.setText(customerData.getGender());
            email.setText(customerData.getEmail());
            mobile.setText(customerData.getMobile());
            emergencyMobile.setText(customerData.getEmergencyMobile());
            perAddr.setText(customerData.getPermanentAddress());
            curAddr.setText(customerData.getCurrentAddress());

        }
    }

    public void blockCustomer(View view) {
    }

    public void deleteCustomer(View view) {
        new AlertDialog.Builder(CustomerProfileActivity.this)
                .setTitle("Delete Customer")
                .setMessage("Are you sure you want to delete this customer ?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        progessActivate(true);
                        Query query = myRef.orderByChild("email").equalTo(customerData.getEmail());

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                    progessActivate(false);
                                    Toast.makeText(CustomerProfileActivity.this, "Successfully Deleted !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(CustomerProfileActivity.this, "Not Deleted !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.delete2_icon)
                .show();
    }
}
