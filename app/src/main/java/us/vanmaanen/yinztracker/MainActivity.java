package us.vanmaanen.yinztracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void ListListner(){
        //Provide function for item that is clicked
        //TODO - Write Function for Listner for Main Place list

    }
    public void ClickAddPlace(View view){
        //Add Place on Button Click... Launch Add_edit_activity
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);

    }
    public void InsertPlace(String name,Integer id){
        //TODO - Write Function to insert Place into list by name and id

    }
    public void InsertPlace(Map<Integer, String> places){
       //TODO - Write Function to insert place into list list given MAP???
    }

}
