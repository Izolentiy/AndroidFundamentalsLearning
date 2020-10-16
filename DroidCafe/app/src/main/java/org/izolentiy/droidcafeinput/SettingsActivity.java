package org.izolentiy.droidcafeinput;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new GeneralFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class GeneralFragment extends PreferenceFragmentCompat {
        private SharedPreferences mPreferences;
        private String sharedPrefFile = "org.izolentiy.droidcafeinput";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.pref_general, rootKey);

            mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
            Preference preference = this.findPreference("example_switch");
            preference.setSummary(mPreferences.getString("summary", "OFF"));

            preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if ((Boolean) newValue) {
                        preference.setSummary("ON");
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString("summary", "ON").apply();
                    } else {
                        preference.setSummary("OFF");
                        SharedPreferences.Editor editor = mPreferences.edit();
                        editor.putString("summary", "OFF").apply();
                    }
                    return true;
                }
            });
        }
    }

    public static class RootFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}