package com.example.myclosetapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.ui.ClotheAdapter;
import com.example.myclosetapp.ui.ClotheViewModel;

import com.example.myclosetapp.utils.FormatDateTime;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_CLOTHE_REQUEST=200;

    private ClotheViewModel clotheViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddClothe = findViewById(R.id.button_add_clothe);
        buttonAddClothe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddClotheActivity.class);
                startActivityForResult(intent,ADD_CLOTHE_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view_clothe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ClotheAdapter adapter = new ClotheAdapter();
        recyclerView.setAdapter(adapter);

        clotheViewModel= new ViewModelProvider(this).get(ClotheViewModel.class);
        clotheViewModel.getAllClothes().observe(this, new Observer<List<Clothe>>() {
            @Override
            public void onChanged(List<Clothe> clothes) {
                //update RecuclerView
                adapter.setClothe(clothes);
                Toast.makeText(MainActivity.this,"changed",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_CLOTHE_REQUEST&&resultCode==RESULT_OK){
            String color=data.getStringExtra(AddClotheActivity.EXTRA_COLOR);
            String category=data.getStringExtra(AddClotheActivity.EXTRA_CATEGORY);
            String description=data.getStringExtra(AddClotheActivity.EXTRA_DESCRIPTION);
            Boolean isClean = data.getBooleanExtra(AddClotheActivity.EXTRA_ISCLEAN,false);
            byte [] clotheImage= data.getByteArrayExtra(AddClotheActivity.EXTRA_IMAGE);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
         //   Date date = new Date(); //FormatDateTime.fromTimestampToDate(timestamp.toString());
            Clothe clothe = new Clothe(color,category,description,isClean, clotheImage,timestamp);
            clotheViewModel.insertClothe(clothe);
            Toast.makeText(this,"Clothe saved",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this,"Clothe not saved",Toast.LENGTH_SHORT).show();
        }

    }
}
