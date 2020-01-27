package com.example.myclosetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.ui.ClotheAdapter;
import com.example.myclosetapp.ui.ClotheViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class OrderByColorActivity extends AppCompatActivity {

    private ClotheViewModel clotheViewModel;
    private String colorToOrderBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_color);


        TabLayout tabs =  findViewById(R.id.tabs);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_clothe_color_ordering_tab);
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
                Toast.makeText(OrderByColorActivity.this,"changed",Toast.LENGTH_LONG).show();
            }
        });


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                colorToOrderBy = tab.getText().toString();
                if(colorToOrderBy.equals("ΟΛΑ")){
                    clotheViewModel.getAllClothes().observe(OrderByColorActivity.this, new Observer<List<Clothe>>() {
                        @Override
                        public void onChanged(List<Clothe> clothes) {
                            //update RecuclerView
                            adapter.setClothe(clothes);
                            Toast.makeText(OrderByColorActivity.this,"changed",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    clotheViewModel.getClothesByColor(colorToOrderBy).observe(OrderByColorActivity.this, new Observer<List<Clothe>>() {
                        @Override
                        public void onChanged(List<Clothe> clothes) {
                            //update RecuclerView
                            adapter.setClothe(clothes);
                            Toast.makeText(OrderByColorActivity.this, "Displaying clothes with color " + colorToOrderBy, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
