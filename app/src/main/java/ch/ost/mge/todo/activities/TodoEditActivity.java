package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.database.Todo;
import ch.ost.mge.todo.database.TodoDatabase;

public class TodoEditActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText textEditText;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);

        titleEditText = findViewById(R.id.title_edittext);
        textEditText = findViewById(R.id.text_edittext);

        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> saveTodo());
    }

    private void saveTodo() {
        String title = titleEditText.getText().toString();
        String text = textEditText.getText().toString();

        Todo todo = new Todo();
        todo.title = title;
        todo.text = text;

        Runnable addToDb = () -> {
            TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").build();
            db.todoDao().insert(todo);
            db.close();

            Intent intent = new Intent(this, TodoListActivity.class);
            startActivity(intent);
        };

        new Thread(addToDb).start();
    }
}