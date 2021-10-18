package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

    private Date dueDateTime = Calendar.getInstance().getTime();

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

        updateDueDisplay();
    }

    private void saveTodo() {
        String title = titleEditText.getText().toString();
        String text = textEditText.getText().toString();

        Todo todo = new Todo();
        todo.title = title;
        todo.text = text;
        todo.dueDateTime = dueDateTime;
        todo.completed = false;

        Runnable addToDb = () -> {
            TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").build();
            db.todoDao().insert(todo);
            db.close();

            Intent intent = new Intent(this, TodoListActivity.class);
            startActivity(intent);
        };

        new Thread(addToDb).start();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dueDateTime.setYear(year-1900);
        dueDateTime.setMonth(month);
        dueDateTime.setDate(dayOfMonth);
        updateDueDisplay();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dueDateTime.setHours(hourOfDay);
        dueDateTime.setMinutes(minute);
        updateDueDisplay();
    }

    private void updateDueDisplay() {
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(dueDateTime.getTime());
        dueDateEditText.setText(currentDateString);

        DateFormat outputFormatter = new SimpleDateFormat("HH:mm");
        dueTimeEditText.setText(outputFormatter.format(dueDateTime));
    }
}