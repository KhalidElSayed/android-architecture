package com.example.android.architecture.blueprints.todoapp.ui.tasks.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.ui.tasks.uimodel.TaskItem;

import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * View holder for the task item.
 */
final class TaskItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    private View mRow;

    private TextView mTitle;

    private CheckBox mCheckBox;

    private Action mOnItemClickAction;

    private Consumer<Boolean> mOnCheckAction;

    public TaskItemViewHolder(View rowView) {
        super(rowView);
        mRow = rowView;
        mTitle = rowView.findViewById(R.id.title);
        mCheckBox = rowView.findViewById(R.id.complete);

        rowView.setOnClickListener(this);
    }

    public void bindItem(TaskItem taskItem) {
        // disable the listener for the setup
        mCheckBox.setOnCheckedChangeListener(null);
        mTitle.setText(taskItem.getTask().getTitleForList());
        mCheckBox.setChecked(taskItem.getTask().isCompleted());
        mRow.setBackgroundResource(taskItem.getBackground());

        mOnCheckAction = taskItem.getOnCheckAction();
        mOnItemClickAction = taskItem.getOnClickAction();
        mCheckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickAction != null) {
            try {
                mOnItemClickAction.run();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnCheckAction != null) {
            try {
                mOnCheckAction.accept(isChecked);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
