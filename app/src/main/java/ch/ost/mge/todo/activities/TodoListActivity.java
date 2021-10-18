package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import ch.ost.mge.todo.R;
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

            //TODO Darf man das hier drin nicht machen?
            List<Todo> todos = db.todoDao().getEntries();
            adapter.updateTodos(todos);

            db.close();
        };

        new Thread(loadTodosAsync).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(this, TodoEditActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

}