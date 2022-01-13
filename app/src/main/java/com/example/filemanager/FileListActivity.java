package com.example.filemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class FileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        TextView noFilesTxt = findViewById(R.id.nofilestextview);

        String path = getIntent().getStringExtra("path");

        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        if(filesAndFolders == null || filesAndFolders.length == 0) {
            noFilesTxt.setVisibility(View.VISIBLE);
            return;
        }



        noFilesTxt.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new Adapter(getApplicationContext(),filesAndFolders));


    }
}