package com.example.tripzoadmin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    private ImageView logoId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();


        final FirebaseUser currentUser = mAuth.getCurrentUser();

        logoId = (ImageView)findViewById(R.id.logo_id);

        Glide
                .with(this)
                .load(R.drawable.tripzologo)
                        .into(logoId);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(currentUser != null){
                    Intent mainOptionsActivityIntent = new Intent(getApplicationContext(), AdminMainActivity.class);
                    startActivity(mainOptionsActivityIntent);
                    finish();
                }else{
                    Intent adminLoginActivityIntent = new Intent(getApplicationContext(), AdminLoginActivity.class);

                    startActivity(adminLoginActivityIntent);

                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
