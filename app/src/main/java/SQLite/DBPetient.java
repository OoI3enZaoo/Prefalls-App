package SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ben on 11/6/2560.
 */

public class DBPetient extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Petient.db";
    public static final String CONTACTS_TABLE_NAME = "petient_list";
    public static final String CONTACTS_COLUMN_SSSN = "SSSN";
    public static final String CONTACTS_COLUMN_firstname = "firstname";
    public static final String CONTACTS_COLUMN_lastname = "lastname";
    public static final String CONTACTS_COLUMN_nickname = "nickname";
    public static final String CONTACTS_COLUMN_sex = "sex";
    public static final String CONTACTS_COLUMN_birthday = "birthday";
    public static final String CONTACTS_COLUMN_address = "address";
    public static final String CONTACTS_COLUMN_imgPath = "imgPath";
    public static final String CONTACTS_COLUMN_weight = "weight";
    public static final String CONTACTS_COLUMN_height = "height";
    public static final String CONTACTS_COLUMN_apparent = "apparent";
    public static final String CONTACTS_COLUMN_diseases = "diseases";
    public static final String CONTACTS_COLUMN_medicine = "medicine";
    public static final String CONTACTS_COLUMN_AllergicMed = "AllergicMed";
    public static final String CONTACTS_COLUMN_AllergicFood = "AllergicFood";
    public static final String CONTACTS_COLUMN_doctorName = "doctorName";
    public static final String CONTACTS_COLUMN_doctorPhone = "doctorPhone";
    public static final String CONTACTS_COLUMN_hospitalName = "hospitalName";
    public static final String CONTACTS_COLUMN_cousinName1 = "cousinName1";
    public static final String CONTACTS_COLUMN_cousinPhone1 = "cousinPhone1";
    public static final String CONTACTS_COLUMN_cousinRelation1 = "cousinRelation1";
    public static final String CONTACTS_COLUMN_cousinName2 = "cousinName2";
    public static final String CONTACTS_COLUMN_cousinPhone2 = "cousinPhone2";
    public static final String CONTACTS_COLUMN_cousinRelation2 = "cousinRelation2";
    public static final String CONTACTS_COLUMN_cousinName3 = "cousinName3";
    public static final String CONTACTS_COLUMN_cousinPhone3 = "cousinPhone3";
    public static final String CONTACTS_COLUMN_cousinRelation3 = "cousinRelation3";
    public static final String CONTACTS_COLUMN_TYPE = "type";
    public static final String CONTACTS_COLUMN_COLOR = "color";
    public static final String CONTACTS_COLUMN_TSTART = "tstart";

    public DBPetient(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + CONTACTS_TABLE_NAME +
                        "(" +
                        CONTACTS_COLUMN_SSSN + " text," +
                        CONTACTS_COLUMN_firstname + " text," +
                        CONTACTS_COLUMN_lastname + " text," +
                        CONTACTS_COLUMN_nickname + " text," +
                        CONTACTS_COLUMN_sex + " text," +
                        CONTACTS_COLUMN_birthday + " text," +
                        CONTACTS_COLUMN_address + " text," +
                        CONTACTS_COLUMN_imgPath + " text," +
                        CONTACTS_COLUMN_weight + " text," +
                        CONTACTS_COLUMN_height + " text," +
                        CONTACTS_COLUMN_apparent + " text," +
                        CONTACTS_COLUMN_diseases + " text," +
                        CONTACTS_COLUMN_medicine + " text," +
                        CONTACTS_COLUMN_AllergicMed + " text," +
                        CONTACTS_COLUMN_AllergicFood + " text," +
                        CONTACTS_COLUMN_doctorName + " text," +
                        CONTACTS_COLUMN_doctorPhone + " text," +
                        CONTACTS_COLUMN_hospitalName + " text," +
                        CONTACTS_COLUMN_cousinName1 + " text," +
                        CONTACTS_COLUMN_cousinPhone1 + " text," +
                        CONTACTS_COLUMN_cousinRelation1 + " text," +
                        CONTACTS_COLUMN_cousinName2 + " text," +
                        CONTACTS_COLUMN_cousinPhone2 + " text," +
                        CONTACTS_COLUMN_cousinRelation2 + " text," +
                        CONTACTS_COLUMN_cousinName3 + " text," +
                        CONTACTS_COLUMN_cousinPhone3 + " text," +
                        CONTACTS_COLUMN_cousinRelation3 + " text, " +
                        CONTACTS_COLUMN_TYPE + " text,"+
                        CONTACTS_COLUMN_COLOR + " text, "+
                        CONTACTS_COLUMN_TSTART + " text "+

                        ")"
        );
    }
    public void drop() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String SSSN, String firstname, String lastname, String nickname, String sex, String birthday, String address, String imgPath, String weight, String height, String apparent, String diseases, String medicine, String AllergicMed, String AllergicFood, String doctorName, String doctorPhone, String hospitalName, String cousinName1, String cousinPhone1, String cousinRelation1, String cousinName2, String cousinPhone2, String cousinRelation2, String cousinName3, String cousinPhone3, String cousinRelation3,String type, String color,String tstart)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_SSSN, SSSN);//0
        contentValues.put(CONTACTS_COLUMN_firstname, firstname);//1
        contentValues.put(CONTACTS_COLUMN_lastname, lastname);//2
        contentValues.put(CONTACTS_COLUMN_nickname, nickname);
        contentValues.put(CONTACTS_COLUMN_sex, sex);
        contentValues.put(CONTACTS_COLUMN_birthday, birthday);
        contentValues.put(CONTACTS_COLUMN_address, address);
        contentValues.put(CONTACTS_COLUMN_imgPath, imgPath);
        contentValues.put(CONTACTS_COLUMN_weight, weight);
        contentValues.put(CONTACTS_COLUMN_height, height);
        contentValues.put(CONTACTS_COLUMN_apparent, apparent);//10
        contentValues.put(CONTACTS_COLUMN_diseases, diseases);
        contentValues.put(CONTACTS_COLUMN_medicine, medicine);
        contentValues.put(CONTACTS_COLUMN_AllergicMed, AllergicMed);
        contentValues.put(CONTACTS_COLUMN_AllergicFood, AllergicFood);
        contentValues.put(CONTACTS_COLUMN_doctorName, doctorName);//15
        contentValues.put(CONTACTS_COLUMN_doctorPhone, doctorPhone );
        contentValues.put(CONTACTS_COLUMN_hospitalName, hospitalName);
        contentValues.put(CONTACTS_COLUMN_cousinName1, cousinName1);
        contentValues.put(CONTACTS_COLUMN_cousinPhone1, cousinPhone1);
        contentValues.put(CONTACTS_COLUMN_cousinRelation1, cousinRelation1);//20
        contentValues.put(CONTACTS_COLUMN_cousinName2, cousinName2);
        contentValues.put(CONTACTS_COLUMN_cousinPhone2, cousinPhone2);
        contentValues.put(CONTACTS_COLUMN_cousinRelation2, cousinRelation2);
        contentValues.put(CONTACTS_COLUMN_cousinName3, cousinName3);
        contentValues.put(CONTACTS_COLUMN_cousinPhone3, cousinPhone3);//25
        contentValues.put(CONTACTS_COLUMN_cousinRelation3, cousinRelation3);//26
        contentValues.put(CONTACTS_COLUMN_TYPE,type);
        contentValues.put(CONTACTS_COLUMN_COLOR,color);
        contentValues.put(CONTACTS_COLUMN_TSTART,tstart);
        db.insert(CONTACTS_TABLE_NAME,null,contentValues);//30
        db.close();
        return true;
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACTS_TABLE_NAME, null);
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
    public Cursor getAllDataEach(String pid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + CONTACTS_TABLE_NAME +" WHERE " + CONTACTS_COLUMN_SSSN + " = " + "'"+pid+"'", null);
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


    public String getFullName(String pid) {
        String firstname = null;
        String lastname = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CONTACTS_COLUMN_firstname + " , "+CONTACTS_COLUMN_lastname+" from " + CONTACTS_TABLE_NAME +" WHERE " + CONTACTS_COLUMN_SSSN+"='"+pid+"'", null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            firstname = res.getString(res.getColumnIndex(CONTACTS_COLUMN_firstname));
            lastname = res.getString(res.getColumnIndex(CONTACTS_COLUMN_lastname));
            res.moveToNext();
        }

        return firstname + " " + lastname;
    }

    public String getNickName(String pid) {
        String nickname = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CONTACTS_COLUMN_nickname +" from " + CONTACTS_TABLE_NAME +" WHERE " + CONTACTS_COLUMN_SSSN+"='"+pid+"'", null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            nickname = res.getString(res.getColumnIndex(CONTACTS_COLUMN_nickname));
            res.moveToNext();
        }
        return nickname;
    }
    public String getImagepath(String pid) {
        String imgpath = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CONTACTS_COLUMN_imgPath +" from " + CONTACTS_TABLE_NAME +" WHERE " + CONTACTS_COLUMN_SSSN+"='"+pid+"'", null);

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            imgpath = res.getString(res.getColumnIndex(CONTACTS_COLUMN_imgPath));
            res.moveToNext();
        }

        return imgpath;
    }
    public void updateColorFromSSSN(String color, String sssn){

        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+CONTACTS_TABLE_NAME+" SET "+CONTACTS_COLUMN_COLOR+" = '"+color+"' WHERE "+CONTACTS_COLUMN_SSSN+" = '"+ sssn+"'";
        db.execSQL(strSQL);

    }
    public void updateTypeFromSSSN(int type , String sssn){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+CONTACTS_TABLE_NAME+" SET "+CONTACTS_COLUMN_TYPE+" = '"+type+"' WHERE "+CONTACTS_COLUMN_SSSN+" = '"+ sssn+"'";
        db.execSQL(strSQL);
    }
    public void updateTstartFromSSSN(String tstart , String sssn){
        SQLiteDatabase db = this.getWritableDatabase();
        String strSQL = "UPDATE "+CONTACTS_TABLE_NAME+" SET "+CONTACTS_COLUMN_TSTART+" = '"+tstart+"' WHERE "+CONTACTS_COLUMN_SSSN+" = '"+ sssn+"'";
        db.execSQL(strSQL);
    }



}
