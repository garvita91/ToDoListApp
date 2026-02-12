package com.example.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Task> tasks;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        this.context = context;
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView taskText;
        CheckBox checkBox;
        ImageView deleteIcon;
        ImageView editIcon;   // ✅ Added edit icon
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_task, parent, false);
            holder = new ViewHolder();
            holder.taskText = convertView.findViewById(R.id.taskText);
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            holder.deleteIcon = convertView.findViewById(R.id.deleteIcon);
            holder.editIcon = convertView.findViewById(R.id.editIcon);  // ✅ init edit icon
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task currentTask = tasks.get(position);

        // ✅ Set task text
        holder.taskText.setText(currentTask.title);

        // 🔥 Reset checkbox listener
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(currentTask.isChecked);

        // ✅ Apply strike-through
        if (currentTask.isChecked) {
            holder.taskText.setPaintFlags(
                    holder.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.taskText.setPaintFlags(
                    holder.taskText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        // ✅ Checkbox logic
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.isChecked = isChecked;

            if (isChecked) {
                holder.taskText.setPaintFlags(
                        holder.taskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.taskText.setPaintFlags(
                        holder.taskText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        });

        // =========================
        // ✏️ EDIT TASK
        // =========================
        holder.editIcon.setOnClickListener(v -> {

            EditText editInput = new EditText(context);
            editInput.setText(currentTask.title);
            editInput.setSelection(currentTask.title.length());

            new AlertDialog.Builder(context)
                    .setTitle("Edit Task")
                    .setView(editInput)
                    .setPositiveButton("Save", (dialog, which) -> {

                        String updated = editInput.getText().toString().trim();

                        if (!updated.isEmpty()) {
                            currentTask.title = updated;
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // =========================
        // 🗑 DELETE TASK
        // =========================
        holder.deleteIcon.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        tasks.remove(position);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        return convertView;
    }
}
