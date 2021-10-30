package ch.ost.mge.todo.adapter;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.activities.TodoEditActivity;
import ch.ost.mge.todo.database.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private List<Todo> todos;
    private int showState;

    public TodoAdapter() {
        this.todos = new ArrayList<>();
    }

    public void updateTodos(List<Todo> todos, int showState) {
        this.todos = todos;
        this.showState = showState;

        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                R.layout.todo_row,
                parent,
                false);

        TextView titleTextView = view.findViewById(R.id.row_title_text);
        TextView textTextView = view.findViewById(R.id.row_text_text);
        TextView dueDateTimeTextView = view.findViewById(R.id.row_due_datetime_text);
        ImageView completedImageView = view.findViewById(R.id.row_completed_image);

        return new TodoViewHolder(view, titleTextView, textTextView, dueDateTimeTextView, completedImageView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        Todo todo = this.todos.get(position);
        holder.titleTextView.setText(todo.title);
        holder.textTextView.setText(todo.text.indexOf("\n") >= 0 ? todo.text.substring(0, todo.text.indexOf("\n")) : todo.text);

        String dateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(todo.dueDateTime.getTime());
        DateFormat outputFormatter = new SimpleDateFormat("HH:mm");
        String timeString = outputFormatter.format(todo.dueDateTime.getTime());
        holder.dueDateTimeTextView.setText(dateString + " " + timeString);

        holder.dueDateTimeTextView.setTextColor(getColor(context, showState == 0 ? getOpenTodoColor(todo) : R.color.black));

        holder.completedImageView.setVisibility(showState == 2 && todo.completed ? View.VISIBLE : View.INVISIBLE);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, TodoEditActivity.class);
            intent.putExtra("todoId", todo.id);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    private int getOpenTodoColor(Todo todo) {
        if(todo.completed)
            return R.color.black;

        Calendar calendar = Calendar.getInstance();
        if(todo.dueDateTime.getTime() < calendar.getTimeInMillis()) {
            return R.color.red;
        }

        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        if(todo.dueDateTime.getTime() < calendar.getTimeInMillis()) {
            return R.color.orange_todo;
        }

        return R.color.black;
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }
}
