package ch.ost.mge.todo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Todo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String text;
    @ColumnInfo
    public Date dueDateTime;
    @ColumnInfo
    public boolean completed;
}
