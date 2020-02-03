package com.example.myclosetapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.ui.ClotheAdapter;
import com.example.myclosetapp.ui.ClotheViewModel;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateOrderActivity extends AppCompatActivity {

    private ClotheViewModel clotheViewModel;
    private String style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_order);

        TabLayout tabs = findViewById(R.id.tabsDays);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_clothe_date_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ClotheAdapter adapter = new ClotheAdapter();
        recyclerView.setAdapter(adapter);

        clotheViewModel = new ViewModelProvider(this).get(ClotheViewModel.class);
        clotheViewModel.getClotheDateASC().observe(this, new Observer<List<Clothe>>() {
            @Override
            public void onChanged(List<Clothe> clothes) {
                //update RecuclerView
                adapter.setClothe(clothes);
                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
            }
        });


        //todo come here to add tabs logic also move entire code to another activity
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()){
                    case "Φθίνουσα":
                        clotheViewModel.getClotheDateASC().observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                    case "Μέρες>3":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(3)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Μέρες>5":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(5)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Μέρες>7":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(7)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Μέρες>10":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(10)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Μέρες>14":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(14)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Μέρες>20":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(20)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case "Μέρες>30":
                        clotheViewModel.getClothesSpecificDate(getDateMinusDays(30)).observe(DateOrderActivity.this, new Observer<List<Clothe>>() {
                            @Override
                            public void onChanged(List<Clothe> clothes) {
                                //update RecuclerView
                                adapter.setClothe(clothes);
                                Toast.makeText(DateOrderActivity.this, "changed", Toast.LENGTH_LONG).show();
                            }
                        });
                        break;

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

    //TODO COME HERE AMAZING LOGIC <3
    public String getDateMinusDays(int minusDays) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String currDateString = sdf.format(date);
        String dateToReturn = currDateString;
        String[] dateArray = currDateString.split("-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        if (day - minusDays > 0) {
            if (day - minusDays > 9) {
                day = day - minusDays;
                if (month > 9) {
                    dateToReturn = year + "-" + month + "-" + day;
                } else {
                    dateToReturn = year + "-0" + month + "-" + day;
                }
            } else {
                day = day - minusDays;
                if (month > 9) {
                    dateToReturn = year + "-" + month + "-0" + day;
                } else {
                    dateToReturn = year + "-0" + month + "-0" + day;
                }
            }
        } else {
            month = month - 1;

            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                day = day + 31 - minusDays;
            } else if (month == 2) {
                day = day + 28 - minusDays;
            } else {
                day = day + 30 - minusDays;
            }

            if (day  > 9) {
                if (month > 9) {
                    dateToReturn = year + "-" + month + "-" + day;
                } else {
                    dateToReturn = +year + "-0" + month + "-" + day;
                }
            } else {
                if (month > 9) {
                    dateToReturn = year + "-" + month + "-0" + day;
                } else {
                    dateToReturn = year + "-0" + month + "-0" + day;
                }
            }
        }

        return dateToReturn;
    }
}
