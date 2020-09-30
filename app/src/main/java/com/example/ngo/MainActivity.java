package com.example.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
    {
    //Defining the variables for drawerlayout
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
////////////////////////////////////////Defining the hooks////////////////////////////////////////
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        button=findViewById(R.id.problemButton);
        firebaseAuth=FirebaseAuth.getInstance();
        ////////////telling the system that we are going to use this toolbar as our actionbar//////////////
        setSupportActionBar(toolbar);
        ////////////telling the system that open menu when the button is clicked//////////////////////
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        /////////to make the menu clickable///////////////
        navigationView.setNavigationItemSelectedListener(this);
        /////////so that by default home is selected//////////////
        navigationView.setCheckedItem(R.id.nav_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProblemSearch.class));
            }
        });
    }
    ////////////do not exit when backpressed/////////////
    @Override
    public void onBackPressed()
        {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_login:
                Intent intent2=new Intent(MainActivity.this,Splash_activity.class);
                startActivity(intent2);
                break;
            case R.id.nav_events:
                Intent intent3=new Intent(MainActivity.this,Events.class);
                startActivity(intent3);
                break;
            case R.id.nav_volenteer:
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
                finish();
                break;
            case R.id.nav_profile:
                Intent intent4=new Intent(MainActivity.this,Profile.class);
                startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    }

