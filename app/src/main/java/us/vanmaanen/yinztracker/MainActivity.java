package us.vanmaanen.yinztracker;

import android.content.Intent;
import android.database.DataSetObserver;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import us.vanmaanen.yinztracker.PlaceDB.Place;
import us.vanmaanen.yinztracker.RestAPI.BusRoute;
import us.vanmaanen.yinztracker.ViewHelpers.MainListAdapter;


public class MainActivity extends ActionBarActivity {
    ListView MainList;
    static final int ADD_EDIT_RESULT=1;
    List<Place> PlaceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.MainList=(ListView) this.findViewById(R.id.PlaceList);

        updateList();
        this.MainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place=(Place) parent.getItemAtPosition(position);

                Intent intent= new Intent(getBaseContext(),TimeTableActivity.class);
                intent.putExtra("Place_ID",place.getId());
                startActivity(intent);
            }
        });
    }
    private void updateList(){
        PlaceList=new ArrayList<Place>();
        Iterator<Place> PlIter= Place.findAll(Place.class);
        int count=0;
        Place next;
        while(PlIter.hasNext()) {
            next=PlIter.next();
            if (next==null){
                Log.d("App","WTF"+count);
            }
            next.FindRouteStops();
            this.PlaceList.add(next);
            count=count+1;
        }
        if (this.PlaceList!=null) {
            this.MainList.setAdapter(new MainListAdapter(this, R.layout.activity_main_row, this.PlaceList));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ADD_EDIT_RESULT :{
                if (resultCode==RESULT_OK) {
                    updateList();
                }
            };
        }
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
    public void ListListner(){
        //Provide function for item that is clicked
        //TODO - Write Function for Listner for Main Place list

    }
    public void ClickAddPlace(View view){
        //Add Place on Button Click... Launch Add_edit_activity
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivityForResult(intent, ADD_EDIT_RESULT);
        Log.w("App","End of Intent.");

    }
    public void InsertPlace(String name,Integer id){
        //TODO - Write Function to insert Place into list by name and id

    }
    public void InsertPlace(Map<Integer, String> places){
       //TODO - Write Function to insert place into list list given MAP???
    }

}
