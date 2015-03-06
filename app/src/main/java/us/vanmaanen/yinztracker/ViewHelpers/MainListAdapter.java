package us.vanmaanen.yinztracker.ViewHelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import us.vanmaanen.yinztracker.PlaceDB.Place;
import us.vanmaanen.yinztracker.R;

/**
 * Created by david on 3/1/15.
 */
public class MainListAdapter extends ArrayAdapter<Place> {
    List<Place> data;
    Context context;
    int ResId;

    public MainListAdapter(Context context, int resource, List<Place> objects) {
        super(context, resource, objects);
        this.data=objects;
        this.context=context;
        this.ResId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView==null){
            LayoutInflater layoutInflater=LayoutInflater.from(context);
            convertView= layoutInflater.inflate(this.ResId, parent, false);
        }
        TextView Tv= (TextView) convertView.findViewById(R.id.RowText);
        Tv.setText(this.data.get(position).getPlaceName());
        return convertView;
    }
}
