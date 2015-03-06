package us.vanmaanen.yinztracker.RestAPI;

import android.content.res.Resources;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import us.vanmaanen.yinztracker.R;

/**
 * Created by david on 2/25/15.
 */
public class TrueTime {
    private String api_key;
    private TrueTimeApi API_CLIENT;
    private static String ROOT= "http://realtime.portauthority.org/bustime/api/v2/";

    public TrueTime(){
        this.api_key=Resources.getSystem().getString(R.string.api_key);
        setupRestClient();
    }
    public TrueTime(String api_key){
        this.api_key=api_key;
        setupRestClient();
    }
    public TrueTimeApi getAPI(){
        return API_CLIENT;
    }
    private void setupRestClient(){
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new RequestInterceptor(){
                    @Override
                    public void intercept(RequestFacade request){
                        request.addQueryParam("key",api_key);
                    }
                });
        RestAdapter restAdapt = builder.build();
        API_CLIENT= restAdapt.create(TrueTimeApi.class);
    }
}
