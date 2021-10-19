package ch.ost.mge.todo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
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
    private TextView bottomText;

    private TodoAdapter _adapter;
    private int _showState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        bottomText = findViewById(R.id.bottom_text);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        _adapter = new TodoAdapter();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.todo_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(_adapter);
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
        TodoDatabase db = Room.databaseBuilder(this, TodoDatabase.class, "todo.db").allowMainThreadQueries().build();
        List<Todo> todos = null;
        String todoText = "";
        switch(_showState) {
            case 0:
                todos = db.todoDao().getAll();
                todoText = "All";
                break;
            case 1:
                todos = db.todoDao().getUncompleted();
                todoText = "Open";
                break;
                case 2:
                todos = db.todoDao().getCompleted();
                todoText = "Completed";
                break;
        }

        if(todos != null) {
            _adapter.updateTodos(todos, _showState);
            bottomText.setText(todos.size() + " " + (todos.size() > 1 ? "Todos" : "Todo") + " (" + todoText + ")");
        }
        db.close();
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
            case R.id.menu_show_all:
                _showState = 0;
                loadTodos();
                break;
            case R.id.menu_show_only_open:
                _showState = 1;
                loadTodos();
                break;
            case R.id.menu_show_only_completed:
                _showState = 2;
                loadTodos();
                break;
        }
        return true;
    }

}