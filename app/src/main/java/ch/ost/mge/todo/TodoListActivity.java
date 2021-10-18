package ch.ost.mge.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import ch.ost.mge.todo.database.Todo;
import ch.ost.mge.todo.database.TodoDatabase;

public class TodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        //writeDatabaseWithRoom();
    }

    private void writeDatabaseWithRoom() {
        Runnable write = () -> {
            Todo todo = new Todo();
            todo.title = "title";
            todo.text = "das ist ein text";

            TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").build();
            db.todoDao().insert(todo);
            db.close();
        };

        new Thread(write).start();
    }
}