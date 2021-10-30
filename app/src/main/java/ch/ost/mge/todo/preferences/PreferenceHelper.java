package ch.ost.mge.todo.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static final String PREFERENCES_FILE_NAME = "ch.ost.mge.todo.preferences";
    private static final String PREFERENCES_SHOW_STATE_KEY = "showState";
    private static final String PREFERENCES_THEME_KEY = "theme";

    private static SharedPreferences _preferences;

    public static void initialize(Context context) {
        _preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static int getShowState() {
        return _preferences.getInt(PREFERENCES_SHOW_STATE_KEY, 0);
    }

    public static void setShowState(int showState) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(PREFERENCES_SHOW_STATE_KEY, showState);
        editor.commit();
    }

    public static int getTheme() {
        return _preferences.getInt(PREFERENCES_THEME_KEY, 1);
    }

    public static void setTheme(int theme) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(PREFERENCES_THEME_KEY, theme);
        editor.commit();
    }
}
