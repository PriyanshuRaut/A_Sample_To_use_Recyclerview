package com.masters.recyclerview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.masters.recyclerview.database.AppDatabase;
import com.masters.recyclerview.database.dao.DAO;
import com.masters.recyclerview.database.model.modelDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onClick {

    public AppDatabase appDatabase;
    List<modelDatabase> model_dbList = new ArrayList<>();
    Adapter_db getAdapter;

    RecyclerView recyclerView;
    FloatingActionButton addBtn;
    LinearLayout addLayout;
    boolean isSaveLayout = true;
    EditText addTask;
    Button saveBtn;
    private DAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        addLayout = findViewById(R.id.saveLayout);
        addBtn = findViewById(R.id.addBtn);
        addTask = findViewById(R.id.task_editText);
        saveBtn = findViewById(R.id.saveBtn);

        // Initialize Room database
        appDatabase = AppDatabase.getDatabase(this);
        userDao = appDatabase.userDao();

        // Initialize RecyclerView adapter
        getAdapter = new Adapter_db(model_dbList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getAdapter);
        getAdapter.setClickListener(this);

        // Add button logic
        addBtn.setOnClickListener(v -> {
            if (isSaveLayout) {
                addLayout.setVisibility(View.VISIBLE);
                isSaveLayout = false;
            } else {
                addLayout.setVisibility(View.GONE);
                isSaveLayout = true;
            }
        });

        // Save button logic
        saveBtn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(addTask.getText().toString())) {
                String taskText = addTask.getText().toString();
                modelDatabase model = new modelDatabase(taskText);

                // Insert data into Room database on background thread
                new task().execute(model);

                // Clear the EditText after saving
                addTask.setText("");
            }
        });
    }

    @Override
    public void onClick(View v, int pos) {
        Toast.makeText(this, "youClicked: " + model_dbList.get(pos).getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Fetch data from the database when the activity starts
        new GetAllUsersTask().execute();
    }

    // AsyncTask for inserting data
    private class InsertTask extends AsyncTask<modelDatabase, Void, Void> {
        @Override
        protected Void doInBackground(modelDatabase... models) {
            userDao.insert(models[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            // After inserting, fetch the updated data to refresh the RecyclerView
            new GetAllUsersTask().execute();
        }
    }

    private class task extends AsyncTask<modelDatabase,Void,Void>{
        @Override
        protected Void doInBackground(modelDatabase... modelDatabases) {
            userDao.insert(modelDatabases[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            new getusers().execute();
        }
    }

    // AsyncTask for fetching data from Room
    private class GetAllUsersTask extends AsyncTask<Void, Void, List<modelDatabase>> {
        @Override
        protected List<modelDatabase> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }

        @Override
        protected void onPostExecute(List<modelDatabase> models) {
            model_dbList.clear();
            model_dbList.addAll(models);
            getAdapter.notifyDataSetChanged();  // Notify the adapter that the data has changed
        }
    }

    private class getusers extends AsyncTask<Void,Void,List<modelDatabase>>{

        @Override
        protected List<modelDatabase> doInBackground(Void... voids) {
            return userDao.getAllUsers();
        }

        @Override
        protected void onPostExecute(List<modelDatabase> modelDatabases) {
            model_dbList.clear();
            model_dbList.addAll(modelDatabases);
            getAdapter.notifyDataSetChanged();
        }
    }
}
