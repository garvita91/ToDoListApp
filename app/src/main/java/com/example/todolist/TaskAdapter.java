package com.example.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class TaskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> tasks;
    private LayoutInflater inflater;

    public TaskAdapter(Context context, ArrayList<String> tasks) {
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
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_task, parent, false);
            holder = new ViewHolder();
            holder.taskText = convertView.findViewById(R.id.taskText);
            holder.checkBox = convertView.findViewById(R.id.checkBox);
            holder.deleteIcon = convertView.findViewById(R.id.deleteIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.taskText.setText(tasks.get(position));

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

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            holder.taskText.setPaintFlags(
                    isChecked ? holder.taskText.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
                            : holder.taskText.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG)
            );
        });

        return convertView;
    }
}