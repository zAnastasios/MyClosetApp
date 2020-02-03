package com.example.myclosetapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myclosetapp.data.Clothe;
import com.example.myclosetapp.data.ClotheRepository;

import java.util.List;

public class ClotheViewModel extends AndroidViewModel {

    private ClotheRepository clotheRepository;
    private LiveData<List<Clothe>> allClothes;

    public ClotheViewModel(@NonNull Application application) {
        super(application);
        clotheRepository= new ClotheRepository(application);
        allClothes = clotheRepository.getAllClothes();
    }

    public void insertClothe(Clothe clothe){
        clotheRepository.insertClothe(clothe);
    }

    public void updateClothe (Clothe clothe){
        clotheRepository.updateClothe(clothe);
    }

    public void deleteClothe(Clothe clothe){
        clotheRepository.deleteClothe(clothe);
    }

    public LiveData<List<Clothe>> getAllClothes() {
        return allClothes;
    }

    public LiveData<List<Clothe>> getClothesByColor(String color){return clotheRepository.getClothesByColorOrder(color);}

    public LiveData<List<Clothe>>  getClotheDateASC (){return  clotheRepository.getClothesDateOrder();}

    public LiveData<List<Clothe>>  getClothesSpecificDate(String date){return clotheRepository.getAllClotheSpecifyDate(date);}

    public LiveData<List<Clothe>> getClothesDirty(){return clotheRepository.getallDirtyClothes();}







}
