package com.example.deepak.bitsxchange;

/**
 * Created by Deepak on 14-02-2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProductsDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PID = "pid";

    private static final String TAG = "ProductsDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "BitsXchange";
    private static final String SQLITE_TABLE = "Product";
    private static final String SQLITE_TABLE_SIZE = "size";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_NAME + "," +
                    KEY_PRICE + "," +
                    KEY_PID + "," +
                    " UNIQUE (" + KEY_PID + "));";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public ProductsDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public ProductsDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createProduct(String name, String price,
                              String pid) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_PRICE, price);
        initialValues.put(KEY_PID, pid);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllProducts() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchProductsByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null || inputText.length() == 0) {
            mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_NAME, KEY_PRICE, KEY_PID},
                    null, null, null, null, null);

        } else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_NAME, KEY_PRICE, KEY_PID},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllProducts() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID,
                        KEY_NAME, KEY_PRICE, KEY_PID},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeProducts() {

        createProduct("Guitar", "5000", "245");
        createProduct("Book", "500", "255");
        createProduct("Speakers", "1000", "265");
        createProduct("Guitar", "5000", "27");
        createProduct("Book", "500", "258");
        createProduct("Speakers", "1000", "269");
        createProduct("Guitar", "5000", "240");
        createProduct("Book", "500", "252");
        createProduct("Speakers", "1000", "267");

    }

}