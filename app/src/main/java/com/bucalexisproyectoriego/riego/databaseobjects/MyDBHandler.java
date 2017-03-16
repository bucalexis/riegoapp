package com.bucalexisproyectoriego.riego.databaseobjects;

/**
 * Created by NanX3 on 29/10/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.text.TextUtils;

import java.io.IOException;

public class MyDBHandler extends SQLiteOpenHelper {

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
        this.context = context;
    }
    private Context context;
    //General
    public static final String DB_NAME = "riegoapp.db";
    public static final int DB_VERSION = 1;


    //TABLA CLIENTES
    public static final String RECORDS_TABLE = "records";
    public static final String CROPS_TABLE = "crops";
    public static final String STAGES_TABLE = "stages";
    public static final String KCS_TABLE = "kcs";
    public static final String PRS_TABLE = "prs";


    public static final String SONG_ID = "_id";
    public static final String SONG_NOMBRE = "nombre";
    public static final String CLI_TELF = "telefono";
    public static final String CLI_MAIL = "email";

    public static final String RECORDS_TABLE_SQL =
            "CREATE TABLE  " + RECORDS_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL, " +
                    "crop_id INTEGER NOT NULL, " +
                    "stage_id INTEGER NOT NULL, " +
                    "result TEXT NOT NULL, " +
                    "cc REAL NOT NULL, " +
                    "ha REAL NOT NULL, " +
                    "date TEXT NOT NULL, " +
                    "latitude REAL, " +
                    "longitude REAL, " +
                    "lote TEXT" +
                    ");";

    public static final String CROPS_TABLE_SQL =
            "CREATE TABLE  " + CROPS_TABLE + "(" +
                    "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name" + " TEXT NOT NULL);";

    public static final String STAGES_TABLE_SQL =
            "CREATE TABLE  " + STAGES_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "crop_id INTEGER, " +
                    "FOREIGN KEY(crop_id) REFERENCES crops(_id) );";


    public static final String KCS_TABLE_SQL =
            "CREATE TABLE  " + KCS_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "value REAL NOT NULL, " +
                    "stage_id INTEGER, " +
                    "FOREIGN KEY(stage_id) REFERENCES stages(_id) );";

    public static final String PRS_TABLE_SQL =
            "CREATE TABLE  " + PRS_TABLE + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "value REAL NOT NULL, " +
                    "stage_id INTEGER, " +
                    "FOREIGN KEY(stage_id) REFERENCES stages(_id) );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CROPS_TABLE_SQL);
        db.execSQL(STAGES_TABLE_SQL);
        db.execSQL(KCS_TABLE_SQL);
        db.execSQL(PRS_TABLE_SQL);
        db.execSQL(RECORDS_TABLE_SQL);

        InputStream is = null;
        try {
            is = context.getAssets().open("import.sql");
            if (is != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.e("Exception jaja:" , ex.toString());
        } finally {
            db.endTransaction();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // Muestra log
                }
            }
        }

      //  Crop crop = new Crop();

        //crop.setName("Trigo");
        //addCrop(crop);
        //crop.setName("Maíz");
        //addCrop(crop);
        //crop.setName("Sorgo");
        //addCrop(crop);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CROPS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STAGES_TABLE);

        onCreate(db);
    }

    public void addCrop(Crop crop) {

        ContentValues values = new ContentValues();
        values.put("name", crop.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("crops", null, values);
        db.close();
    }
    private String name;
    private int crop_id;
    private int stage_id;
    private float result;
    private float cc;
    private float ha;
    private float latitude;
    private float longitude;
    private String lote;
    private String date;


    public void addRecord(String name, int crop_id, int stage_id, String result, float cc, float ha, float latitude, float longitude, String lote, String date) {

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("crop_id", crop_id);
        values.put("stage_id", stage_id);
        values.put("result", result);
        values.put("cc", cc);
        values.put("ha", ha);
        values.put("date", date);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("lote", lote);

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert("records", null, values);
        db.close();
    }

    public ArrayList getCrops() {
        String query = "Select * FROM " + "crops";
        Log.d("Query:", query);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Crop> list = new ArrayList<Crop>();

        try {
            while (cursor.moveToNext()) {
                Crop crop = new Crop();
                crop.setId(cursor.getInt(0));
                crop.setName(cursor.getString(1));
                list.add(crop);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return list;
    }

    public ArrayList getRecords() {
        String query = "Select * FROM " + "records";
        Log.d("Query:", query);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Record> list = new ArrayList<Record>();

        try {
            while (cursor.moveToNext()) {
                Record record = new Record();
                record.setId(cursor.getInt(0));
                record.setName(cursor.getString(1));
                record.setCrop_id(cursor.getInt(2));
                record.setStage_id(cursor.getInt(3));
                record.setResult(cursor.getFloat(4));
                record.setCc(cursor.getFloat(5));
                record.setHa(cursor.getFloat(6));
                record.setDate(cursor.getString(7));
                record.setLatitude(cursor.getFloat(8));
                record.setLongitude(cursor.getFloat(9));
                record.setLote(cursor.getString(10));
                list.add(record);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return list;
    }

    public ArrayList getStages(int crop_id) {
        String query = "Select * FROM " + "stages WHERE crop_id = " + crop_id;
        Log.d("Query:", query);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Stage> list = new ArrayList<Stage>();

        try {
            while (cursor.moveToNext()) {
                Stage stage = new Stage();
                stage.setId(cursor.getInt(0));
                stage.setName(cursor.getString(1));
                stage.setCrop_id(cursor.getInt(2));

                list.add(stage);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return list;
    }

    public Kc getKc(int stage_id) {
        String query = "Select * FROM " + "kcs WHERE stage_id = " + stage_id;
        Log.d("Query:", query);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Kc> list = new ArrayList<Kc>();

        try {
            while (cursor.moveToNext()) {
                Kc kc = new Kc();
                kc.setId(cursor.getInt(0));
                kc.setValue(cursor.getFloat(1));
                kc.setStage_id(cursor.getInt(2));

                list.add(kc);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return list.get(0);
    }

    public Pr getPr(int stage_id) {
        String query = "Select * FROM " + "prs WHERE stage_id = " + stage_id;
        Log.d("Query:", query);

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Pr> list = new ArrayList<Pr>();
        try {
            while (cursor.moveToNext()) {
                Pr pr = new Pr();
                pr.setId(cursor.getInt(0));
                pr.setValue(cursor.getFloat(1));
                pr.setStage_id(cursor.getInt(2));
                list.add(pr);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return list.get(0);
    }
}