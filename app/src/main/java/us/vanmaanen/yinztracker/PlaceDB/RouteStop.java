package us.vanmaanen.yinztracker.PlaceDB;


import android.util.Log;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import us.vanmaanen.yinztracker.RestAPI.Pred;
import us.vanmaanen.yinztracker.RestAPI.TrueTime;
import us.vanmaanen.yinztracker.RestAPI.TrueTimeApi;


/**
 * Created by david on 2/25/15.
 */
public class RouteStop extends SugarRecord<RouteStop> {
    String rtId;
    String routeLabel;
    String direction;
    int stopId;
    String stopLabel;

    Place place;
    @Ignore
    List<Integer> MinToBus;


    public RouteStop (){}
    public RouteStop( Place place){
        this.place=place;
    }
    public RouteStop(String rtId, String rtLabel, String direction, int stopId, String StopLabel, Place place ){
        this.place=place;
        this.rtId=rtId;
        this.routeLabel=rtLabel;
        this.stopLabel=StopLabel;
        this.stopId=stopId;
        this.direction=direction;
    }
    public void updateTimes(String key, final Runnable onDone){
        TrueTimeApi api=new TrueTime(key).getAPI();
        Log.d("App","Running update RS 10LS");
        api.getPredictions(this.rtId, Integer.toString(this.stopId), new Callback<Map<String, Map<String, ArrayList<Pred>>>>() {
                    @Override
                    public void success(Map<String, Map<String, ArrayList<Pred>>> stringMapMap, Response response) {
                        Log.d("App","update done rt: "+rtId+" stpid: "+stopId);
                        if (stringMapMap.get("bustime-response").get("error")!=null){
                            Log.e("App",stringMapMap.get("bustime-response").get("error").get(0).getMsg());
                            setMinToBus(null);
                        }
                        else {
                            ArrayList<Pred> predResponse = stringMapMap.get("bustime-response").get("prd");
                            List<Integer> nexts = new ArrayList();
                            Log.d("App", "PRDTM: " + predResponse.get(0).getPrdtm());
                            Log.d("App", "PRDCTDN: " + predResponse.get(0).getPrdctdn());
                            for (Pred prd : predResponse) {
                                try {
                                    nexts.add(Integer.parseInt(prd.getPrdctdn()));
                                }
                                catch (NumberFormatException nfe){
                                    nexts.add(0);
                                }
                            }
                            setMinToBus(nexts);
                        }
                        onDone.run();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("App", "Error in retrieving next time.");
                    }
                }

        );
    }

    public void setMinToBus(List<Integer> minToBus){
        this.MinToBus=minToBus;
    }

    //Getter-Setters

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setRouteLabel(String routeLabel) {
        this.routeLabel = routeLabel;
    }

    public void setRtId(String rtId) {
        this.rtId = rtId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getRtId() {
        return rtId;
    }

    public void setStopLabel(String stopLabel) {
        this.stopLabel = stopLabel;
    }

    public int getStopId() {
        return stopId;
    }

    public List<Integer> getMinToBus() {
        return MinToBus;
    }

    public Place getPlace() {
        return place;
    }

    public String getStopLabel() {
        return stopLabel;
    }

    public String getRouteLabel() {
        return routeLabel;
    }
}
