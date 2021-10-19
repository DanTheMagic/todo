package ch.ost.mge.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ch.ost.mge.todo.R;
import ch.ost.mge.todo.activities.TodoEditActivity;
import ch.ost.mge.todo.database.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private List<Todo> todos;

    public TodoAdapter() {
        this.todos = new ArrayList<>();
    }

    public void updateTodos(List<Todo> todos) {
        this.todos = todos;
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

        return new TodoViewHolder(view, titleTextView, textTextView, dueDateTimeTextView);
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

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, TodoEditActivity.class);
            intent.putExtra("todoId", todo.id);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }
}
