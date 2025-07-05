package com.example.demochat;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import androidx.core.graphics.Insets;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TabbedActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge(); // Optional depending on your implementation
        setContentView(R.layout.activity_tabbed);

        setupWindowInsets();
        initViews();
        setupNavigation(savedInstanceState);
    }

    private void enableEdgeToEdge() {
        // This is equivalent to enableEdgeToEdge() in Kotlin
        getWindow().setDecorFitsSystemWindows(false);
    }

    private void setupWindowInsets() {
        View main = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void setupNavigation(Bundle savedInstanceState) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = createFragmentForNavItem(item.getItemId());
            replaceFragment(fragment);
            return true;
        });

        // Set default fragment only when activity is first created
        if (savedInstanceState == null) {
            replaceFragment(new ChatsFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_chats);
        }
    }

    private Fragment createFragmentForNavItem(int itemId) {
        if (itemId == R.id.nav_chats) {
            return new ChatsFragment();
        } else if (itemId == R.id.nav_call_logs) {
            return new CallLogsFragment();
        } else if (itemId == R.id.nav_users) {
            return new UsersFragment();
        } else if (itemId == R.id.nav_groups) {
            return new GroupsFragment();
        } else {
            return new ChatsFragment();
        }
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
