package us.vanmaanen.yinztracker.ViewHelpers;

import android.app.Activity;
import android.app.Application;
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
public class AddEditListAdapter extends ArrayAdapter<RouteStop> {
    private Activity context;
    private final List<RouteStop> data;
    private final int ResId;
    public AddEditListAdapter(Activity context, int resource, List<RouteStop> data) {
        super(context, resource, data);
        this.data=data;
        this.context=context;
        this.ResId=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView==null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(this.ResId, parent, false);
        }
        TextView RtTv=(TextView) convertView.findViewById(R.id.route);
        TextView DirTv=(TextView) convertView.findViewById(R.id.direction);
        TextView StpTv=(TextView) convertView.findViewById(R.id.stop);
        Button button=(Button) convertView.findViewById(R.id.addEditRemove);
        RouteStop curItem=this.data.get(position);
        RtTv.setText(curItem.getRtId()+" : "+curItem.getRouteLabel());
        DirTv.setText(curItem.getDirection());
        StpTv.setText(curItem.getStopLabel());
        AddEditActivity myact=(AddEditActivity) context;
        final int curpos=position;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(curpos);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
