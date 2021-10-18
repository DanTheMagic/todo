package ch.ost.mge.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ch.ost.mge.todo.adapter.TodoAdapter;
import ch.ost.mge.todo.database.Todo;
import ch.ost.mge.todo.database.TodoDatabase;

public class TodoListActivity extends AppCompatActivity {
    private TodoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new TodoAdapter();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.todo_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        loadTodos();
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

    private void loadTodos() {
        Runnable loadTodosAsync = () -> {
            TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").build();

            List<Todo> todos = db.todoDao().getEntries();
            adapter.updateTodos(todos);

            db.close();
        };

        new Thread(loadTodosAsync).start();
    }

}