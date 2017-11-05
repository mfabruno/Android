package com.example.a6749.app2test.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a6749.app2test.BuildConfig;

/**
 * Created by 6749 on 03/11/2017.
 */

public final class SharedPreferencesUtils {

    //region Shared Preferences

    private static final String SharedPreferences_FileName = "CarRegistPreferences";

    private static final String SPKey_FirstTime = "FirstTimeUsingApp";
    public static final Integer SPKey_FirstTime_True = 0;
    public static final Integer SPKey_FirstTime_False = 1;
    public static final Integer SPKey_FirstTime_Updated = 2;

    //source:https://stackoverflow.com/questions/4636141/determine-if-android-app-is-the-first-time-used
    ///Compute results:
    //        0: If this is the first time.
    //        1: It has started ever.
    //        2: It has started once, but not that version , ie it is an update.
    public static int AppGetFirstTimeRun(Context mContext)
    {
        //Check if App Start First Time
        SharedPreferences appPreferences = mContext.getSharedPreferences(SharedPreferences_FileName, Context.MODE_PRIVATE);
        int appCurrentBuildVersion = BuildConfig.VERSION_CODE;
        int appLastBuildVersion = appPreferences.getInt(SPKey_FirstTime, 0);


        if (appLastBuildVersion == appCurrentBuildVersion)
        {
            return SPKey_FirstTime_False; //It is not the first time.

        } else
        {
            appPreferences.edit().putInt(SPKey_FirstTime, appCurrentBuildVersion).apply();
            if (appLastBuildVersion == 0)
            {
                return SPKey_FirstTime_True; //If this is the first time.
            } else
            {
                return SPKey_FirstTime_Updated; //It has started once, but not that version , ie it is an update.
            }
        }
    }

    //endregion

    //Region ActiveCar

    private static final String SPKey_ActiveCar = "ActiveCar";

    public static  Integer GetActiveCar(Context mContext)
    {
        SharedPreferences appPreferences = mContext.getSharedPreferences(SharedPreferences_FileName, Context.MODE_PRIVATE);
        return appPreferences.getInt(SPKey_ActiveCar, -1);
    }

    public static void SetActiveCar(Context mContext, int activeCar)
    {
        SharedPreferences appPreferences = mContext.getSharedPreferences(SharedPreferences_FileName, Context.MODE_PRIVATE);
        appPreferences.edit().putInt(SPKey_ActiveCar, activeCar).apply();
    }


    //endregion
}
