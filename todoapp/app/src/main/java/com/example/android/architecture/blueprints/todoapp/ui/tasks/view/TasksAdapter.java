package com.example.android.architecture.blueprints.todoapp.ui.tasks.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.uimodel.TaskItem;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adapter for the list of tasks.
 */
final class TasksAdapter extends RecyclerView.Adapter<TaskItemViewHolder> {

    private List<TaskItem> mTasks;

    public TasksAdapter() {
        setList(Collections.emptyList());
    }

    @NonNull
    @Override
    public TaskItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskItemViewHolder holder, int position) {
        final TaskItem taskItem = getItem(position);
        holder.bindItem(taskItem);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public TaskItem getItem(int i) {
        return mTasks.get(i);
    }

    public void replaceData(List<TaskItem> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    private void setList(List<TaskItem> tasks) {
        mTasks = checkNotNull(tasks);
    }
}
