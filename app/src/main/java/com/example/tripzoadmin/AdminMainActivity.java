package com.example.tripzoadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mAuth = FirebaseAuth.getInstance();


        currentUser = mAuth.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.admind_drawer_layout);
        NavigationView navigationView = findViewById(R.id.admin_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                    new RideDetailsFragement()).commit();
            navigationView.setCheckedItem(R.id.ride_details);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.customer_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                        new CustomersListFragement()).commit();
                break;
            case R.id.driver_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                        new DriversListFragement()).commit();
                break;
            case R.id.ride_details:
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_fragment_container,
                        new RideDetailsFragement()).commit();
                break;
            case R.id.admin_logout:
                if(currentUser != null){
                    mAuth.signOut();
                    Intent adminLoginActivityIntent = new Intent(getApplicationContext(), AdminLoginActivity.class);

                    startActivity(adminLoginActivityIntent);

                    finish();
                }
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
