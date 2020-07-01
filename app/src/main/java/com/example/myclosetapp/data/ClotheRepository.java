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

    public LiveData<List<Clothe>> getClothesByColorOrder(String color) { return clotheDao.getClothesByColor(color);}

    public LiveData<List<Clothe>> getClothesDateOrder (){return clotheDao.getClothesOrderASC();}

    public LiveData<List<Clothe>> getAllClotheSpecifyDate(String date){return clotheDao.getAllClothesByDate(date);}

    public LiveData<List<Clothe>> getallDirtyClothes(){return  clotheDao.getAllDirtyClothes();}



    //   public List<Clothe> getClotheMatching(String style) {return clotheDao.get}
//    private static class GetClothesByColorAsync extends AsyncTask<String,Void,Void>{
//        private ClotheDAO clotheDAO;
//        private GetClothesByColorAsync(ClotheDAO clotheDAO){
//            this.clotheDAO=clotheDAO;
//        }
//        @Override
//        protected LiveData<List<Clothe>> doInBackground(String... strings) {
//            return clotheDAO.getClothesByColor(strings[0]);
//
//        }
//    }
//    private static class GetClotheDateAsyncTask extends AsyncTask<Clothe,Void,Void>{
//        private ClotheDAO clotheDAO;
//        private GetClotheDateAsyncTask(ClotheDAO clotheDAO){
//            this.clotheDAO=clotheDAO;
//        }
//
//    @Override
//    protected Void doInBackground(Clothe... clothes) {
//        clotheDAO.getAllClothesByDate()
//    }
//
//}






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
