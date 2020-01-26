package com.example.myclosetapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ClotheRepository {
    private ClotheDAO clotheDao;
    private LiveData<List<Clothe>> allClothes;

    public ClotheRepository (Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        clotheDao = database.clotheDao();
        allClothes=clotheDao.getAllClothes() ;
    }

    //For any other tasks we need to run them on background because Room does not allow operations on the main thread
    public void insertClothe(Clothe clothe){
        new InsertClotheAsyncTask(clotheDao).execute(clothe);
    }
    public void updateClothe(Clothe clothe){
        new UpdateClotheAsyncTask(clotheDao).execute(clothe);
    }
    public void deleteClothe(Clothe clothe){
        new DeleteClotheAsyncTask(clotheDao).execute(clothe);
    }
    //This is done on the background thread
    public LiveData<List<Clothe>> getAllClothes(){
        return allClothes;
    }

    private static class InsertClotheAsyncTask extends AsyncTask<Clothe,Void,Void>{
        private ClotheDAO clotheDAO;
        private InsertClotheAsyncTask(ClotheDAO clotheDAO){
            this.clotheDAO=clotheDAO;
        }
        @Override
        protected Void doInBackground(Clothe... clothes) {
            clotheDAO.insertClothe(clothes[0]);
            return null;
        }
    }

    private static class UpdateClotheAsyncTask extends AsyncTask<Clothe,Void,Void>{
        private ClotheDAO clotheDAO;
        private UpdateClotheAsyncTask(ClotheDAO clotheDAO){
            this.clotheDAO=clotheDAO;
        }
        @Override
        protected Void doInBackground(Clothe... clothes) {
            clotheDAO.updateClothe(clothes[0]);
            return null;
        }
    }

    private static class DeleteClotheAsyncTask extends AsyncTask<Clothe,Void,Void>{
        private ClotheDAO clotheDAO;
        private DeleteClotheAsyncTask(ClotheDAO clotheDAO){
            this.clotheDAO=clotheDAO;
        }
        @Override
        protected Void doInBackground(Clothe... clothes) {
            clotheDAO.deleteClothe(clothes[0]);
            return null;
        }
    }


}
