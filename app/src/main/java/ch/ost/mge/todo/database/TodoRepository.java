package ch.ost.mge.todo.database;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class TodoRepository {
    private static TodoDatabase _database;

    public static void initialize(Context context) {
        _database = Room.databaseBuilder(context, TodoDatabase.class, "todo.db").allowMainThreadQueries().build();
    }

    public static List<Todo> getUncompletedTodos() {
        return _database.todoDao().getUncompleted();
    }

    public static List<Todo> getCompletedTodos() {
        return _database.todoDao().getCompleted();
    }

    public static List<Todo> getAllTodos() {
        return _database.todoDao().getAll();
    }

    public static Todo getTodoById(int id) {
        return _database.todoDao().getById(id);
    }

    public static void addTodo(Todo todo) {
        _database.todoDao().insert(todo);
    }

    public static void updateTodo(Todo todo) {
        _database.todoDao().update(todo);
    }

    public static void deleteTodo(Todo todo) {
        _database.todoDao().delete(todo);
    }
}
