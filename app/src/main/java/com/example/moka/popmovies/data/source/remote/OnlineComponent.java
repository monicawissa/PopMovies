//package com.example.moka.popmovies.data.source.remote;
//
//import android.support.v4.app.Fragment;
//
//import com.example.moka.popmovies.data.Models.Cast;
//import com.example.moka.popmovies.data.Models.Movie;
//import com.example.moka.popmovies.data.Models.Review;
//import com.example.moka.popmovies.data.Models.Trailer;
//
//import java.util.List;
//interface IonlineResponse {
//    void onDataError();
//    void onDataFetched(Object data);
//
//
//}
//abstract public class OnlineComponent extends Fragment implements IResponse,IResponseCast,IResponseTrailer,IResponseReview{
//    private String option="" ;
//    private int movie_id=-1;
//
//
//
//    private IonlineResponse iOnlineResponse;
//    public OnlineComponent() {
//        //this.execute();
//    }
//    public void setOption(String option) {
//        this.option = option;
//    }
//
//    public void setMovie_id(int movie_id) {
//        this.movie_id = movie_id;
//    }
//
//
//
//    abstract public String getPath();
//    abstract public void getAttribute();
////    abstract public void onDataFetched(Object data);
////
////    abstract public void onDataError();
//
//    public void execute(){
//        final Client[] client = new Client[1];
//        Thread t=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                client[0] =  Client.getInstance(getPath());
//                getAttribute();
//                if(movie_id==-1){
//                    client[0].setResponse( OnlineComponent.this );
//                    client[0].Perform(option);
//                }
//                else{
//                    switch(option) {
//
//                        case "Review":
//                            client[0].setResponseReview( OnlineComponent.this );
//                            break;
//                        case "Trailer":
//                            client[0].setResponseTrailer( OnlineComponent.this );
//                            break;
//                        case "Actor":
//                            client[0].setResponseCast( OnlineComponent.this );
//                            break;
//                        default:
//                            // code block
//                    }
//                    client[0].Perform(option,movie_id);
//                }
//            }
//        });
//        t.start();
//    }
//    public void setonlineResponse(IonlineResponse response){this.iOnlineResponse=response;}
//    @Override
//    public void OnRespponse(List<Movie> data) {
//
//        if(data==null){
//            iOnlineResponse.onDataError();
//        }
//        else iOnlineResponse.onDataFetched(data);
//    }
//    @Override
//    public void OnRespponseTrailer(List<Trailer> data) {
//
//        if(data==null){
//            iOnlineResponse.onDataError();
//        }
//        else iOnlineResponse.onDataFetched(data);
//    }
//
//    @Override
//    public void OnRespponseCast(List<Cast> data) {
//
//        if(data==null){
//            iOnlineResponse.onDataError();
//        }
//        else iOnlineResponse.onDataFetched(data);
//    }
//
//    @Override
//    public void OnRespponseReview(List<Review> data) {
//
//        if(data==null){
//            iOnlineResponse.onDataError();
//        }
//        else iOnlineResponse.onDataFetched(data);
//    }
//
//}
