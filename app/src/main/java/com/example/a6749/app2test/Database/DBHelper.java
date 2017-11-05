package com.example.a6749.app2test.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.a6749.app2test.Classes.Car;
import com.example.a6749.app2test.Classes.FillUp;
import com.example.a6749.app2test.Classes.Odometer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.time.LocalTime.now;


public class DBHelper extends SQLiteOpenHelper {

    //region Overrides SQLiteOpenHelper

    public static final String DATABASE_NAME = "CarRegist.db";
    private static final Integer DATABASE_VERSION = 1;

    // Use a Singleton to Instantiate the SQLiteOpenHelper
    private static DBHelper sInstance;

    public static synchronized DBHelper GetInstance(Context context)
    {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null)
        {
            sInstance = new DBHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(CREATE_TABLE_CAR);
        db.execSQL(CREATE_TABLE_ODOMETER);
        db.execSQL(CREATE_TABLE_FILLUP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAR_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ODOMETER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILLUP_NAME);
        onCreate(db);

    }


    //endregion

    //Region Utils

    //Region Time

    public static final String DATABASE_PATTERN_DATE_ALL = "yyyy-MM-dd";//"yyyy-MM-dd-HH:mm:ss";
    public static final DateFormat DATABASE_DATEFORMAT_DATE_ALL = new SimpleDateFormat(DATABASE_PATTERN_DATE_ALL);

    public static final String DATABASE_PATTERN_YEAR = "yyyy";
    public static final DateFormat DATABASE_DATEFORMAT_YEAR = new SimpleDateFormat(DATABASE_PATTERN_YEAR);

    public static final String DATABASE_PATTERN_MONTH = "MM";
    public static final DateFormat DATABASE_DATEFORMAT_MONTH = new SimpleDateFormat(DATABASE_PATTERN_MONTH);

    //endregion

    //endregion


    //region CAR

    //region DataBase

    private static final String TABLE_CAR_NAME = "Car";

    private static final String TABLE_CAR_COLUMN_ID = "car_id";
    private static final Integer TABLE_CAR_COLUMN_ID_INDEX = 0;

    private static final String TABLE_CAR_COLUMN_NAME = "car_name";
    private static final Integer TABLE_CAR_COLUMN_NAME_INDEX = 1;

    private static final String TABLE_CAR_COLUMN_DATE = "car_date";
    private static final Integer TABLE_CAR_COLUMN_DATE_INDEX = 2;

    private static final String TABLE_CAR_COLUMN_FUEL_TYPE = "car_fuel_type";
    private static final Integer TABLE_CAR_COLUMN_FUEL_TYPE_INDEX = 3;

    private static final String TABLE_CAR_COLUMN_FUEL_CAPACITY = "car_fuel_capacity";
    private static final Integer TABLE_CAR_COLUMN_FUEL_CAPACITY_INDEX = 4;

    private static final String CREATE_TABLE_CAR = "CREATE TABLE " +
            TABLE_CAR_NAME + "(" +
            TABLE_CAR_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_CAR_COLUMN_NAME + " TEXT, " +
            TABLE_CAR_COLUMN_DATE + " TEXT, " +
            TABLE_CAR_COLUMN_FUEL_TYPE + " TEXT, " +
            TABLE_CAR_COLUMN_FUEL_CAPACITY + " TEXT" + ");";

    //endregion

    //region Methods

    public long CarInsertRegist(String name, String date, String fuelType, String fuelCapacity)
    {

        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE_CAR_COLUMN_NAME, name);
        contentValues.put(TABLE_CAR_COLUMN_DATE, date);
        contentValues.put(TABLE_CAR_COLUMN_FUEL_TYPE, fuelType);
        contentValues.put(TABLE_CAR_COLUMN_FUEL_CAPACITY, fuelCapacity);

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_CAR_NAME, null, contentValues);

    }

    public boolean DatabaseHasCars()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try
        {
            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM " + TABLE_CAR_NAME);

            cursor = db.rawQuery(query.toString(), null);

            //boolean hasCars = cursor != null && cursor.getInt(TABLE_CAR_COLUMN_ID_INDEX) > 0;
            boolean hasCars = cursor != null && cursor.getInt(TABLE_CAR_COLUMN_ID_INDEX) > 0;


            return hasCars;

        } finally
        {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
    }

    public List<String> GetAllCars()
    {

        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try
        {

            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM " + TABLE_CAR_NAME);

            cursor = db.rawQuery(query.toString(), null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                //String string = DatabaseUtils.dumpCursorToString(cursor);
                list.add(cursor.getString(TABLE_CAR_COLUMN_NAME_INDEX));

                cursor.moveToNext();
            }

            return list;
        } finally
        {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

    }

    public Car CarGetCar(Integer id_car)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try
        {

            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM " + TABLE_CAR_NAME);
            query.append(" WHERE " + TABLE_CAR_COLUMN_ID + "=").append(id_car);

            cursor = db.rawQuery(query.toString(), null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                //String string = DatabaseUtils.dumpCursorToString(cursor);
                Car car = CursorToCar(cursor);

                return car;
            }

            return null;
        } finally
        {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
    }

    private Car CursorToCar(Cursor cursor)
    {

        Car car = new Car();
        car.Id = cursor.getInt(TABLE_CAR_COLUMN_ID_INDEX);
        car.CarName = cursor.getInt(TABLE_CAR_COLUMN_NAME_INDEX);
        car.CarDate = cursor.getString(TABLE_CAR_COLUMN_DATE_INDEX);
        car.CarFuelCapacity = cursor.getString(TABLE_CAR_COLUMN_FUEL_CAPACITY_INDEX);
        car.CarFuelType = cursor.getString(TABLE_CAR_COLUMN_FUEL_TYPE_INDEX);

        return car;

    }

    //endregion

    //endregion

    //region ODOMETER

    //region DataBase

    private static final String TABLE_ODOMETER_NAME = "Odometer";

    private static final String TABLE_ODOMETER_COLUMN_ID = "odometer_id";
    private static final Integer TABLE_ODOMETER_COLUMN_ID_INDEX = 0;

    private static final String TABLE_ODOMETER_COLUMN_ID_CAR = "odometer_id_car";
    private static final Integer TABLE_ODOMETER_COLUMN_ID_CAR_INDEX = 1;

    private static final String TABLE_ODOMETER_COLUMN_VALUE_KM = "odometer_value";
    private static final Integer TABLE_ODOMETER_COLUMN_VALUE_KM_INDEX = 2;

    private static final String TABLE_ODOMETER_COLUMN_DATE_ALL = "odometer_date_all";
    private static final Integer TABLE_ODOMETER_COLUMN_DATE_ALL_INDEX = 3;

    private static final String TABLE_ODOMETER_COLUMN_DATE_YEAR = "odometer_date_year";
    private static final Integer TABLE_ODOMETER_COLUMN_DATE_YEAR_INDEX = 4;

    private static final String TABLE_ODOMETER_COLUMN_DATE_MONTH = "odometer_date_month";
    private static final Integer TABLE_ODOMETER_COLUMN_DATE_MONTH_INDEX = 5;

    private static final String CREATE_TABLE_ODOMETER = "CREATE TABLE " +
            TABLE_ODOMETER_NAME + "(" +
            TABLE_ODOMETER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_ODOMETER_COLUMN_ID_CAR + " INTEGER REFERENCES " + TABLE_CAR_NAME + " ON DELETE CASCADE, " +
            TABLE_ODOMETER_COLUMN_VALUE_KM + " INTEGER NOT NULL, " +
            TABLE_ODOMETER_COLUMN_DATE_ALL + " TEXT NOT NULL," +
            TABLE_ODOMETER_COLUMN_DATE_YEAR + " INTEGER NOT NULL," +
            TABLE_ODOMETER_COLUMN_DATE_MONTH + " INTEGER NOT NULL" + ");";

    //endregion

    //region Methods

    public boolean OdometerInsertRegist(Integer id_car, Integer value_km)
    {
        boolean insertedCorrectly = false;

        if (OdemeterIsValidValue(id_car, value_km))
        {

            Date now = Calendar.getInstance().getTime();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_ODOMETER_COLUMN_ID_CAR, id_car);
            contentValues.put(TABLE_ODOMETER_COLUMN_VALUE_KM, value_km);
            contentValues.put(TABLE_ODOMETER_COLUMN_DATE_ALL, DATABASE_DATEFORMAT_DATE_ALL.format(now));
            contentValues.put(TABLE_ODOMETER_COLUMN_DATE_YEAR, Integer.parseInt(DATABASE_DATEFORMAT_YEAR.format(now)));
            contentValues.put(TABLE_ODOMETER_COLUMN_DATE_MONTH, Integer.parseInt(DATABASE_DATEFORMAT_MONTH.format(now)));

            SQLiteDatabase db = this.getReadableDatabase();
            insertedCorrectly = db.insert(TABLE_ODOMETER_NAME, null, contentValues) != -1;

        }

        return insertedCorrectly;


    }

    private boolean OdemeterIsValidValue(Integer id_car, Integer value_km)
    {
        return OdometerGetLatestValue(id_car) < value_km;
    }

    public Integer OdometerGetLatestValue(Integer id_car)
    {

        StringBuilder query = new StringBuilder();
        query.append(" SELECT * FROM " + TABLE_ODOMETER_NAME);
        query.append(" WHERE " + TABLE_ODOMETER_COLUMN_ID_CAR + "=").append(id_car);
        query.append(" ORDER BY " + TABLE_ODOMETER_COLUMN_ID + " DESC;");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery(query.toString(), null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                //String string = DatabaseUtils.dumpCursorToString(cursor);

                Integer value_km = cursor.getInt(TABLE_ODOMETER_COLUMN_VALUE_KM_INDEX);

                return value_km;
            }

            return -1;
        } finally
        {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
    }

    public String OdometerGetLatestValueWithDate(Integer id_car)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try
        {

            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM " + TABLE_ODOMETER_NAME);
            query.append(" WHERE " + TABLE_ODOMETER_COLUMN_ID_CAR + "=").append(id_car);
            query.append(" ORDER BY " + TABLE_ODOMETER_COLUMN_ID + " DESC;");

            cursor = db.rawQuery(query.toString(), null);

            cursor.moveToFirst();
            if (!cursor.isAfterLast())
            {
                //String string = DatabaseUtils.dumpCursorToString(cursor);

                Odometer odometer = CursorToOdometer(cursor);

                return odometer.DateAll + ": " + Long.toString(odometer.Value);
            }

            return "";
        } finally
        {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

    }

    public List<String> OdometerGetAllValuesForCar(Integer id_car)
    {

        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try
        {

            StringBuilder query = new StringBuilder();
            query.append(" SELECT * FROM " + TABLE_ODOMETER_NAME);
            query.append(" WHERE " + TABLE_ODOMETER_COLUMN_ID_CAR + "=").append(id_car);
            query.append(" ORDER BY " + TABLE_ODOMETER_COLUMN_ID + " DESC;");

            cursor = db.rawQuery(query.toString(), null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                //String string = DatabaseUtils.dumpCursorToString(cursor);
                list.add(cursor.getString(TABLE_ODOMETER_COLUMN_VALUE_KM_INDEX));

                cursor.moveToNext();
            }

            return list;
        } finally
        {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }
    }

    private Odometer CursorToOdometer(Cursor cursor)
    {

        Odometer odometer = new Odometer();
        odometer.Id = cursor.getInt(TABLE_ODOMETER_COLUMN_ID_INDEX);
        odometer.IdCar = cursor.getInt(TABLE_ODOMETER_COLUMN_ID_CAR_INDEX);
        odometer.Value = cursor.getInt(TABLE_ODOMETER_COLUMN_VALUE_KM_INDEX);
        odometer.DateAll = cursor.getString(TABLE_ODOMETER_COLUMN_DATE_ALL_INDEX);
        odometer.DateYear = cursor.getString(TABLE_ODOMETER_COLUMN_DATE_YEAR_INDEX);
        odometer.DateMonth = cursor.getString(TABLE_ODOMETER_COLUMN_DATE_MONTH_INDEX);

        return odometer;

    }

    //endregion

    //endregion

    //region FILLUP

    //region DataBase

    private static final String TABLE_FILLUP_NAME = "Fillup";

    private static final String TABLE_FILLUP_COLUMN_ID = "fillup_id";
    private static final Integer TABLE_FILLUP_COLUMN_ID_INDEX = 0;

    private static final String TABLE_FILLUP_COLUMN_ID_CAR = "fillup_id_car";
    private static final Integer TABLE_FILLUP_COLUMN_ID_CAR_INDEX = 1;

    private static final String TABLE_FILLUP_COLUMN_ID_ODOMETER = "fillup_id_odometer";
    private static final Integer TABLE_FILLUP_COLUMN_ID_ODOMETER_INDEX = 2;

    private static final String TABLE_FILLUP_COLUMN_LITERS = "fillup_liters";
    private static final Integer TABLE_FILLUP_COLUMN_LITERS_INDEX = 3;

    private static final String TABLE_FILLUP_COLUMN_PRICE = "fillup_price";
    private static final Integer TABLE_FILLUP_COLUMN_PRICE_INDEX = 4;

    private static final String TABLE_FILLUP_COLUMN_LITERPRICE = "fillup_literprice";
    private static final Integer TABLE_FILLUP_COLUMN_LITERPRICE_INDEX = 5;

    private static final String TABLE_FILLUP_COLUMN_DATEIME_ALL = "fillup_date_all";
    private static final Integer TABLE_FILLUP_COLUMN_DATE_ALL_INDEX = 6;

    private static final String TABLE_FILLUP_COLUMN_DATE_YEAR = "fillup_date_year";
    private static final Integer TABLE_FILLUP_COLUMN_DATE_YEAR_INDEX = 7;

    private static final String TABLE_FILLUP_COLUMN_DATE_MONTH = "fillup_date_month";
    private static final Integer TABLE_FILLUP_COLUMN_DATE_MONTH_INDEX = 8;

    private static final String TABLE_FILLUP_COLUMN_LOCATION = "fillup_location";
    private static final Integer TABLE_FILLUP_COLUMN_LOCATION_INDEX = 9;

    private static final String TABLE_FILLUP_COLUMN_NOTES = "fillup_notes";
    private static final Integer TABLE_FILLUP_COLUMN_NOTES_INDEX = 10;

    private static final String CREATE_TABLE_FILLUP = "CREATE TABLE " +
            TABLE_FILLUP_NAME + "(" +
            TABLE_FILLUP_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TABLE_FILLUP_COLUMN_ID_CAR + " INTEGER REFERENCES " + TABLE_CAR_NAME + " ON DELETE CASCADE, " +
            TABLE_FILLUP_COLUMN_ID_ODOMETER + " INTEGER REFERENCES " + TABLE_ODOMETER_NAME + " ON DELETE SET NULL, " +
            TABLE_FILLUP_COLUMN_LITERS + " REAL NOT NULL, " +
            TABLE_FILLUP_COLUMN_PRICE + " REAL NOT NULL, " +
            TABLE_FILLUP_COLUMN_LITERPRICE + " REAL NOT NULL, " +
            TABLE_FILLUP_COLUMN_DATEIME_ALL + " TEXT NOT NULL, " +
            TABLE_FILLUP_COLUMN_DATE_YEAR + " INTEGER NOT NULL, " +
            TABLE_FILLUP_COLUMN_DATE_MONTH + " INTEGER NOT NULL, " +
            TABLE_FILLUP_COLUMN_LOCATION + " TEXT NOT NULL, " +
            TABLE_FILLUP_COLUMN_NOTES + " TEXT NOT NULL" + ");";


    //endregion

    //region Methods

    private FillUp CursorToFillUp(Cursor cursor)
    {

        FillUp fillUp = new FillUp();
        fillUp.Id = cursor.getInt(TABLE_FILLUP_COLUMN_ID_INDEX);
        fillUp.IdCar = cursor.getInt(TABLE_FILLUP_COLUMN_ID_CAR_INDEX);
        fillUp.IdOdometer = cursor.getInt(TABLE_FILLUP_COLUMN_ID_ODOMETER_INDEX);

        fillUp.Liters = cursor.getDouble(TABLE_FILLUP_COLUMN_LITERS_INDEX);
        fillUp.Price = cursor.getDouble(TABLE_FILLUP_COLUMN_PRICE_INDEX);
        fillUp.LiterPrice = cursor.getDouble(TABLE_FILLUP_COLUMN_LITERPRICE_INDEX);

        fillUp.DateAll = cursor.getString(TABLE_FILLUP_COLUMN_DATE_ALL_INDEX);
        fillUp.DateYear = cursor.getInt(TABLE_FILLUP_COLUMN_DATE_YEAR_INDEX);
        fillUp.DateMonth = cursor.getInt(TABLE_FILLUP_COLUMN_DATE_MONTH_INDEX);

        fillUp.Location = cursor.getString(TABLE_FILLUP_COLUMN_LOCATION_INDEX);
        fillUp.Notes = cursor.getString(TABLE_FILLUP_COLUMN_NOTES_INDEX);

        return fillUp;

    }


    //endregion

    //endregion

    //region REPAIRS

    //region DataBase

    //endregion

    //region Methods

    //endregion

    //endregion

    //region future
    //public boolean updatePerson(Integer id, String name, String gender, Integer age) {
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    ContentValues contentValues = new ContentValues();
    //    contentValues.put(PERSON_COLUMN_NAME, name);
    //    contentValues.put(PERSON_COLUMN_GENDER, gender);
    //    contentValues.put(PERSON_COLUMN_AGE, age);
    //    db.update(PERSON_TABLE_NAME, contentValues, PERSON_COLUMN_ID + " = ? ", new String[]{Integer.toString(id)});
    //    return true;
    //}
    //
    //public Integer deletePerson(Integer id) {
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    return db.delete(PERSON_TABLE_NAME,
    //            PERSON_COLUMN_ID + " = ? ",
    //            new String[]{Integer.toString(id)});
    //}
    //
    //public Cursor getPerson(Integer id) {
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    Cursor cursor = db.rawQuery("SELECT * FROM " + PERSON_TABLE_NAME + " WHERE " +
    //            PERSON_COLUMN_ID + "=?", new String[]{Integer.toString(id)});
    //    return cursor;
    //}
    //
    //public Cursor getAllPersons() {
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    Cursor cursor = db.rawQuery("SELECT * FROM " + PERSON_TABLE_NAME, null);
    //    return cursor;
    //}
    //endregion

}