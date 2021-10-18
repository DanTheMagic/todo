package ch.ost.mge.todo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
                android.R.layout.simple_list_item_2,
                parent,
                false);

        TextView titleTextView = view.findViewById(android.R.id.text1);
        TextView textTextView = view.findViewById(android.R.id.text2);

        return new TodoViewHolder(view, titleTextView, textTextView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = this.todos.get(position);
        holder.titleTextView.setText(todo.title);
        holder.textTextView.setText(todo.text);
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }
}
