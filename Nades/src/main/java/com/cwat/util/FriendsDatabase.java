package com.cwat.util;

/**
 * Created by alexthornburg on 11/13/13.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FriendsDatabase extends SQLiteOpenHelper {

    public static final String TABLE_FRIENDS = "friends_table";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOCATION = "location";

    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_FRIENDS + "(" + COLUMN_USERNAME
            + " TEXT primary key, " + COLUMN_ID
            + " TEXT, "+COLUMN_LOCATION+" TEXT);";

    public FriendsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FriendsDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(db);
    }
}
