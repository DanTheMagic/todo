package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.database.Todo;
import ch.ost.mge.todo.database.TodoDatabase;
import ch.ost.mge.todo.fragments.DatePickerFragment;
import ch.ost.mge.todo.fragments.TimePickerFragment;

public class TodoEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText titleEditText;
    private EditText textEditText;
    private EditText dueDateEditText;
    private EditText dueTimeEditText;
    private Button saveButton;
    private TextView completedText;
    private SwitchCompat completedSwitch;

    private Todo _todo;
    private int _todoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);

        titleEditText = findViewById(R.id.title_edittext);
        textEditText = findViewById(R.id.text_edittext);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveTodo());

        dueDateEditText = findViewById(R.id.due_date_edittext);
        dueDateEditText.setEnabled(false);

        dueTimeEditText = findViewById(R.id.due_time_edittext);
        dueTimeEditText.setEnabled(false);

        completedText = findViewById(R.id.completed_text);
        completedSwitch = findViewById(R.id.completed_switch);

        _todoId = this.getIntent().getIntExtra("todoId", 0);

        if(_todoId > 0) {
            completedText.setVisibility(View.VISIBLE);
            completedSwitch.setVisibility(View.VISIBLE);

            TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").allowMainThreadQueries().build();
            _todo = db.todoDao().getById(_todoId);
            db.close();

            titleEditText.setText(_todo.title);
            textEditText.setText(_todo.text);
            completedSwitch.setChecked(_todo.completed);
        } else {
            _todo = new Todo();
            _todo.dueDateTime = Calendar.getInstance().getTime();
        }
        updateDueDisplay();
    }

    private void saveTodo() {
        _todo.title = titleEditText.getText().toString();
        _todo.text = textEditText.getText().toString();
        _todo.completed = _todoId != 0 ? completedSwitch.isChecked() : false;

        TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").allowMainThreadQueries().build();
        if(_todoId > 0) {
            db.todoDao().update(_todo);
        } else {
            db.todoDao().insert(_todo);
        }
        db.close();

        Intent intent = new Intent(this, TodoListActivity.class);
        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment(_todo.dueDateTime);
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timeFragment = new TimePickerFragment(_todo.dueDateTime);
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        _todo.dueDateTime.setYear(year-1900);
        _todo.dueDateTime.setMonth(month);
        _todo.dueDateTime.setDate(dayOfMonth);
        updateDueDisplay();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _todo.dueDateTime.setHours(hourOfDay);
        _todo.dueDateTime.setMinutes(minute);
        updateDueDisplay();
    }

    private void updateDueDisplay() {
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(_todo.dueDateTime.getTime());
        dueDateEditText.setText(currentDateString);

        DateFormat outputFormatter = new SimpleDateFormat("HH:mm");
        dueTimeEditText.setText(outputFormatter.format(_todo.dueDateTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        menu.findItem(R.id.menu_item_delete).setVisible(_todoId > 0);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                AlertDialog dialog;
                dialog = new AlertDialog.Builder(this)
                        .setTitle("Delete Todo")
                        .setMessage("Do you really want to delete this todo?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (d, id) -> {
                            TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").allowMainThreadQueries().build();
                            db.todoDao().delete(_todo);
                            db.close();

                            Intent intent = new Intent(this, TodoListActivity.class);
                            startActivity(intent);
                        })
                        .setNegativeButton("No", (d, id) -> { /* Do nothing */ })
                        .create();
                dialog.show();
                break;
        }

        return true;
    }
}