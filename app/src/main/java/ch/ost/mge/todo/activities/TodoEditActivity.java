package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Calendar;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.database.Todo;
import ch.ost.mge.todo.database.TodoRepository;
import ch.ost.mge.todo.fragments.DatePickerFragment;
import ch.ost.mge.todo.fragments.TimePickerFragment;

public class TodoEditActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private final static float FULL_VISIBLE_ALPHA = 1.0f;
    private final static float HALF_VISIBLE_ALPHA = 0.5f;

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
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String title  = titleEditText.getText().toString();
                if (title.length() == 0) {
                    titleEditText.setError(getString(R.string.title_cannot_be_empty));
                }  else {
                    titleEditText.setError(null);
                }
                updateSaveButton();
            }
        });
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

            _todo = TodoRepository.getTodoById(_todoId);
            titleEditText.setText(_todo.title);
            textEditText.setText(_todo.text);
            completedSwitch.setChecked(_todo.completed);
        } else {
            _todo = new Todo();
            _todo.dueDateTime = Calendar.getInstance().getTime();
        }
        updateDueDisplay();
        updateSaveButton();
    }

    private void updateSaveButton() {
        boolean isSaveButtonEnabled = titleEditText.getText().toString().length() > 0;
        float buttonAlpha = isSaveButtonEnabled ? FULL_VISIBLE_ALPHA : HALF_VISIBLE_ALPHA;

        saveButton.setEnabled(isSaveButtonEnabled);
        saveButton.setAlpha(buttonAlpha);
    }

    private void saveTodo() {
        _todo.title = titleEditText.getText().toString();
        _todo.text = textEditText.getText().toString();
        _todo.completed = _todoId != 0 ? completedSwitch.isChecked() : false;

        if(_todoId > 0) {
            TodoRepository.updateTodo(_todo);
        } else {
            TodoRepository.addTodo(_todo);
        }

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
                        .setTitle(R.string.delete_todo_title)
                        .setMessage(R.string.delete_todo_message)
                        .setCancelable(false)
                        .setPositiveButton("Yes", (d, id) -> {
                            TodoRepository.deleteTodo(_todo);

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