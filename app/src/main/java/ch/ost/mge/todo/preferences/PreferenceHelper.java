package ch.ost.mge.todo.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static final String PREFERENCES_FILE_NAME = "ch.ost.mge.todo.preferences";
    private static final String PREFERENCES_SHOW_STATE_KEY = "showState";
    private static final String PREFERENCES_THEME_KEY = "theme";

    private SharedPreferences _preferences;

    public PreferenceHelper(Context context) {
        _preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void setShowState(int showState) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(PREFERENCES_SHOW_STATE_KEY, showState);
        editor.commit();
    }

    public int getShowState() {
        return _preferences.getInt(PREFERENCES_SHOW_STATE_KEY, 0);
    }


    public void setTheme(int theme) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(PREFERENCES_THEME_KEY, theme);
        editor.commit();
    }

    public int getTheme() {
        return _preferences.getInt(PREFERENCES_THEME_KEY, 1);
    }
}
