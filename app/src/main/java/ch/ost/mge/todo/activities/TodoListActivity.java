package ch.ost.mge.todo.activities;

import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.adapter.TodoAdapter;
import ch.ost.mge.todo.database.Todo;
import ch.ost.mge.todo.database.TodoRepository;
import ch.ost.mge.todo.preferences.PreferenceHelper;

public class TodoListActivity extends BaseActivity {
    private TextView bottomText;
    private ImageView noTodoImage;
    private TextView noTodoText;

    private TodoAdapter _adapter;
    private int _showState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _showState = PreferenceHelper.getShowState();

        setContentView(R.layout.activity_todo_list);

        bottomText = findViewById(R.id.bottom_text);
        noTodoImage = findViewById(R.id.no_todo_image);
        noTodoText = findViewById(R.id.no_todo_text);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        _adapter = new TodoAdapter();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        RecyclerView recyclerView = findViewById(R.id.todo_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(_adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        loadTodos();
    }

    private void loadTodos() {
        List<Todo> todos = null;
        String todoText = "";
        switch(_showState) {
            case 0:
                todos = TodoRepository.getUncompletedTodos();
                todoText = getString(R.string.open);
                break;
                case 1:
                todos = TodoRepository.getCompletedTodos();
                todoText = getString(R.string.completed);
                break;
            case 2:
                todos = TodoRepository.getAllTodos();
                todoText = getString(R.string.all);
                break;
        }

        if(todos != null) {
            _adapter.updateTodos(todos, _showState);
            if(todos.size() > 0) {
                bottomText.setText(todos.size() + " " + (todos.size() > 1 ? getString(R.string.todos) : getString(R.string.todo)) + " (" + todoText + ")");
                noTodoImage.setVisibility(View.GONE);
                noTodoText.setVisibility(View.GONE);
            } else {
                bottomText.setText("(" + todoText + ")");
                noTodoImage.setVisibility(View.VISIBLE);
                noTodoText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_item_add:
                Intent intentTodoEdit = new Intent(this, TodoEditActivity.class);
                startActivity(intentTodoEdit);
                break;
            case R.id.menu_item_show_only_open:
                _showState = 0;
                break;
            case R.id.menu_item_show_only_completed:
                _showState = 1;
                break;
            case R.id.menu_item_show_all:
                _showState = 2;
                break;
            case R.id.menu_item_select_theme:
                Intent intentSelectTheme = new Intent(this, SelectThemeActivity.class);
                startActivity(intentSelectTheme);
                return true;
        }

        if(itemId == R.id.menu_item_show_all || itemId == R.id.menu_item_show_only_open ||
                itemId == R.id.menu_item_show_only_completed) {
            PreferenceHelper.setShowState(_showState);
            loadTodos();
        }

        return true;
    }
}