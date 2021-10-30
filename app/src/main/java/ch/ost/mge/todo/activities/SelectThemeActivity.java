package ch.ost.mge.todo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.preferences.PreferenceHelper;

public class SelectThemeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_theme);

        Button orangeButton = findViewById(R.id.orange_button);
        orangeButton.setOnClickListener(v -> chooseTheme(1));

        Button greenButton = findViewById(R.id.green_button);
        greenButton.setOnClickListener(v -> chooseTheme(2));

        Button blueButton = findViewById(R.id.blue_button);
        blueButton.setOnClickListener(v -> chooseTheme(3));
    }

    private void chooseTheme(int theme) {
        PreferenceHelper.setTheme(theme);

        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }
}