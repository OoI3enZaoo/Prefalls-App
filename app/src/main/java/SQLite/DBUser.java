package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ben on 10/6/2560.
 */

public class DBUser extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";
    public static final String CONTACTS_TABLE_NAME = "user";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_STATUS = "statusLogin";


    public DBUser(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "(" +
                        CONTACTS_COLUMN_NAME + " text," +
                        CONTACTS_COLUMN_STATUS + " integer" +
                        ")"
        );

        db.execSQL(
                "INSERT INTO " + CONTACTS_TABLE_NAME + " VALUES ('x',0)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }
    public boolean updateStatus(int status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_STATUS, status);
        db.update(CONTACTS_TABLE_NAME, contentValues, null, null);
        return true;
    }
    public int getStatus() {
        String place = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CONTACTS_COLUMN_STATUS + " from " + CONTACTS_TABLE_NAME, null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            place = res.getString(res.getColumnIndex(CONTACTS_COLUMN_STATUS));
            res.moveToNext();
        }

        return Integer.parseInt(place);
    }

    public boolean updateName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        db.update(CONTACTS_TABLE_NAME, contentValues, null, null);
        return true;
    }

    public String getName() {
        String place = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CONTACTS_COLUMN_NAME + " from " + CONTACTS_TABLE_NAME, null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            place = res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
            res.moveToNext();
        }

        return place;
    }

}