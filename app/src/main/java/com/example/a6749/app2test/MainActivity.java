package com.example.a6749.app2test;

import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView OdimeterKm;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //       Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        ConfigNumberPickers();
        OdimeterKm = findViewById(R.id.textView);

        final Button button = findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    ChangeValueKms();
                }
            });

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

    //region Configuration

    private void ConfigNumberPickers() {

        NumberPicker np = findViewById(R.id.numberPicker1);
        np.setMinValue(0);
        np.setMaxValue(9);
        np = findViewById(R.id.numberPicker2);
        np.setMinValue(0);
        np.setMaxValue(9);
        np = findViewById(R.id.numberPicker3);
        np.setMinValue(0);
        np.setMaxValue(9);
        np = findViewById(R.id.numberPicker4);
        np.setMinValue(0);
        np.setMaxValue(9);
        np = findViewById(R.id.numberPicker5);
        np.setMinValue(0);
        np.setMaxValue(9);
        np =  findViewById(R.id.numberPicker6);
        np.setMinValue(0);
        np.setMaxValue(9);
    }
    //endregion

    //region Methods

    private String GetNumberPickerValueAsString(int id){
        return Integer.toString(((NumberPicker)findViewById(id)).getValue());
    }

    private void ChangeValueKms() {

        OdimeterKm.setText("");

        OdimeterKm.append(GetNumberPickerValueAsString(R.id.numberPicker1));
        OdimeterKm.append(GetNumberPickerValueAsString(R.id.numberPicker2));
        OdimeterKm.append(GetNumberPickerValueAsString(R.id.numberPicker3));
        OdimeterKm.append(GetNumberPickerValueAsString(R.id.numberPicker4));
        OdimeterKm.append(GetNumberPickerValueAsString(R.id.numberPicker5));
        OdimeterKm.append(GetNumberPickerValueAsString(R.id.numberPicker6));

        int totalValue = Integer.parseInt((OdimeterKm.getText()).toString());

        OdimeterKm.append(" Km");

    }


    //endregion

}
