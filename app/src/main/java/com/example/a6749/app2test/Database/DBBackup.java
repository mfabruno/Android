package com.example.a6749.app2test.Database;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;

/**
 * Created by 6749 on 02/11/2017.
 */

public class DBBackup {
    private Context mContext;

   // public DBBackup(Context context)
   // {
   //     this.mContext = context;
   // }
//
   // public void other()
   // {
   //     try
   //     {
   //         File sd = Environment.getExternalStorageDirectory();
//
   //         if (sd.canWrite())
   //         {
//
//
   //             String currentDBPath = "/data/data/" + mContext.getPackageName() + "/databases/" + DBHelper.DATABASE_NAME;
   //             File currentDB = new File(currentDBPath);
//
   //             String dateTime = (DBHelper.DATABASE_DATEFORMAT_DATE_ALL).format(Calendar.getInstance().getTime());
   //             String backupDBPath = dateTime + "_" + DBHelper.DATABASE_NAME;
   //             File backupDB = new File(sd, backupDBPath);
//
   //             if (currentDB.exists())
   //             {
   //                 FileChannel src = new FileInputStream(currentDB).getChannel();
   //                 FileChannel dst = new FileOutputStream(backupDB).getChannel();
   //                 dst.transferFrom(src, 0, src.size());
   //                 src.close();
   //                 dst.close();
   //             }
   //         }
   //     } catch (Exception e)
   //     {
//
   //     }
//
   // }
//
   // public void exportDB()
   // {
   //     try
   //     {
   //         File sd = Environment.getExternalStorageDirectory();
   //         File data = Environment.getDataDirectory();
   //         if (sd.canWrite())
   //         {
   //             String currentDBPath = "//data//com.innovagency.meucarro//databases//commments.db";
   //             String backupDBPath = "/MeuCarro/meuCarro.db";
   //             File direct = new File(Environment.getExternalStorageDirectory() + "/MeuCarro");
   //             if (!direct.exists())
   //             {
   //                 direct.mkdir();
   //             }
   //             File currentDB = new File(data, currentDBPath);
   //             File backupDB = new File(sd, backupDBPath);
   //             FileChannel src = new FileInputStream(currentDB).getChannel();
   //             FileChannel dst = new FileOutputStream(backupDB).getChannel();
   //             dst.transferFrom(src, 0, src.size());
   //             src.close();
   //             dst.close();
   //             sendFileToMail(backupDB);
   //         }
   //     } catch (Exception e)
   //     {
   //         Log.e("MainActivity", e.toString());
   //     }
   // }
//
   // public void importDB()
   // {
   //     try
   //     {
   //         File sd = Environment.getExternalStorageDirectory();
   //         File data = Environment.getDataDirectory();
   //         if (sd.canWrite())
   //         {
   //             File backupDB = new File(data, "//data//com.innovagency.meucarro//databases//commments.db");
   //             FileChannel src = new FileInputStream(new File(sd, "/MeuCarro/meuCarro.db")).getChannel();
   //             FileChannel dst = new FileOutputStream(backupDB).getChannel();
   //             dst.transferFrom(src, 0, src.size());
   //             src.close();
   //             dst.close();
   //         }
   //     } catch (Exception e)
   //     {
   //     }
   // }
//
   // public void sendFileToMail(File file)
   // {
    //    DateUtils dateUtils = new DateUtils();
    //    Uri u1 = Uri.fromFile(file);
    //    Intent sendIntent = new Intent("android.intent.action.SEND");
    //    sendIntent.putExtra("android.intent.extra.SUBJECT", "MeuCarro db backup");
    //    sendIntent.putExtra("android.intent.extra.TEXT", "MeuCarro db backup " + dateUtils.getTimeSpecialFormat(dateUtils.getTimeInMillis(), "dd-MM-yyyy hh:mm:ss"));
    //    sendIntent.putExtra("android.intent.extra.STREAM", u1);
    //    sendIntent.setType("text/html");
    //    this.mContext.startActivity(sendIntent);
    //}
}
