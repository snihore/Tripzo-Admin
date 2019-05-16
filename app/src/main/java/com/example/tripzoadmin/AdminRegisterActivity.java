package com.example.tripzoadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegisterActivity extends AppCompatActivity {

    private EditText firstName, lastName, dob, email, mobile, parAddr, curAddr, pwd, confPwd;
    private RadioButton maleRadio, femaleRadio;
    private TextView status;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private void progessActivate(boolean key){

        if(key){
            dialog.show();
        }else{
            dialog.cancel();
        }
    }


    private void initViews(){
        status = (TextView)findViewById(R.id.registration_status);
        maleRadio = (RadioButton)findViewById(R.id.radio_male);
        femaleRadio = (RadioButton)findViewById(R.id.radio_female);
        firstName = (EditText)findViewById(R.id.reg_first_name);
        lastName = (EditText)findViewById(R.id.reg_last_name);
        dob = (EditText)findViewById(R.id.reg_dob);
        email = (EditText)findViewById(R.id.reg_email);
        mobile = (EditText)findViewById(R.id.reg_mobile);
        parAddr = (EditText)findViewById(R.id.reg_par_addr);
        curAddr = (EditText)findViewById(R.id.reg_cur_addr);
        pwd = (EditText)findViewById(R.id.reg_pwd);
        confPwd = (EditText)findViewById(R.id.reg_conf_pwd);
    }

    private AdminData fetchData(){
        String firstStr = firstName.getText().toString().trim();
        String lastStr = lastName.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        String mobileStr = mobile.getText().toString().trim();
        String parAddrStr = parAddr.getText().toString().trim();
        String curAddrStr = curAddr.getText().toString().trim();
        String pwdStr = pwd.getText().toString().trim();
        String confPwdStr = confPwd.getText().toString().trim();
        String dobStr = dob.getText().toString().trim();
        String gender;

        if(maleRadio.isChecked()){
            gender = "male";
        }else if(femaleRadio.isChecked()){
            gender = "female";
        }else{
            gender = "no gender";
        }

        if(
             firstStr.matches("") ||
             lastStr.matches("") ||
             emailStr.matches("") ||
             mobileStr.matches("") ||
             parAddrStr.matches("") ||
             curAddrStr.matches("") ||
             pwdStr.matches("") ||
             confPwdStr.matches("") ||
             gender.matches("no gender")
        ){
            status.setText("Please fill all the input fields ...");
        }else{
            if(pwdStr.length() < 8){
                status.setText("Passwword length should be atleast 8");
            }else{
                if(!pwdStr.equals(confPwdStr)){
                    status.setText("Password not matched ...");
                }else{
                    status.setText("");
                    AdminData data = new AdminData(
                            firstStr,
                            lastStr,
                            dobStr,
                            gender,
                            emailStr,
                            mobileStr,
                            parAddrStr,
                            curAddrStr,
                            pwdStr
                    );
                    return data;
                }
            }
        }
        return null;
    }

    private void sinupUser(final AdminData adminData) {
        mAuth.createUserWithEmailAndPassword(adminData.getEmail(), adminData.getPassword())
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(adminData.getEmail().matches(authResult.getUser().getEmail())){
                            adminData.setEmail(authResult.getUser().getEmail());
                            adminData.setPassword(authResult.getUser().getUid());
                            database(adminData);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progessActivate(false);
                        Toast.makeText(AdminRegisterActivity.this, "Failed !"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        status.setText(e.getMessage());
                    }
                });
    }

    private void database(AdminData adminData){

        databaseReference.child(databaseReference.push().getKey())
                .setValue(adminData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progessActivate(false);
                Toast.makeText(AdminRegisterActivity.this, "Success :)", Toast.LENGTH_SHORT).show();
                ////
                Intent mainOptionsActivityIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
                startActivity(mainOptionsActivityIntent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progessActivate(false);
                Toast.makeText(AdminRegisterActivity.this, "Failed !"+e.getMessage(), Toast.LENGTH_SHORT).show();
                status.setText(e.getMessage());
                mAuth.signOut();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        initViews();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Admin Information");

        builder = new AlertDialog.Builder(AdminRegisterActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
    }


    public void register(View view) {
        AdminData adminData = fetchData();
        if(adminData != null){
            progessActivate(true);
            sinupUser(adminData);
//            database(adminData);
        }
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
        startActivity(intent);
        finish();
    }
}
