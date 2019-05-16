package com.example.tripzoadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText email, pwd;
    private TextView status;

    AlertDialog.Builder builder;
    AlertDialog dialog;

    private FirebaseAuth mAuth;

    private void progessActivate(boolean key){

        if(key){
            dialog.show();
        }else{
            dialog.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        initViews();
        mAuth = FirebaseAuth.getInstance();

        builder = new AlertDialog.Builder(AdminLoginActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
    }

    private void initViews(){
        status = (TextView)findViewById(R.id.login_status);
        email = (EditText)findViewById(R.id.login_email);
        pwd = (EditText)findViewById(R.id.login_pwd);
    }
    public void login(View view) {
//        progessActivate(true);
        if(email.getText().toString().trim().matches("") ||
                pwd.getText().toString().trim().matches("")
            ){
            status.setText("Please fill all the input fields ...");
        }else{
            progessActivate(true);

            mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pwd.getText().toString().trim())
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progessActivate(false);
                            Toast.makeText(AdminLoginActivity.this, "Login Success :)", Toast.LENGTH_SHORT).show();
                            Intent mainOptionsActivityIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
                            startActivity(mainOptionsActivityIntent);
                            finish();
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progessActivate(false);
                            Toast.makeText(AdminLoginActivity.this, "Login Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            status.setText(e.getMessage());
                        }
                    });
        }
    }

    public void gotoRegister(View view) {

        Intent intent = new Intent(getApplicationContext(), AdminRegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
