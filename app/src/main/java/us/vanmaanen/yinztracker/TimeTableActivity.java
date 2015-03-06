package us.vanmaanen.yinztracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.ArrayAdapter;
import android.widget.ListView;

import us.vanmaanen.yinztracker.PlaceDB.Place;
import us.vanmaanen.yinztracker.R;
import us.vanmaanen.yinztracker.ViewHelpers.TimeTableAdapter;

public class TimeTableActivity extends ActionBarActivity {
    ListView timetable;
    TimeTableAdapter ttAdapter;
    Place place;
    boolean done=false;
    ProgressDialog progressDialog;
    boolean initDone=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Intent intent=getIntent();
        long id=intent.getLongExtra("Place_ID",-1);
        Log.d("App","Place ID: "+id);
        timetable=(ListView) this.findViewById(R.id.timeTableList);
        place=Place.findById(Place.class, id);
        place.FindRouteStops();
        progressDialog=ProgressDialog.show(this,"","Loading...");
        place.updateTimes(this.getString(R.string.api_key), new Runnable() {
            @Override
            public void run() {
                start_adapter();
                progressDialog.dismiss();
                initDone=true;
            }
        });


        new Thread(new updateTask()).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        done=true;
    }
    public void start_adapter(){
        ttAdapter=new TimeTableAdapter(this, R.layout.activity_time_table_row, place.getRouteStopList());
        timetable.setAdapter(ttAdapter);
    }

    class updateTask implements Runnable{

        @Override
        public void run() {
            String key=getString(R.string.api_key);
            while(!done) {
                if (initDone) {
                    ttAdapter.updateTimes(key, new Runnable() {
                        @Override
                        public void run() {
                            ttAdapter.notifyDataSetChanged();
                        }
                    });
                }
                try{
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_table, menu);
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
}
