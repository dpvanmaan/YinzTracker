package us.vanmaanen.yinztracker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import us.vanmaanen.yinztracker.PlaceDB.Place;
import us.vanmaanen.yinztracker.PlaceDB.RouteStop;
import us.vanmaanen.yinztracker.RestAPI.BusRoute;
import us.vanmaanen.yinztracker.RestAPI.Dirs;
import us.vanmaanen.yinztracker.RestAPI.Stops;
import us.vanmaanen.yinztracker.RestAPI.TrueTime;
import us.vanmaanen.yinztracker.RestAPI.TrueTimeApi;
import us.vanmaanen.yinztracker.ViewHelpers.AddEditListAdapter;
import us.vanmaanen.yinztracker.ViewHelpers.SpinnerCustomAdapter;


public class AddEditActivity extends ActionBarActivity {
    //Parameters
    List<BusRoute> busList;
    Place place;
    ListView mainListView;
    AddEditListAdapter mainListAdapter;
    TrueTimeApi API;
    Spinner dirSpinner;
    Spinner stpSpinner;
    boolean BusComplete;

    ProgressDialog ringProgressDialog;
    //CallBacks
    Callback<Map< String, Map<String,ArrayList<Dirs>>>> DirsCB = new Callback<Map<String, Map<String,
            ArrayList<Dirs>>>>() {
        @Override
        public void success(Map<String, Map<String, ArrayList<Dirs>>> stringMapMap,
                            Response response) {
            List<Dirs> DirsResp = stringMapMap.get("bustime-response").get("directions");
            Log.e("App",response.getUrl());
            if (DirsResp==null) {
                Log.e("App", "Error in Direction Call");
            }
            else{
                addItems(dirSpinner, DirsResp,
                        "Choose Direction"/*getString(R.string.ChooseDirection)*/);
            }
            ringProgressDialog.dismiss();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("App", "Error in getting bus Directions.");
            Log.e("App", error.getUrl());
            Log.e("App", error.getLocalizedMessage());

        }
    };
    Callback<Map<String, Map<String, ArrayList<Stops>>>> StopsCB= new Callback<Map<String,
            Map<String, ArrayList<Stops>>>>() {
        @Override
        public void success(Map<String, Map<String, ArrayList<Stops>>> stringMapMap, Response response) {
            List<Stops> StopsResp = stringMapMap.get("bustime-response").get("stops");
            if (StopsResp==null){
                Log.e("App","Error in Getting response");
            }
            else {
                addItems(stpSpinner, StopsResp, getString(R.string.ChooseStop));
            }
            ringProgressDialog.dismiss();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("App", "Error in getting Stops.");
            Log.e("App", error.getUrl());
            Log.e("App", error.getLocalizedMessage());
        }
    };
    Callback<Map<String, Map<String, ArrayList<BusRoute>>>> RtsCB=new Callback<Map<String, Map<String,
            ArrayList<BusRoute>>>>() {
        @Override
        public void success(Map<String, Map<String, ArrayList<BusRoute>>> stringMapMap, Response response) {
            List<BusRoute> busListResp = stringMapMap.get("bustime-response").get("routes");
            if (busListResp==null){
                Log.e("App","Error in Bus Route Response.");
            }
            else{
                setBusList(busListResp);
            }

            ringProgressDialog.dismiss();
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("App", "Error in getting bus List.");
            Log.e("App", error.getUrl());
            Log.e("App", error.getLocalizedMessage());
            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
    };
    //Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        this.API=new TrueTime(this.getString(R.string.api_key)).getAPI();
        this.place=new Place();
        this.BusComplete=false;
        this.startWaiting();
        this.API.getRoutes(this.RtsCB);
        this.mainListView= (ListView) this.findViewById(R.id.addEditList);
        this.mainListAdapter=new AddEditListAdapter(this,R.layout.activity_add_edit_row,
                this.place.getRouteStopList());
        mainListView.setAdapter(this.mainListAdapter);
    }


    public void startWaiting(){
        this.ringProgressDialog=ProgressDialog.show(this,"","Loading...");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //Click Methods
    public void onClickAdd(View view){
        startDialog();
    }

    //Other Methods
    public void AddRouteStop(RouteStop RS){
        place.addRouteStop(RS);
        Log.e("App", "List Count: " + this.mainListAdapter.getCount());
        for (int pos=0; pos < this.mainListAdapter.getCount();pos++){
            Log.e("App","List Item: "+this.mainListAdapter.getItem(pos).toString());
        }
        this.mainListAdapter.notifyDataSetChanged();
    }
    public <T> void addItems (Spinner spin, List<T> Items, String itemDefault){
        SpinnerCustomAdapter<T> dataAdapter= new SpinnerCustomAdapter<T>(this, R.layout.spinner_item_gold_text,
                Items);
        dataAdapter.setDefault(itemDefault);
        spin.setAdapter(dataAdapter);
    }
    public void startDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_edit_dialog);
        final Spinner rtSpinner= (Spinner) dialog.findViewById(R.id.routeSpinner);
        this.dirSpinner= (Spinner) dialog.findViewById(R.id.directionSpinner);
        this.stpSpinner= (Spinner) dialog.findViewById(R.id.stopSpinner);
        Button saveButton=(Button) dialog.findViewById(R.id.DialogAdd);
        dialog.show();
        final RouteStop curRouteStop= new RouteStop(this.place);
        addItems(rtSpinner, this.busList, this.getString(R.string.ChooseRoute));
        rtSpinner.post(new Runnable() {
            @Override
            public void run() {
                rtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        startWaiting();
                        BusRoute bsrt= (BusRoute) parent.getItemAtPosition(position);
                        curRouteStop.setRtId(bsrt.getRt());
                        curRouteStop.setRouteLabel(bsrt.getRtnm());
                        API.getDirections(bsrt.getRt(),DirsCB);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
        dirSpinner.post(new Runnable() {
            @Override
            public void run() {
                dirSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        startWaiting();
                        Dirs dirs= (Dirs) parent.getItemAtPosition(position);
                        curRouteStop.setDirection(dirs.getDirection());
                        API.getStops(curRouteStop.getRtId(),
                                curRouteStop.getDirection(),StopsCB);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.e("App","Nothing Selected");
                    }
                });
            }
        });
        stpSpinner.post(new Runnable() {
            @Override
            public void run() {
                stpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Stops stopsSelect= (Stops) parent.getItemAtPosition(position);
                        curRouteStop.setStopId(stopsSelect.getStopId());
                        curRouteStop.setStopLabel(stopsSelect.getStpnm());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.e("App","Nothing Selected");
                    }
                });
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddRouteStop(curRouteStop);
                dialog.dismiss();
        }});
    }

    public void ClickSavePlace(View view){
        EditText mEdit = (EditText) findViewById(R.id.PlaceNameEdit);
        String theStr=mEdit.getText().toString();
        if(theStr==""){
            Toast.makeText(this,"Enter Place Name First", Toast.LENGTH_SHORT);
        }
        else {
            this.place.setName(theStr);
            this.place.saveAll();
            this.setResult(RESULT_OK);
            Toast.makeText(this,"Place: "+theStr+" Saved.", Toast.LENGTH_LONG);
            this.finish();
        }
    }
    //Getter-Setters

    public void setBusList(List<BusRoute> busList) {
        this.busList = busList;
    }
    public List<BusRoute> getBusList(){
        return busList;
    }

    public void setBusComplete(boolean busComplete) {
        BusComplete = busComplete;
    }
}
