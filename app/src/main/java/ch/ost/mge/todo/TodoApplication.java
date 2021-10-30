package ch.ost.mge.todo;

import android.app.Application;
import ch.ost.mge.todo.database.TodoRepository;

public class TodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TodoRepository.initialize(getApplicationContext());
    }
}