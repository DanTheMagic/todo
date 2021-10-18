package ch.ost.mge.todo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Todo.class}, version = 1)
@TypeConverters({DateConverters.class})
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
}

