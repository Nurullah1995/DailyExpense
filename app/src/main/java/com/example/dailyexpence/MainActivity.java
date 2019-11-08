package com.example.dailyexpence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Dashboard");
        replaceFragment(new DashboardFragment());
        BottomNavigationView bottomNavigationView=findViewById(R.id.mainBottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.dashboard:
                        setTitle("Dashboard");
                        replaceFragment(new DashboardFragment());
                        //Toast.makeText(MainActivity.this, "dashboard", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.expenses:
                        setTitle("Expenses");
                        replaceFragment(new ExpensesFragment());

                        // Toast.makeText(MainActivity.this, "expenses", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });

    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame,fragment);
        ft.commit();
    }
}
