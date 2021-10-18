package ch.ost.mge.todo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM todo")
    List<Todo> getEntries();

    @Insert
    void insert(Todo todo);

    @Delete
    void delete(Todo todo);
}
