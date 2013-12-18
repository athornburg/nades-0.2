package com.cwat.util;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FriendsDataDistributor {

    private SQLiteDatabase database;
    private FriendsDatabase dbHelper;
    private String[] allColumns = { FriendsDatabase.COLUMN_USERNAME,
            FriendsDatabase.COLUMN_ID,FriendsDatabase.COLUMN_LOCATION };
    private String[] usernameColumn = {FriendsDatabase.COLUMN_USERNAME};

    public FriendsDataDistributor(Context context) {
        dbHelper = new FriendsDatabase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Friend createFriend(String username,String ID) {
        ContentValues values = new ContentValues();
        values.put(FriendsDatabase.COLUMN_ID,ID);
        values.put(FriendsDatabase.COLUMN_USERNAME, username);
        database.insert(FriendsDatabase.TABLE_FRIENDS, null,
                values);
        Cursor cursor = database.query(FriendsDatabase.TABLE_FRIENDS,
                allColumns, FriendsDatabase.COLUMN_USERNAME + " = '" + username+"'", null,
                null, null, null);
        cursor.moveToFirst();
        Friend newFriend = cursorToFriend(cursor);
        cursor.close();
        return newFriend;
    }

    public void deleteFriend(Friend friend) {
        String username = friend.getUsername();
        System.out.println("Friend deleted with id: " + username);
        database.delete(FriendsDatabase.TABLE_FRIENDS, FriendsDatabase.COLUMN_USERNAME
                + " = '" + username + "'", null);
    }

    public void addLocation(String location, String username){
        ContentValues values = new ContentValues();
        values.put(FriendsDatabase.COLUMN_LOCATION, location);
        database.update(FriendsDatabase.TABLE_FRIENDS, values, FriendsDatabase.COLUMN_USERNAME + "=' "
                +username+"'", null);
    }

    public List<Friend> getAllFriends() {
        List<Friend> friends = new ArrayList<Friend>();

        Cursor cursor = database.query(FriendsDatabase.TABLE_FRIENDS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Friend friend = cursorToFriend(cursor);
            friends.add(friend);
            cursor.moveToNext();
        }
        cursor.close();
        return friends;
    }

    public String getID(String username){
        String[] IDColumn = {FriendsDatabase.COLUMN_ID};
        Cursor cursor = database.query(FriendsDatabase.TABLE_FRIENDS,IDColumn,
                " = '" +username+"'",null,null,null,null);
        cursor.moveToFirst();
        String ID = null;
        while (!cursor.isAfterLast()) {
            ID = cursorToFriend(cursor).getID();
            cursor.moveToNext();
        }
        cursor.close();
        return ID;
    }

    public boolean containsFriend(String username){
        List<Friend> friends = new ArrayList<Friend>();
        Cursor cursor = database.query(FriendsDatabase.TABLE_FRIENDS,
                usernameColumn, FriendsDatabase.COLUMN_USERNAME + " = '" + username+"'", null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Friend friend = cursorToFriend(cursor);
            friends.add(friend);
            cursor.moveToNext();
        }
        cursor.close();
        if(!friends.isEmpty()){
            return true;
        }else return false;
    }

    private Friend cursorToFriend(Cursor cursor) {
        Friend friend = new Friend();
        friend.setUsername(cursor.getString(0));
        friend.setID(cursor.getString(1));
        return friend;
    }

}
