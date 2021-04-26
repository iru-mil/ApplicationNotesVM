package ru.geekbrains.applicationnotesvm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.geekbrains.applicationnotesvm.domain.Note;
import ru.geekbrains.applicationnotesvm.ui.add.AddFragment;
import ru.geekbrains.applicationnotesvm.ui.update.UpdateFragment;
import ru.geekbrains.applicationnotesvm.ui.home.HomeFragment;
import ru.geekbrains.applicationnotesvm.ui.notes.NotesFragment;
import ru.geekbrains.applicationnotesvm.ui.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NotesFragment.OnNoteSelected, UpdateFragment.OnNoteSaved {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            int itemID = item.getItemId();
            if (itemID == R.id.navigation_home) {
                openTab(new HomeFragment(), HomeFragment.TAG);
                return true;
            } else if (itemID == R.id.navigation_notes) {
                openTab(new NotesFragment(), NotesFragment.TAG);
                return true;
            } else if (itemID == R.id.navigation_settings) {
                openTab(new SettingsFragment(), SettingsFragment.TAG);
                return true;
            } else if (itemID == R.id.navigation_adding_update) {
                openTab(new AddFragment(), AddFragment.TAG);
                return true;
            }
            return false;
        });
        if (savedInstanceState == null) {
            openTab(new HomeFragment(), HomeFragment.TAG);
        }
    }

    private void openTab(Fragment fragment, String tag) {
        Fragment addedFragment = getSupportFragmentManager().findFragmentByTag(tag);
        getSupportFragmentManager().popBackStack();
        if (addedFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.host_fragment, fragment, tag)
                    .commit();
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.host_fragment, UpdateFragment.newInstance(note), UpdateFragment.TAG)
                .addToBackStack(UpdateFragment.TAG)
                .commit();
    }

    @Override
    public void onNoteSaved() {
        getSupportFragmentManager()
                .popBackStack();
    }
}