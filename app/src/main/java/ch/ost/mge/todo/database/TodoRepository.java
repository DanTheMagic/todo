package ch.ost.mge.todo.database;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class TodoRepository {
    private static TodoDatabase database;

    public static void initialize(Context context) {
        database = Room.databaseBuilder(context, TodoDatabase.class, "todo.db").allowMainThreadQueries().build();
    }

    public static List<Todo> getUncompletedTodos() {
        return database.todoDao().getUncompleted();
    }

    public static List<Todo> getCompletedTodos() {
        return database.todoDao().getCompleted();
    }

    public static List<Todo> getAllTodos() {
        return database.todoDao().getAll();
    }

    public static Todo getTodoById(int id) {
        return database.todoDao().getById(id);
    }

    public static void addTodo(Todo todo) {
        database.todoDao().insert(todo);
    }

    public static void updateTodo(Todo todo) {
        database.todoDao().update(todo);
    }

    public static void deleteTodo(Todo todo) {
        database.todoDao().delete(todo);
    }
}
