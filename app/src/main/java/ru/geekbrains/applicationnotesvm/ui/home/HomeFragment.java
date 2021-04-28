package ru.geekbrains.applicationnotesvm.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import ru.geekbrains.applicationnotesvm.MainActivity;
import ru.geekbrains.applicationnotesvm.R;
import ru.geekbrains.applicationnotesvm.ui.settings.SettingsFragment;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    private final Fragment settingsFragment = new SettingsFragment();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container,
                             @androidx.annotation.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();

        Toolbar toolbar = view.findViewById(R.id.home_toolbar);

        final DrawerLayout drawer = view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = view.findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.about) {
                    Toast.makeText(requireContext(), "Учебное приложение в рамках курса geekbrains", Toast.LENGTH_LONG).show();
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.archive) {
                    Toast.makeText(requireContext(), "Заметки, отправленные в архив", Toast.LENGTH_LONG).show();
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.settings) {
                    ((MainActivity) getActivity()).openTab(settingsFragment, "SettingsFragment");
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

}
