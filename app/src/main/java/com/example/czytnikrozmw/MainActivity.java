package com.example.czytnikrozmw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int OPEN_DIRECTORY_REQUEST_CODE = 789;

    ArrayList<Caller_model> callerModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button choseDir = findViewById(R.id.chose_dir);
        RecyclerView recyclerView = findViewById(R.id.mRecView);
        Context context = this;
        //choseDir.setOnClickListener(v -> openDir());
        openDir();

        choseDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Caller_RecyclerViewAdapter adapter = new Caller_RecyclerViewAdapter(context,callerModels);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        });



    }

    private void openDir(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent,OPEN_DIRECTORY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == OPEN_DIRECTORY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(resultData != null){
                Uri treeRes = resultData.getData();
                if(treeRes != null){
                    DocumentFile documentFile = DocumentFile.fromTreeUri(this,treeRes);
                    DocumentFile[] files = documentFile.listFiles();
                    for(int i = 0; i < files.length;i++) {
                        //Caller_model callerModel = new Caller_model(files[i].getName(),files[i].getUri());
                        callerModels.add(new Caller_model(files[i].getName(),files[i].getUri()));
                    }
                }
            }
        }
    }

}