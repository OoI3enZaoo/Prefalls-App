package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ben on 13/6/2560.
 */

public class DBAlertAll extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AlertAll.db";
    public static final String CONTACTS_TABLE_NAME = "alertall";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_TYPE = "type";
    public static final String CONTACTS_COLUMN_IMAGE = "image";
    public static final String CONTACTS_COLUMN_TIME = "time";
    public static final String CONTACTS_COLUMN_LAT = "lat";
    public static final String CONTACTS_COLUMN_LONG = "lng";
    public static final String CONTACTS_COLUMN_COLOR = "color";
    public DBAlertAll(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "(" +
                        CONTACTS_COLUMN_NAME + " text, " +
                        CONTACTS_COLUMN_TYPE + " text, " +
                        CONTACTS_COLUMN_IMAGE + " text, " +
                        CONTACTS_COLUMN_TIME + " integer, " +
                        CONTACTS_COLUMN_LAT + " text, " +
                        CONTACTS_COLUMN_LONG + " text, " +
                        CONTACTS_COLUMN_COLOR + " text " +
                        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }
    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, null, null);
        db.close();
    }
    public boolean insertData(String name , String type , String image , String time,String lat, String lng,String color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_TYPE, type);
        contentValues.put(CONTACTS_COLUMN_IMAGE, image);
        contentValues.put(CONTACTS_COLUMN_TIME, time);
        contentValues.put(CONTACTS_COLUMN_LAT, lat);
        contentValues.put(CONTACTS_COLUMN_LONG, lng);
        contentValues.put(CONTACTS_COLUMN_COLOR, color);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+CONTACTS_COLUMN_NAME+","+CONTACTS_COLUMN_TYPE+", "+CONTACTS_COLUMN_IMAGE+" ,datetime("+CONTACTS_COLUMN_TIME+"/1000, 'unixepoch', 'localtime') ,"+CONTACTS_COLUMN_LAT+","+CONTACTS_COLUMN_LONG+" ,"+CONTACTS_COLUMN_COLOR+ " from " + CONTACTS_TABLE_NAME +" ORDER BY " + CONTACTS_COLUMN_TIME + " DESC", null);
        return res;

        /*
            how to use
          Cursor res = dbGrid.getAllData();
                if (res.getCount() == 0) {
                    Log.i("griddata", "Nothing found");
                } else {
                    while (res.moveToNext()) {
                        String sName = res.getString(0);
                    }
         */
    }

}
