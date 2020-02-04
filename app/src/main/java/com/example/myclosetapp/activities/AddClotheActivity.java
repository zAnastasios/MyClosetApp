package com.example.myclosetapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myclosetapp.R;
import com.example.myclosetapp.utils.ImageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddClotheActivity extends AppCompatActivity {

    public static final String EXTRA_ID=
            "com.example.myclosetapp.activities.EXTRA_ID";
    public static final String EXTRA_IMAGE=
            "com.example.myclosetapp.activities.EXTRA_IMAGE";
    public static final String EXTRA_COLOR=
            "com.example.myclosetapp.activities.EXTRA_COLOR";
    public static final String EXTRA_CATEGORY=
            "com.example.myclosetapp.activities.EXTRA_CATEGORY";
    public static final String EXTRA_DESCRIPTION=
            "com.example.myclosetapp.activities.EXTRA_DESCRIPTION";
    public static final String EXTRA_ISCLEAN=
            "com.example.myclosetapp.activities.EXTRA_ISCLEAN";

    public static final int REQUEST_IMAGE_CAMERA=1,REQUEST_IMAGE_GALLERY=2;

    private Spinner spinnerColor;
    private Spinner spinnerCategory;
    private EditText editTextDescription;
    private CheckBox checkBoxClean;
    private String colorSelected;
    private String categorySelected;
    private ImageView clotheImage;
    private Button cameraButton;
    private Button galleryButton;
    private Bitmap bitmap;


    //TODO ADD MORE COLORS HERE AND CATEGORIES
    private static final String[] colors = {"Κόκκινο", "Πράσινο", "Μπλε"};
    private static final String[] categories ={"Μπλόυζα","Φούστα","Μπουφάν"};

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothe);

        editTextDescription = findViewById(R.id.edit_text_description);
        checkBoxClean = findViewById(R.id.checkboxIsClean);
        clotheImage=findViewById(R.id.image_view_clothe);
        cameraButton=findViewById(R.id.button_camera);
        galleryButton=findViewById(R.id.button_gallery);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_clothe);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Επεξεργασία ρούχου");
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            if(intent.getBooleanExtra(EXTRA_ISCLEAN,false)){
                checkBoxClean.setChecked(true);
            }
            else{
                checkBoxClean.setChecked(false);
            }
            clotheImage.setImageBitmap(ImageConverter.convertByteArrayToImage(intent.getByteArrayExtra(EXTRA_IMAGE)));
        }
        else {
            setTitle("Προσθήκη Ρούχου");
        }


        spinnerColor =  findViewById(R.id.spinner_color);

        ArrayAdapter<String> adapterColor = new ArrayAdapter<String>(AddClotheActivity.this,
                android.R.layout.simple_spinner_item, colors);

        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapterColor);
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                colorSelected=(String) parent.getItemAtPosition(position);
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCategory=findViewById(R.id.spinner_category);

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(AddClotheActivity.this,
                android.R.layout.simple_spinner_item, categories);

        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                categorySelected=(String) parent.getItemAtPosition(position);
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });











        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(AddClotheActivity.this,new String[]{Manifest.permission.CAMERA},1);
                    }
                }

                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(cameraIntent,REQUEST_IMAGE_CAMERA);
                }
                else{
                    System.out.println("SOMETHING WRONG");
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(AddClotheActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
                    }
                }

                Intent galerryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galerryIntent.setType("image/*");
                startActivityForResult(galerryIntent,REQUEST_IMAGE_GALLERY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_IMAGE_CAMERA){
               bitmap = (Bitmap) data.getExtras().get("data");
               clotheImage.setImageBitmap(bitmap);
            }else if(requestCode==REQUEST_IMAGE_GALLERY){
                //todo check why here fucks up COME BACK AFTER TESTING
                Uri uri = (Uri) data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    clotheImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void saveClothe()
    {
        String color=colorSelected;
        String category=categorySelected;
        String description=editTextDescription.getText().toString();
        Boolean isCleanState=checkBoxClean.isChecked();

//        ByteArrayOutputStream bs = new ByteArrayOutputStream();
//        assert clothePic != null;
//        clothePic.compress(Bitmap.CompressFormat.PNG,50,bs);


        if(color.trim().isEmpty()||category.trim().isEmpty()||description.trim().isEmpty()||bitmap==null){
            Toast.makeText(this,"LATHOS",Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COLOR,color);
        data.putExtra(EXTRA_CATEGORY,category);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_ISCLEAN,isCleanState);
        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if(id!=-1){
            data.putExtra(EXTRA_ID,id);
        }
        //todo Or maybe here fucks up
        byte [] byte_array= ImageConverter.convertImageToByteArray(bitmap);
        data.putExtra(EXTRA_IMAGE, byte_array);
        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_clothe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_clothe:
                saveClothe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
