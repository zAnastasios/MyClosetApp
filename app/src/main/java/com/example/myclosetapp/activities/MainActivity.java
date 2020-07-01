package com.example.myclosetapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myclosetapp.R;
import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.login.LoginActivity;
import com.example.myclosetapp.ui.ClotheAdapter;
import com.example.myclosetapp.ui.ClotheViewModel;
import com.example.myclosetapp.utils.CircleTransformation;
import com.example.myclosetapp.utils.FormatDateTime;
import com.facebook.AccessToken;
import com.facebook.FacebookGraphResponseException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    public static final int ADD_CLOTHE_REQUEST=200;
    public static final int EDIT_CLOTHE_REQUEST=201;

    private Date dateOfUpdatedClothe;
    private ImageButton deleteClothe;
    private NavigationView mainNavView;
    private DrawerLayout mainDrawer;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Boolean isLoggedIn;

    private ClotheViewModel clotheViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteClothe=findViewById(R.id.button_delete_clothe);
        mainNavView=findViewById(R.id.main_user_navigation_view);
        mainDrawer=findViewById(R.id.drawer_main);
//        getSupportActionBar();
//        setTitle("Η ντουλάπα μου");
        toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("Η ντουλάπα μου");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mainDrawer,toolbar,R.string.nav_drawer_openGR,R.string.nav_drawer_closeGR);
        mainDrawer.addDrawerListener(toggle);
        toggle.syncState();





        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
         isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
        View navViewHeaderView = mainNavView.getHeaderView(0);
            final TextView userUserName = navViewHeaderView.findViewById(R.id.navigation_user_header_name);
            ImageView userImage = navViewHeaderView.findViewById(R.id.navigation_user_header_image);
            String facebookUserID = accessToken.getUserId();
            String facebookUrlString =  "https://graph.facebook.com/"+facebookUserID+"/picture?type=large";
            Picasso.get()
                    .load(facebookUrlString)
                    .transform(new CircleTransformation())
                    .into(userImage);
//            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                @Override
//                public void onCompleted(JSONObject object, GraphResponse response) {
//                        Log.v("LoginActivity Response ", response.toString());
//                        try {
//                        userUserName.setText(object.getString("name"));
//                        }
//                        catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                }
//        });
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
                if(adapter.getItemCount()==0)
                Toast.makeText(MainActivity.this,"Δεν υπάρχουν ρούχα στην ντουλάπα σας.Προσθέστε ρούχα για να εμφανιστούν στο αρχικό Menu",Toast.LENGTH_SHORT).show();
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

                switch (menuItem.getItemId()) {
                    case R.id.orderByColor:
                        Intent intent = new Intent(MainActivity.this, OrderByColorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.randomStyle:
                        Intent intent1 = new Intent(MainActivity.this, ClotheMatchingActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.dateOrder:
                        Intent intent2 = new Intent(MainActivity.this, DateOrderActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.washer:
                        Intent intent3 = new Intent(MainActivity.this, WasherActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.help:
                        Intent intent4 = new Intent(MainActivity.this, InstructionsActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.exit:
                        if (isLoggedIn){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Αποσύνδεση")
                                    .setMessage("Θέλετε να αποσυνδεθείτε απο την εφαρμογή;")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            LoginManager.getInstance().logOut();
                                            Intent intent5 = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent5);
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }
                        else {
                            Intent intent5 = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent5);
                        }
                        break;
                }
                return false;
            }
        });



        adapter.setOnItemClickListener(new ClotheAdapter.OnClotheClickListener() {
            @Override
            public void onClotheClickDelete(final Clothe clothe) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Διαγραφή Ρούχου")
                        .setMessage("Είστε σίγουροι ότι θέλετε θα διαγράψετε αυτό το ρούχο;")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(R.string.yesGR, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                clotheViewModel.deleteClothe(clothe);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(R.string.noGR, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            //TODO IMPLEMENT EDIT LOGIC HERE
            @Override
            public void onClotheClickUpdate(Clothe clothe) {
                Intent intent = new Intent(MainActivity.this,AddClotheActivity.class);
                intent.putExtra(AddClotheActivity.EXTRA_ID,clothe.getClohteid());
                intent.putExtra(AddClotheActivity.EXTRA_COLOR,clothe.getColor());
                intent.putExtra(AddClotheActivity.EXTRA_CATEGORY,clothe.getCategory());
                intent.putExtra(AddClotheActivity.EXTRA_DESCRIPTION,clothe.getDescription());
                intent.putExtra(AddClotheActivity.EXTRA_ISCLEAN,clothe.getToClean());
                intent.putExtra(AddClotheActivity.EXTRA_IMAGE,clothe.getImage());
                dateOfUpdatedClothe=clothe.getDateLastWorn();
                startActivityForResult(intent,EDIT_CLOTHE_REQUEST);

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
            Toast.makeText(this,"Το ρούχο αποθηκεύτηκε επιτυχώς",Toast.LENGTH_SHORT).show();

        }else  if(requestCode==EDIT_CLOTHE_REQUEST&&resultCode==RESULT_OK){
            int id = data.getIntExtra(AddClotheActivity.EXTRA_ID,-1);
            if(id==-1){
                Toast.makeText(this,"Λάθος",Toast.LENGTH_SHORT).show();
                return;
            }

            String color=data.getStringExtra(AddClotheActivity.EXTRA_COLOR);
            String category=data.getStringExtra(AddClotheActivity.EXTRA_CATEGORY);
            String description=data.getStringExtra(AddClotheActivity.EXTRA_DESCRIPTION);
            Boolean isClean = data.getBooleanExtra(AddClotheActivity.EXTRA_ISCLEAN,false);
            byte [] clotheImage= data.getByteArrayExtra(AddClotheActivity.EXTRA_IMAGE);
            Timestamp timestamp = new Timestamp(dateOfUpdatedClothe.getTime());
            Clothe clothe = new Clothe(color,category,description,isClean, clotheImage,timestamp);
            clothe.setClohteid(id);
            clotheViewModel.updateClothe(clothe);
            Toast.makeText(this,"Το ρούχο ενημερώθηκε",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"Το ρούχο δεν αποθηκεύτηκε",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
            if (isLoggedIn){
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Αποσύνδεση")
                        .setMessage("Θέλετε να αποσυνδεθείτε απο την εφαρμογή;")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                Intent intent5 = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent5);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
        }
    }
}
