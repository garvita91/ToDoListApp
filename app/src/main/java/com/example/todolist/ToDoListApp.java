package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ToDoListApp extends AppCompatActivity {

    EditText editTextTask;
    Button btnAdd;
    ListView listViewTasks;

    ArrayList<String> taskList;
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

        btnAdd.setOnClickListener(v -> {
            String task = editTextTask.getText().toString().trim();
            if (!task.isEmpty()) {
                taskList.add(task);
                adapter.notifyDataSetChanged();
                editTextTask.setText("");
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}