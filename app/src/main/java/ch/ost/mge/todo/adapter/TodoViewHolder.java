package ch.ost.mge.todo.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView textTextView;

    public TodoViewHolder(View parent, TextView titleTextView, TextView textTextView) {
        super(parent);
        this.titleTextView = titleTextView;
        this.textTextView = textTextView;
    }
}
