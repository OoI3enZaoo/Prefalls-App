package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ben on 12/6/2560.
 */

public class DBAlertType extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AlertType.db";
    public static final String CONTACTS_TABLE_NAME = "alerttype";
    public static final String CONTACTS_COLUMN_ALERT_TYPE = "alert_type";
    public static final String CONTACTS_COLUMN_ALERT_NAME = "alert_name";

    public DBAlertType(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "(" +
                        CONTACTS_COLUMN_ALERT_TYPE + " text," +
                        CONTACTS_COLUMN_ALERT_NAME + " text" +
                        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }
    public String getAlertTypeName(int type) {
        String alertname = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CONTACTS_COLUMN_ALERT_NAME + " from " + CONTACTS_TABLE_NAME +" WHERE " + CONTACTS_COLUMN_ALERT_TYPE+"='"+type+"'", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            alertname = res.getString(res.getColumnIndex(CONTACTS_COLUMN_ALERT_NAME));
            res.moveToNext();
        }
        return alertname;
    }
    public boolean insertData(String type, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_ALERT_TYPE,type);
        contentValues.put(CONTACTS_COLUMN_ALERT_NAME,name);
        db.insert(CONTACTS_TABLE_NAME,null,contentValues);
        db.close();
        return true;
    }

}
