package com.dzondza.vasya.diagnostix;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.dzondza.vasya.diagnostix.MainContent.AndroidFragment;
import com.dzondza.vasya.diagnostix.MainContent.BatteryFragment;
import com.dzondza.vasya.diagnostix.MainContent.CamerasFragment;
import com.dzondza.vasya.diagnostix.MainContent.DirectoriesFragment;
import com.dzondza.vasya.diagnostix.MainContent.DisplayFragment;
import com.dzondza.vasya.diagnostix.MainContent.InstalledAppsFragment;
import com.dzondza.vasya.diagnostix.MainContent.NetworkFragment;
import com.dzondza.vasya.diagnostix.MainContent.SensorsFragment;
import com.dzondza.vasya.diagnostix.MainContent.SystemFragment;
import com.dzondza.vasya.diagnostix.MainContent.DeviceFragment;


/**
 * Program main activity:
 * creates and initializes toolbar, drawerlayout
 */

public class MainScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    private DrawerLayout drawerLayout;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        //sets text to navigationView header's textViews
        View header = navigationView.getHeaderView(0);
        TextView headerUpper = header.findViewById(R.id.header_textView_upper);
        headerUpper.setText(Build.BRAND);
        TextView headerLower = header.findViewById(R.id.header_textView_lower);
        headerLower.setText(Build.MODEL);


        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_fragment_container, new DeviceFragment()).commit();


        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_device));
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_item_developer:
                intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                startActivity(intent);
                return true;
            case R.id.menu_item_accounts:
                intent = new Intent(Settings.ACTION_ADD_ACCOUNT);
                startActivity(intent);
                return true;
            case R.id.menu_item_security:
                intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(intent);
                return true;
            case R.id.menu_item_search:
                intent = new Intent(Settings.ACTION_SEARCH_SETTINGS);
                startActivity(intent);
                return true;
            case R.id.menu_item_sound:
                intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
                startActivity(intent);
                return true;
            case R.id.menu_item_exit:
                finish();
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       // selectDrawerItem(item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_system:
                fragment = new SystemFragment();
                break;
            case R.id.nav_device:
                fragment = new DeviceFragment();
                break;
            case R.id.nav_installed_app:
                fragment = new InstalledAppsFragment();
                break;
            case R.id.nav_display:
                fragment = new DisplayFragment();
                break;
            case R.id.nav_network:
                fragment = new NetworkFragment();
                break;
            case R.id.nav_android:
                fragment = new AndroidFragment();
                break;
            case R.id.nav_battery:
                fragment = new BatteryFragment();
                break;
            case R.id.nav_camera:
                fragment = new CamerasFragment();
                break;
            case R.id.nav_sensors:
                fragment = new SensorsFragment();
                break;
            case R.id.nav_directories:
                fragment = new DirectoriesFragment();
                break;
        }

        transaction = getSupportFragmentManager().beginTransaction();
        if (fragment != null) {
            transaction.addToBackStack(null);
            transaction.replace(R.id.activity_main_fragment_container, fragment).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}