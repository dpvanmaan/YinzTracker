package us.vanmaanen.yinztracker.ViewHelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import us.vanmaanen.yinztracker.AddEditActivity;
import us.vanmaanen.yinztracker.PlaceDB.RouteStop;
import us.vanmaanen.yinztracker.R;

/**
 * Created by david on 3/1/15.
 */

public class TimeTableAdapter extends ArrayAdapter<RouteStop> {
    Context context;
    int ResID;

    List<RouteStop> data;

    public TimeTableAdapter(Context context, int resource, List<RouteStop> objects) {
        super(context, resource, objects);
        this.context=context;
        this.ResID=resource;
        this.data=objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView==null) {
            LayoutInflater inflater =LayoutInflater.from(context);
            convertView = inflater.inflate(this.ResID, parent, false);
        }
        TextView RtTv=(TextView) convertView.findViewById(R.id.route);
        TextView DirTv=(TextView) convertView.findViewById(R.id.direction);
        TextView StpTv=(TextView) convertView.findViewById(R.id.stop);
        TextView Time=(TextView) convertView.findViewById(R.id.TimetoBus);
        RouteStop curItem=this.data.get(position);
        RtTv.setText(curItem.getRtId()+" : "+curItem.getRouteLabel());
        DirTv.setText(curItem.getDirection());
        StpTv.setText(curItem.getStopLabel());
        List<Integer> next=curItem.getMinToBus();
        if (next==null){
            Time.setText("NA");
        }
        else{
            Time.setText(""+next.get(0));
        }

        return convertView;
    }

    public void updateTimes(String key,final Runnable onDone){
        final int UpdateRunning=data.size();
        for (RouteStop RS: data){
            RS.updateTimes(key,new Runnable() {
                int curDone=0;
                @Override
                public void run() {
                   curDone+=1;
                    if(this.curDone==UpdateRunning){
                        onDone.run();
                    }
                }
            });
        }
    }


}
