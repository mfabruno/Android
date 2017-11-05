package com.example.a6749.app2test.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.a6749.app2test.Database.DBHelper;
import com.example.a6749.app2test.R;
import com.example.a6749.app2test.Utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.DateUtils;
import android.widget.Spinner;

import java.util.Locale;

import static com.example.a6749.app2test.Utils.StringUtils.IsNullEmptyOrWhiteSpace;
import static com.example.a6749.app2test.Utils.StringUtils.StringEmpty;

/**
 * Created by 6749 on 31/10/2017.
 */

public class CarCreateActivity extends AppCompatActivity {

    private Integer ActiveCarId = -1;

    private DBHelper CarsDatabase;
    private Calendar myCalendar = Calendar.getInstance();
    private View ErrorsDummyView;
    private EditText carName;
    private EditText carDate;
    private Spinner carFuelType;

    //Region Override

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_create);

        CarsDatabase = DBHelper.GetInstance(this);
        ErrorsDummyView = findViewById(R.id.createCarLineartLayout);

        //Car Name:
        carName = findViewById(R.id.createCarName);
        carName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                VerifyCarName();
            }
        });

        //Car Date:
        carDate = findViewById(R.id.createCarDate);
        carDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(CarCreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Car Fuel Type:
        carFuelType = findViewById(R.id.createCarFuelType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.FuelTypes, android.R.layout.simple_spinner_item);// Create an ArrayAdapter using the string array and a default spinner layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Specify the layout to use when the list of choices appears
        carFuelType.setAdapter(adapter);// Apply the adapter to the spinner


        Button createCarAddButton = findViewById(R.id.createCarAddButton);
        createCarAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                AddCar();
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

    //endregion

    //region Errors

    private void SetErrorInSnackbar(String error)
    {
        Snackbar.make(ErrorsDummyView, error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    private void SetErrorInputLayout(int id, String error)
    {
        TextInputLayout elem = findViewById(id);
        elem.setError(error);
    }

    private void SetErrorDate(String error)
    {
        SetErrorInputLayout(R.id.createCarDateTextInputLayout, error);
    }

    private void CleanErrorDate()
    {
        SetErrorInputLayout(R.id.createCarDateTextInputLayout, null);
    }

    private void CleanErrorName()
    {
        SetErrorInputLayout(R.id.createCarNameTextInputLayout, null);
    }

    //endregion

    //region Database

    private void AddCar()
    {

        boolean HasErrors = false;

        //Name:
        if (VerifyCarName())
            HasErrors = true;

        //Date:
        if (VerifyCarDate())
            HasErrors = true;

        //Errors:
        if (HasErrors)
        {
            SetErrorInSnackbar("Errors found.");
            return;
        }

        String dateValue = DBHelper.DATABASE_DATEFORMAT_DATE_ALL.format(myCalendar.getTime());
        long carId = CarsDatabase.CarInsertRegist(GetText(carName), dateValue, GetText(carFuelType), GetText(R.id.createCarFuelCapacity));
        if (carId == -1)
        {
            SetErrorInSnackbar("Errors inserting car in database.");
            return;
        }

        SharedPreferencesUtils.SetActiveCar(this, (int)carId);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //endregion

    //region utils

    private String GetText(int elemId)
    {
        EditText elem = findViewById(elemId);
        return elem.getText().toString();
    }

    private String GetText(EditText editText)
    {
        return editText.getText().toString();
    }

    private String GetText(Spinner spinner)
    {
        return spinner.getSelectedItem().toString();
    }

    //endregion

    //region Verifications

    private boolean VerifyCarDate()
    {
        boolean HasErrors = false;

        CleanErrorDate();
        String dateValue = GetText(R.id.createCarDate);
        if (IsNullEmptyOrWhiteSpace(dateValue))
        {
            HasErrors = true;
            SetErrorDate("You need to enter a date for this car.");
        } else
        {
            Date carDate = myCalendar.getTime();
            Date today = (Calendar.getInstance()).getTime();
            if (!carDate.before(today))
            {
                HasErrors = true;
                SetErrorDate("Trying to register a car with a date in the future.");
            }

        }

        return HasErrors;
    }

    private boolean VerifyCarName()
    {
        boolean HasErrors = false;

        CleanErrorName();
        if (IsNullEmptyOrWhiteSpace(GetText(carName)))
        {
            TextInputLayout til = (TextInputLayout) findViewById(R.id.createCarNameTextInputLayout);
            til.setError("You need to enter a name for this car.");
            HasErrors = true;
        }

        return HasErrors;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth)
        {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            UpdateDateLabel();
            VerifyCarDate();

        }

    };

    //endregion


    private void UpdateDateLabel()
    {
        ((EditText) findViewById(R.id.createCarDate)).setText(DBHelper.DATABASE_DATEFORMAT_DATE_ALL.format(myCalendar.getTime()));
    }

}