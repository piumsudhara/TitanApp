package com.titan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MaterialSearchView searchView;
    private FirebaseAuth auth;
    TextView profile_email;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Home");
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fram,fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profile_email = (TextView)findViewById(R.id.profile_email);

        auth = FirebaseAuth.getInstance();
        if(FirebaseAuth.getInstance()!=null)
        {
            TextView profile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.profile_email);
            profile.setText(auth.getCurrentUser().getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            setTitle("Home");
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment);
            fragmentTransaction.commit();
        }
        if (id == R.id.nav_products)
        {
            setTitle("Categories");
            ProductFragment fragment = new ProductFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment);
            fragmentTransaction.commit();
        }
        if (id == R.id.nav_cart)
        {
            setTitle("Cart");
            CartFragment fragment = new CartFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_settings)
        {
            setTitle("Settings");
            SettingsFragment fragment = new SettingsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_about)
        {
            setTitle("About Us");
            AboutUsFragment fragment = new AboutUsFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_logout)
        {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DashBoard.this);
            alertDialogBuilder.setMessage("Are You Sure Want To Exit ?");
            alertDialogBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int arg1)
                {
                    auth.signOut();
                    if(auth.getCurrentUser() ==null)
                    {
                        final ProgressDialog pd = new ProgressDialog(DashBoard.this);
                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        pd.setMessage("Signing Out...");
                        pd.setIndeterminate(false);
                        pd.show();
                        progressStatus = 0;

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while(progressStatus < 100){
                                    progressStatus +=1;
                                    try
                                    {
                                        Thread.sleep(50);
                                    }catch(InterruptedException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            pd.setProgress(progressStatus);

                                            if(progressStatus == 70)
                                            {
                                                pd.dismiss();
                                                startActivity(new Intent(DashBoard.this,Login.class));
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }
            });
            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
