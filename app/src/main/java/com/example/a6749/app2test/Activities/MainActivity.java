package com.example.a6749.app2test.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import com.example.a6749.app2test.BuildConfig;
import com.example.a6749.app2test.Classes.Car;
import com.example.a6749.app2test.Database.DBHelper;
import com.example.a6749.app2test.R;
import com.example.a6749.app2test.Utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView OdometerListView;
    ArrayList<String> OdometerListViewValues = new ArrayList<String>();
    ArrayList<NumberPicker> NumberPickersList = new ArrayList<NumberPicker>();

    private Integer ActiveCarId = -1;
    private Spinner ActiveCarSpinner;

    private DBHelper CarsDatabase;

    //region Override

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CarsDatabase = DBHelper.GetInstance(this);
        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});
        ConfigureVisualElements();


        Button button = findViewById(R.id.GoToOdometerActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                GoToOdometerActivity();
            }
        });

        button = findViewById(R.id.GoToAddNewCarActivity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                GoToAddNewCarActivity();
            }
        });


        Integer firstTime = SharedPreferencesUtils.AppGetFirstTimeRun(this);
        if (firstTime == SharedPreferencesUtils.SPKey_FirstTime_True)
        {
            //createCar
            //welcome
        } else if (firstTime == SharedPreferencesUtils.SPKey_FirstTime_Updated)
        {
            //update:
            //tables,values...
        }

        ActiveCarId = SharedPreferencesUtils.GetActiveCar(this);
        if (ActiveCarId == -1)
        {
            GoToAddNewCarActivity();
        }


        //Sets:
        ActiveCarSpinner.setSelection(ActiveCarId - 1);


        RefreshView();


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

    private void GoToOdometerActivity()
    {

        Intent intent = new Intent(getApplicationContext(), OdometerActivity.class);
        startActivity(intent);
    }

    private void GoToAddNewCarActivity()
    {

        Intent intent = new Intent(getApplicationContext(), CarCreateActivity.class);
        startActivity(intent);
    }


    //region Configuration

    private void ConfigureVisualElements()
    {

        ActiveCarSpinner = findViewById(R.id.ChooseCarSpinner);

        List<String> list = CarsDatabase.GetAllCars();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ActiveCarSpinner.setAdapter(dataAdapter);

        ActiveCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                ChangeActiveCar(position+1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    //endregion

    //region Methods

    private void ChangeActiveCar(int position)
    {
        ActiveCarId=position;
        SharedPreferencesUtils.SetActiveCar(this, position);
        RefreshView();
    }

    private void RefreshView()
    {
        StringBuilder text= new StringBuilder();

        Car car=CarsDatabase.CarGetCar(ActiveCarId);
        if(car!=null)
        {
            text.append("Car= ").append(car.CarName).append("\n");
            text.append("Date= ").append(car.CarDate).append("\n");
            text.append("FuelCapacity= ").append(car.CarFuelCapacity).append("\n");
            text.append("FuelType= ").append(car.CarFuelType).append("\n");
        }

        TextView odometerActiveCar = findViewById(R.id.OdometerActiveCar);
        text.append("Odometer=").append(CarsDatabase.OdometerGetLatestValue(ActiveCarId));



        odometerActiveCar.setText(text);
    }
    //endregion

    //region backup

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

    //endregion
}