package com.example.a6749.app2test.Activities;

//region imports

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.example.a6749.app2test.BuildConfig;
import com.example.a6749.app2test.Database.DBHelper;
import com.example.a6749.app2test.R;

import java.util.ArrayList;
import java.util.List;

import com.example.a6749.app2test.Utils.SharedPreferencesUtils;

//endregion

public class OdometerActivity extends AppCompatActivity {

    private ListView OdometerListView;
    ArrayList<String> OdometerListViewValues = new ArrayList<String>();
    ArrayList<NumberPicker> NumberPickersList = new ArrayList<NumberPicker>();

    private Integer ActiveCarId = -1;
    private DBHelper CarsDatabase;

    //region Override

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odometer);


        CarsDatabase = DBHelper.GetInstance(this);


        Integer firstTime = SharedPreferencesUtils.AppGetFirstTimeRun(this);
        if (firstTime == SharedPreferencesUtils.SPKey_FirstTime_True)
        {
            //createCar
        } else if (firstTime == SharedPreferencesUtils.SPKey_FirstTime_Updated)
        {
            //update:
            //tables,values...
        }

        ActiveCarId = SharedPreferencesUtils.GetActiveCar(this);

        ConfigureVisualElements();


        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ChangeValueKms();
            }
        });


        OdometerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng)
            {
                String selectedFromList = (String) (OdometerListView.getItemAtPosition(myItemInt));
                selectedFromList += "km";
            }
        });
    }


    @Override
    protected void onDestroy()
    {
        if (CarsDatabase != null)
            CarsDatabase.close();

        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Integer id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region Configuration

    private void ConfigureVisualElements()
    {

        OdometerListView = findViewById(R.id.listView);

        List<String> values = CarsDatabase.OdometerGetAllValuesForCar(ActiveCarId);
        OdometerListViewValues.addAll(values);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, OdometerListViewValues);
        OdometerListView.setAdapter(adapter);
        //((ArrayAdapter<String>) OdometerListView.getAdapter()).notifyDataSetChanged();

        ConfigNumberPickers();

    }

    private void ConfigNumberPickers()
    {
        NumberPickersList.add((NumberPicker) findViewById(R.id.numberPicker1));
        NumberPickersList.add((NumberPicker) findViewById(R.id.numberPicker2));
        NumberPickersList.add((NumberPicker) findViewById(R.id.numberPicker3));
        NumberPickersList.add((NumberPicker) findViewById(R.id.numberPicker4));
        NumberPickersList.add((NumberPicker) findViewById(R.id.numberPicker5));
        NumberPickersList.add((NumberPicker) findViewById(R.id.numberPicker6));

        for (NumberPicker np : NumberPickersList)
        {
            np.setMinValue(0);
            np.setMaxValue(9);
        }

        Integer lastValue = CarsDatabase.OdometerGetLatestValue(ActiveCarId);
        if (lastValue > 0)
        {
            String formatted = String.format("%06d", lastValue);
            for (NumberPicker np : NumberPickersList)
                np.setValue(Integer.parseInt(Character.toString(formatted.charAt(NumberPickersList.indexOf(np)))));

        }
    }

    //endregion

    //region Methods

    private Boolean ChangeValueKms()
    {
        StringBuilder OdometerKm = new StringBuilder();
        for (NumberPicker np : NumberPickersList)
            OdometerKm.append(Integer.toString(np.getValue()));

        Integer valueAsInteger=Integer.parseInt(OdometerKm.toString());
        boolean inserted = CarsDatabase.OdometerInsertRegist(ActiveCarId, valueAsInteger);
        if (!inserted)
        {
            Snackbar.make(findViewById(R.id.OdometerNumberPickers), "Trying to register a value lower than previous for this car. ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        else
        {
            UpdateList(valueAsInteger.toString());
        }

        return inserted;

    }

    private void UpdateList(String newValue)
    {

        OdometerListViewValues.add(0, newValue);
        ((ArrayAdapter<String>) OdometerListView.getAdapter()).notifyDataSetChanged();

    }

    //endregion

}