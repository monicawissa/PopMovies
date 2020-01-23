package com.example.moka.popmovies.api;

import android.support.v4.app.Fragment;

abstract public class OnlineComponent extends Fragment implements IResponse{
    private String option="" ;
    private int movie_id=-1;
    private IonlineResponse iOnlineResponse;
    public OnlineComponent() {
        //this.execute();
    }
    public void setOption(String option) {
        this.option = option;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }



    abstract public String getPath();
    abstract public void getAttribute();
    abstract public void onDataFetched(Object data);
    abstract public void onDataError();

    public void execute(){
        final Client[] client = new Client[1];
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                client[0] =  Client.getInstance(getPath());
                getAttribute();
                client[0].setResponse( OnlineComponent.this );
                if(movie_id==-1){
                    client[0].Perform(option);
                }
                else{
                    client[0].Perform(option,movie_id);
                }
            }
        });
        t.start();
    }
    public void setonlineResponse(IonlineResponse response){
        this.iOnlineResponse=response;
    }
    @Override
    public void OnRespponse(Object data) {
        //if(iOnlineResponse!=null){

        if(data==null){
            onDataError();
        }
        else onDataFetched(data);
        //}
    }

}
