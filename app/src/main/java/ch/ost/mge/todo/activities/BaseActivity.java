package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.preferences.PreferenceHelper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setThemeInternal(PreferenceHelper.getTheme());
        setContentView(R.layout.activity_base);
    }

    private void setThemeInternal(int theme) {
        switch(theme) {
            case 1:
                setTheme(R.style.AppThemeOrange);
                break;
            case 2:
                setTheme(R.style.AppThemeGreen);
                break;
            case 3:
                setTheme(R.style.AppThemeBlue);
                break;
        }
    }
}