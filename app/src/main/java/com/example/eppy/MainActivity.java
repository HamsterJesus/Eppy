package com.example.eppy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //create nav controller
    private static NavController navController;

    public static NavController getNavController() {
        return navController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NAVBAR CODE

        //define nav fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);

        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.navBar);
        NavigationUI.setupWithNavController(bottomNav, navController);

        //when buttons clicked on nav bar
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.homeMenuItem){
                navController.navigate(R.id.home2);
            } else if(itemId == R.id.alarmMenuItem){
                navController.navigate(R.id.alarm);
            } else if (itemId == R.id.settingsMenuItem) {
                navController.navigate(R.id.settings);
            }

            return true;
        });
    }
}