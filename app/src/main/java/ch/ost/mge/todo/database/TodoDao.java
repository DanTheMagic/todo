package ch.ost.mge.todo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo ORDER BY dueDateTime ASC")
    List<Todo> getAll();

    @Query("SELECT * FROM todo WHERE completed = 0 ORDER BY dueDateTime ASC")
    List<Todo> getUncompleted();

    @Query("SELECT * FROM todo WHERE completed = 1 ORDER BY dueDateTime ASC")
    List<Todo> getCompleted();

    @Query("SELECT * FROM todo WHERE id = :id")
    Todo getById(int id);

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);
}
