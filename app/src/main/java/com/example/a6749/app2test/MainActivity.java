package com.example.a6749.app2test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView OdometerListView;
    ArrayList<String> OdometerListViewValues = new ArrayList<String>();
    ArrayList<NumberPicker> NumberPickersList = new ArrayList<NumberPicker>();

    private Integer ActiveCarId = -1;

    private DBHelper CarsDatabase = new DBHelper(this);


    //Region Override

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});
        ConfigureVisualElements();


        final Button button = findViewById(R.id.GoToOdometerActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                //setContentView(R.layout.activity_odometer);
                ChangeContext();
            }
        });


        //OdometerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng)
        //    {
        //        String selectedFromList = (String) (OdometerListView.getItemAtPosition(myItemInt));
        //        selectedFromList += "km";
        //    }
        //});
//
        //Integer firstTime=AppGetFirstTimeRun();
        //if (firstTime == SharedPreferences_Key_FirstTime_True)
        //{
        //    //createCar
        //}
        //else if (firstTime == SharedPreferences_Key_FirstTime_Updated)
        //{
        //    //update:
        //    //tables,values...
        //}
//
        //int activeCar = GetActiveCar();
        //if (activeCar < 1)
        //{
        //    activeCar = LaunchCreateCar();
        //    SetActiveCar(activeCar);
        //}

    }

    private void ChangeContext()
    {

        Intent intent = new Intent(this, OdometerActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy()
    {
        if (CarsDatabase != null)
            CarsDatabase.close();

        super.onDestroy();
    }

    //endregion

    //Region Shared Preferences

    private static final String SharedPreferences_FileName = "CarRegistPreferences";

    private static final String SharedPreferences_Key_FirstTime = "FirstTimeUsingApp";
    private static final Integer SharedPreferences_Key_FirstTime_True = 0;
    private static final Integer SharedPreferences_Key_FirstTime_False = 1;
    private static final Integer SharedPreferences_Key_FirstTime_Updated = 2;

    //source:https://stackoverflow.com/questions/4636141/determine-if-android-app-is-the-first-time-used
    ///Compute results:
    //        0: If this is the first time.
    //        1: It has started ever.
    //        2: It has started once, but not that version , ie it is an update.
    private int AppGetFirstTimeRun()
    {
        //Check if App Start First Time
        SharedPreferences appPreferences = getSharedPreferences(SharedPreferences_FileName, Context.MODE_PRIVATE);
        int appCurrentBuildVersion = BuildConfig.VERSION_CODE;
        int appLastBuildVersion = appPreferences.getInt(SharedPreferences_Key_FirstTime, 0);


        if (appLastBuildVersion == appCurrentBuildVersion)
        {
            return SharedPreferences_Key_FirstTime_False; //It is not the first time.

        } else
        {
            appPreferences.edit().putInt(SharedPreferences_Key_FirstTime, appCurrentBuildVersion).apply();
            if (appLastBuildVersion == 0)
            {
                return SharedPreferences_Key_FirstTime_True; //If this is the first time.
            } else
            {
                return SharedPreferences_Key_FirstTime_Updated; //It has started once, but not that version , ie it is an update.
            }
        }
    }

    //endregion

    //Region ActiveCar

    private static final String SharedPreferences_Key_ActiveCar = "ActiveCar";

    private Integer GetActiveCar()
    {

        SharedPreferences appPreferences = getSharedPreferences(SharedPreferences_FileName, Context.MODE_PRIVATE);
        return appPreferences.getInt(SharedPreferences_Key_ActiveCar, 0);

    }

    private Integer LaunchCreateCar()
    {
        CarsDatabase.CarInsertRegist("Clio", "01-02-2002", "Gasolina", "40");
        return 1;
    }

    private void SetActiveCar(int activeCar)
    {

        ActiveCarId = activeCar;

        SharedPreferences appPreferences = getSharedPreferences(SharedPreferences_FileName, Context.MODE_PRIVATE);
        appPreferences.edit().putInt(SharedPreferences_Key_ActiveCar, activeCar).apply();

    }


    //endregion

    //BackupDatabase in ?SD?
    //private void BackupDataBase()
    //{
    //    if (BuildConfig.DEBUG)
    //    {
    //        // do something for a debug build
    //        try
    //        {
    //            File sd = Environment.getExternalStorageDirectory();
//
    //            if (sd.canWrite())
    //            {
    //                String currentDBPath = "/data/data/" + getPackageName() + "/databases/" + DBHelper.DATABASE_NAME;
    //                File currentDB = new File(currentDBPath);
//
    //                String dateTime = (DBHelper.DATABASE_DATEFORMAT_DATETIME).format(Calendar.getInstance().getTime());
    //                String backupDBPath = dateTime +"_"+DBHelper.DATABASE_NAME;
    //                File backupDB = new File(sd, backupDBPath);
//
    //                if (currentDB.exists())
    //                {
    //                    FileChannel src = new FileInputStream(currentDB).getChannel();
    //                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
    //                    dst.transferFrom(src, 0, src.size());
    //                    src.close();
    //                    dst.close();
    //                }
    //            }
    //        } catch (Exception e)
    //        {
//
    //        }
    //    }
    //}


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

    //region Configuration

    private void ConfigureVisualElements()
    {

        Spinner spinner = findViewById(R.id.ChooseCarSpinner);

        List<String> list = CarsDatabase.GetCars();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


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

        boolean inserted = CarsDatabase.OdometerInsertRegist(ActiveCarId, Integer.parseInt(OdometerKm.toString()));
        if (!inserted)
        {
            Snackbar.make(findViewById(R.id.toolbar), "Trying to register a value lower than previous for this car. ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        return inserted;

    }

    private void UpdateList()
    {

        OdometerListViewValues.add(0, CarsDatabase.OdometerGetLatestValueWithDate(ActiveCarId));
        ((ArrayAdapter<String>) OdometerListView.getAdapter()).notifyDataSetChanged();

    }

    //endregion

}