package com.example.todolist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ToDoListApp extends AppCompatActivity {

    EditText editTextTask;
    Button btnAdd;
    ListView listViewTasks;

    ArrayList<Task> taskList;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        editTextTask = findViewById(R.id.editTextTask);
        btnAdd = findViewById(R.id.btnAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList);
        listViewTasks.setAdapter(adapter);

        // =========================
        // ADD TASK
        // =========================
        btnAdd.setOnClickListener(v -> {
            String taskText = editTextTask.getText().toString().trim();

            if (!taskText.isEmpty()) {
                taskList.add(new Task(taskText));
                adapter.notifyDataSetChanged();
                editTextTask.setText("");
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });

        // =========================
        // EDIT TASK (Single Click)
        // =========================
//        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
//
//            Task currentTask = taskList.get(position);
//
//            EditText editTaskInput = new EditText(this);
//            editTaskInput.setText(currentTask.title);
//            editTaskInput.setSelection(currentTask.title.length());
//
//            new AlertDialog.Builder(this)
//                    .setTitle("Edit Task")
//                    .setView(editTaskInput)
//                    .setPositiveButton("Save", (dialog, which) -> {
//
//                        String updatedTask = editTaskInput.getText().toString().trim();
//
//                        if (!updatedTask.isEmpty()) {
//                            currentTask.title = updatedTask;
//                            adapter.notifyDataSetChanged();
//                        } else {
//                            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .setNegativeButton("Cancel", null)
//                    .show();
//        });

        // =========================
        // DELETE TASK (Long Click)
        // =========================
        listViewTasks.setOnItemLongClickListener((parent, view, position, id) -> {
            taskList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
