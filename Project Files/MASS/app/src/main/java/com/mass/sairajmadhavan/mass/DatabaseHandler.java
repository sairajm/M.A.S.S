package com.mass.sairajmadhavan.mass;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sairaj Madhavan on 2/23/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Static variables for providing Database name and for creating the table.

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MASS";

    // Contacts table name
    private static final  String TABLE_PHARMACY="crocin";

    private static final String KEY_ID = "id";
   // private static final String KEY_MEDNAME = "medicine_name";
    private static final String KEY_PHARMACY_NAME = "pharmacy_name";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_AVAILABILITY = "availability";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_PHARMACY_TABLE = "CREATE TABLE " + TABLE_PHARMACY + " ("
                + KEY_ID + " INTEGER PRIMARY KEY ," +
                KEY_PHARMACY_NAME + " TEXT," +KEY_LATITUDE+" REAL,"+KEY_LONGITUDE+" REAL,"+KEY_AVAILABILITY+" INTEGER"+ ")";
        db.execSQL(CREATE_PHARMACY_TABLE);
    }



    // Adding new Pharmacy
    public void addPharmacy(Pharmacy pharmacy) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,pharmacy.getID());
        values.put(KEY_PHARMACY_NAME, pharmacy.getPharmaName()); // Pharmacy Name
        values.put(KEY_LATITUDE, pharmacy.getLatitude());
        values.put(KEY_LONGITUDE,pharmacy.getLongitude());
        values.put(KEY_AVAILABILITY, pharmacy.getAvailability());

        // Inserting Row
        db.insert(TABLE_PHARMACY, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Pharmacy getPharmacy(int availability) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Pharmacy pharmacy=new Pharmacy();
       // TABLE_PHARMACY=pharmacy.MedicineName();
        Cursor cursor = db.query(TABLE_PHARMACY, new String[] { KEY_ID,
                        KEY_PHARMACY_NAME, KEY_LATITUDE, KEY_LONGITUDE, KEY_AVAILABILITY }, KEY_AVAILABILITY + "=?",
                new String[] { String.valueOf(availability) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Pharmacy pharma = new Pharmacy(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Double.parseDouble(cursor.getString(2)) ,Double.parseDouble(cursor.getString(3)),(Integer.parseInt(cursor.getString(4))));
        // return pharmacy
        return pharma;
    }


    // Getting All Pharmacies
    public List<Pharmacy> getAllPharmacies() {
        List<Pharmacy> PharmacyList = new ArrayList<Pharmacy>();

      // Pharmacy pharmacy=new Pharmacy();
        //TABLE_PHARMACY=pharmacy.MedicineName();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PHARMACY+" where "+KEY_AVAILABILITY+"=1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pharmacy medicals = new Pharmacy();
                medicals.setID(Integer.parseInt(cursor.getString(0)));
                medicals.setPharmaName(cursor.getString(1));
                medicals.setLatitude(Double.parseDouble(cursor.getString(2)));
                medicals.setLongitude(Double.parseDouble(cursor.getString(3)));
                //medicals.setAvailability(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
               PharmacyList.add(medicals);
            } while (cursor.moveToNext());
        }

        // return pharmacy list
        return PharmacyList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHARMACY);

        // Create tables again
        onCreate(db);
    }



}
