package ru.geekbrains.applicationnotesvm.ui.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import ru.geekbrains.applicationnotesvm.R;

public class SettingsFragment extends Fragment {

    public static final String TAG = "SettingsFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();

        TextView textViewTheme = view.findViewById(R.id.theme_settings);
        textViewTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupThemeMenu = new PopupMenu(activity, textViewTheme);
                popupThemeMenu.inflate(R.menu.popup_theme_menu);
                popupThemeMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_dark_theme) {
                            Toast.makeText(requireContext(), "Применена тёмная тема", Toast.LENGTH_LONG).show();
                            return true;
                        } else if (id == R.id.action_light_theme) {
                            Toast.makeText(requireContext(), "Применена светлая тема", Toast.LENGTH_LONG).show();
                            return true;
                        } else if (id == R.id.action_grey_theme) {
                            Toast.makeText(requireContext(), "Применена серая тема", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                popupThemeMenu.show();
            }
        });

        TextView textViewDisplay = view.findViewById(R.id.display_settings);
        textViewDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupDisplayMenu = new PopupMenu(activity, textViewDisplay);
                popupDisplayMenu.inflate(R.menu.popup_display_menu);
                popupDisplayMenu.show();
                popupDisplayMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_display_list) {
                            Toast.makeText(requireContext(), "Отображение заметок списком", Toast.LENGTH_LONG).show();
                            return true;
                        } else if (id == R.id.action_display_two_column) {
                            Toast.makeText(requireContext(), "Отображение заметок в 2 колонки", Toast.LENGTH_LONG).show();
                            return true;
                        } else if (id == R.id.action_display_three_column) {
                            Toast.makeText(requireContext(), "Отображение заметок в 3 колонки", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
            }
        });
    }

}