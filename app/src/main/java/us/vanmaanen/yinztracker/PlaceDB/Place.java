package us.vanmaanen.yinztracker.PlaceDB;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2/25/15.
 */
public class Place extends SugarRecord<Place> {
    String place_name;
    Integer display_order;
    @Ignore
    int UpdateRunning;
    @Ignore
    List<RouteStop> RouteStopList;
    @Ignore
    Runnable onDone;
    public Place(){
        RouteStopList= new ArrayList<>();
        //FindRouteStops();

    }
    public Place(String name, Integer order){
        this.place_name=name;
        this.display_order=order;
        this.RouteStopList=new ArrayList<>();
    }
    public void addRouteStop(RouteStop RS){
        RS.setPlace(this);
        this.RouteStopList.add(RS);

    }
    public void addRouteStop(String rtId, String rtLabel, String direction, int stopId,
                             String StopLabel){
        RouteStop RS=new RouteStop(rtId, rtLabel,direction, stopId,StopLabel, this);
        this.RouteStopList.add(RS);
    }
    public void saveAll() {
        this.save();
        for (RouteStop RS : this.RouteStopList){
            RS.save();
        }

    }
    public void UpdateDone(){
        this.UpdateRunning-=1;
        if(this.UpdateRunning==0) {
            this.onDone.run();
        }
    }
   /** public void updateTimes(String key){
        Log.d("App","Running Update Times");
        Log.d("App","Size: "+RouteStopList.size());
        for(RouteStop RS: RouteStopList){
            RS.updateTimes(key);
        }
    }**/
    public void updateTimes(String key, Runnable onDone){
        this.onDone=onDone;
        Log.d("App","Running Update Times");
        Log.d("App","Size: "+RouteStopList.size());
        this.UpdateRunning=RouteStopList.size();
        for(RouteStop RS: RouteStopList){
            RS.updateTimes(key,new Runnable() {
                @Override
                public void run() {
                    UpdateDone();
                }
            });
        }
    }

    // Getter-Setters
    public List<RouteStop> FindRouteStops(){
        Log.d("App","Find routes");
        List<RouteStop> out=RouteStop.find(RouteStop.class, "place = ?", new String[] {this.getId().toString()});
        Log.d("App","Size of find: "+out.size());
        if (out!=null) {
            this.RouteStopList = out;
        }
        return this.RouteStopList;
    }

    public List<RouteStop> getRouteStopList() {
        return RouteStopList;
    }

    public void setName(String name) {
        this.place_name = name;
    }

    public void setDisplay_order(Integer order) {
        this.display_order = order;
    }

    public String getPlaceName() {
        return place_name;
    }

    public Integer getDisplay_order() {
        return this.display_order;
    }
}
