package com.example.moka.popmovies.Room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepository repository;
    private LiveData<List<Favorite>> allFavorites;
    private LiveData<Favorite>FavoriteById;
    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        repository = new FavoriteRepository(application);
        allFavorites = repository.getAllFavorite();

    }

    public void insert(Favorite Favorite) {
        repository.insert(Favorite);
    }
    public void update(Favorite Favorite){
        repository.update(Favorite);
    }
    public void delete(int id){repository.deletebyid(id);}
    public void deleteall(){
        repository.deletetable();
    }

    public LiveData<Favorite> getFavoriteById(int idd){
        return repository.getfavoritebyid(idd);}


    public LiveData<List<Favorite>> getAllFavorites(){
        return allFavorites;
    }

}
