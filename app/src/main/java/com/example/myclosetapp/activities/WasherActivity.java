package com.example.myclosetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.ui.ClotheAdapter;
import com.example.myclosetapp.ui.ClotheViewModel;
import com.example.myclosetapp.ui.WasherAdapter;

import java.util.ArrayList;
import java.util.List;

public class WasherActivity extends AppCompatActivity {

    private ClotheViewModel clotheViewModel;
    private List<Clothe> washer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_washer);
        getSupportActionBar();
        setTitle("Πλυντήριο");


        final RecyclerView recyclerView = findViewById(R.id.recycler_view_washer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        washer=new ArrayList<>();
        final WasherAdapter adapter = new WasherAdapter();
        recyclerView.setAdapter(adapter);

        clotheViewModel= new ViewModelProvider(this).get(ClotheViewModel.class);

        clotheViewModel.getClothesDirty().observe(this, new Observer<List<Clothe>>() {
            @Override
            public void onChanged(List<Clothe> clothes) {
                //update RecuclerView
                adapter.setClothe(clothes);
                if(adapter.getItemCount()==0){
                    Toast.makeText(WasherActivity.this,"Δεν υπάρχουν βρώμικα ρούχα για πλυντήριο",Toast.LENGTH_SHORT).show();
                }

            }
        });

        adapter.setOnWasherItemClickListener(new WasherAdapter.OnWasherClotheClickListener() {
            @Override
            public void onWasherClotheClickAddToWasher(Clothe clothe) {
                if(washer.contains(clothe)){
                    Toast.makeText(WasherActivity.this,"Το ρούχο είναι ήδη στον κάδο",Toast.LENGTH_LONG).show();
                }
                else {
                    washer.add(clothe);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.washer_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.startWaher:
                if(washer.isEmpty()){
                    Toast.makeText(WasherActivity.this,"Δεν υπάρχουν ρούχα στον κάδο για πλύσιμο",Toast.LENGTH_SHORT).show();
                    return true;
                }
                else {
                    updateClotheToWashState();
                    Toast.makeText(WasherActivity.this,"Τα ρούχα βρίσκονται στο πλυντήριο",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.resetWasher:
                if(washer.isEmpty()){
                    Toast.makeText(WasherActivity.this,"Ο κάδος είναι ήδη άδειος",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(WasherActivity.this, "Ο κάδος πλυντηρίου άδειασε", Toast.LENGTH_SHORT).show();
                    washer.clear();
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
    public void updateClotheToWashState(){
        for (Clothe clotheToWash:washer){
            clotheToWash.setToClean(false);
            clotheViewModel.updateClothe(clotheToWash);
        }
    }
}
