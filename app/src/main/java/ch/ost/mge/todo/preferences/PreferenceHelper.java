package ch.ost.mge.todo.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static final String PREFERENCES_FILE_NAME = "ch.ost.mge.todo.preferences";
    private static final String PREFERENCES_SHOW_STATE_KEY = "showState";

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
}
