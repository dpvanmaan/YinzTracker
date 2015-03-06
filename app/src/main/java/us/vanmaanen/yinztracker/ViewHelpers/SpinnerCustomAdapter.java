package us.vanmaanen.yinztracker.ViewHelpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import us.vanmaanen.yinztracker.R;

/**
 * Created by david on 2/26/15.
 */
public class SpinnerCustomAdapter <T> extends ArrayAdapter<T> {
    private Activity context;
    List<T> data= null;
    boolean NothingSelected=true;
    String Default;
    int resource;
    public SpinnerCustomAdapter (Activity context, int resource, List<T> data){
        super(context, resource, data);
        this.context=context;
        this.data=data;
        this.resource=resource;
    }
    public void setDefault(String Default){
        this.Default=Default;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ( NothingSelected) {
            if (convertView ==null){
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(this.resource, parent, false);
            }
            TextView txtcontent= (TextView) convertView.findViewById(R.id.text1);
            txtcontent.setText(this.Default);
            return convertView;
        }
        else {
            return super.getView(position, convertView, parent);
        }

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        NothingSelected=false;
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);
        }

        T item = data.get(position);

        if(item != null)
        {   // Parse the data from each object and set it.
            TextView itemText = (TextView) row.findViewById(R.id.SpinnerItemName);

            if(itemText != null)
                itemText.setText(item.toString());

        }

        return row;
    }
}
