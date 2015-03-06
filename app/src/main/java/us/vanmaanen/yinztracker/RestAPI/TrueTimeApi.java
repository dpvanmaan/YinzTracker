package us.vanmaanen.yinztracker.RestAPI;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by david on 2/25/15.
 */
public interface TrueTimeApi {
    @GET("/getroutes?format=json")
    void getRoutes(Callback<Map< String, Map< String, ArrayList<BusRoute>>>> callback);

    @GET("/getdirections?format=json")
    void getDirections( @Query("rt") String rt, Callback<Map< String, Map<String,ArrayList<Dirs>>>> callback);

    @GET("/getstops?format=json")
    void getStops( @Query("rt") String rt,
                   @Query("dir") String Dir,Callback<Map<String, Map<String, ArrayList<Stops>>>> callback);

    @GET("/getpredictions?format=json")
    void getPredictions( @Query("rt") String route, @Query("stpid") String StopId,
                         Callback<Map<String, Map<String, ArrayList<Pred>>>> callback);
}
