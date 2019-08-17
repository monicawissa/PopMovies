package com.example.moka.popmovies.Room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class FavoriteRepository {
    private FavoriteDao FavoriteDao;
    private LiveData<List<Favorite>> allFavorite;
    public FavoriteRepository(Application application){
        FavoriteDataBase dataBase=FavoriteDataBase.getInstance(application);
        FavoriteDao=dataBase.FavoriteDao();
        allFavorite=FavoriteDao.getAllFavorite();

    }
    public void insert(Favorite Favorite){
        new insertFavoriteAsyncTask(FavoriteDao).execute(Favorite);
    }
    public void update(Favorite Favorite){
        new updateFavoriteAsyncTask(FavoriteDao).execute(Favorite);
    }

    public void deletetable(){
        new deleteallFavoriteAsyncTask(FavoriteDao).execute();
    }
    public LiveData<Favorite>  getfavoritebyid(int id){
        return FavoriteDao.getFavoriteById(id);
    }
    public void deletebyid(int id){
        new deleteFavoriteAsyncTask(FavoriteDao).execute(id);
    }

    public LiveData<List<Favorite>> getAllFavorite() {
        return allFavorite;
    }
    private static class insertFavoriteAsyncTask extends AsyncTask<Favorite,Void,Void> {
        private FavoriteDao FavoriteDao;

        private insertFavoriteAsyncTask(FavoriteDao FavoriteDao) {
            this.FavoriteDao = FavoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... Favorites) {
            FavoriteDao.insertFavorite(Favorites[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("TAGGG_INSERT" , "Movie Inserted");
        }
    }

    private static class updateFavoriteAsyncTask extends AsyncTask<Favorite,Void,Void> {
        private FavoriteDao FavoriteDao;

        private updateFavoriteAsyncTask(FavoriteDao FavoriteDao) {
            this.FavoriteDao = FavoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... Favorites) {
            FavoriteDao.updateFavorite(Favorites[0]);
            return null;
        }
    }

    private static class deleteFavoriteAsyncTask extends AsyncTask<Integer,Void,Void> {
        private FavoriteDao FavoriteDao;

        private deleteFavoriteAsyncTask(FavoriteDao FavoriteDao) {
            this.FavoriteDao = FavoriteDao;
        }


        @Override
        protected Void doInBackground(Integer... integers) {
            FavoriteDao.deleteFavoritebyId(integers[0]);

            return null;
        }
    }
    private static class deleteallFavoriteAsyncTask extends AsyncTask<Void,Void,Void> {
        private FavoriteDao FavoriteDao;

        private deleteallFavoriteAsyncTask(FavoriteDao FavoriteDao) {
            this.FavoriteDao = FavoriteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FavoriteDao.deletetable();
            return null;
        }
    }
}
