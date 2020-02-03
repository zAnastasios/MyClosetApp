package com.example.myclosetapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.ui.ClotheAdapter;
import com.example.myclosetapp.ui.ClotheViewModel;
import com.facebook.AccessToken;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.sql.Timestamp;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    public static final int ADD_CLOTHE_REQUEST=200;
    private ImageButton deleteClothe;
    private NavigationView mainNavView;

    private ClotheViewModel clotheViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteClothe=findViewById(R.id.button_delete_clothe);
        mainNavView=findViewById(R.id.main_user_navigation_view);

        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){

        }

        FloatingActionButton buttonAddClothe = findViewById(R.id.button_add_clothe);
        buttonAddClothe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddClotheActivity.class);
                startActivityForResult(intent,ADD_CLOTHE_REQUEST);
            }
        });
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_clothe);
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




    //TODO FULLY WORKING DELETE CODE BEFORE WITH MINOR BACK. CHECK BACK IF FUCK UP HAPPENS

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Delete Clothe")
//                        .setMessage("Are you sure you want to delete this clothe?")
//
//                        // Specifying a listener allows you to take an action before dismissing the dialog.
//                        // The dialog is automatically dismissed when a dialog button is clicked.
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                clotheViewModel.deleteClothe(adapter.getClotheAt(viewHolder.getAdapterPosition()));
//
//                            }
//                        })
//
//                        // A null listener allows the button to dismiss the dialog and take no further action.
//                        .setNegativeButton(android.R.string.no, null)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
//            }
//        }).attachToRecyclerView(recyclerView);


        mainNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.orderByColor:
                        Intent intent = new Intent(MainActivity.this,OrderByColorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.randomStyle:
                       Intent  intent1 = new Intent(MainActivity.this,ClotheMatchingActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.dateOrder:
                        Intent intent2 = new Intent(MainActivity.this,DateOrderActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.washer:
                        Intent intent3 = new Intent(MainActivity.this,WasherActivity.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });



        adapter.setOnItemClickListener(new ClotheAdapter.OnClotheClickListener() {
            @Override
            public void onClotheClickDelete(final Clothe clothe) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Clothe")
                        .setMessage("Are you sure you want to delete this clothe?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                clotheViewModel.deleteClothe(clothe);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            //TODO IMPLEMENT EDIT LOGIC HERE
            @Override
            public void onClotheClickUpdate(Clothe clothe) {

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
