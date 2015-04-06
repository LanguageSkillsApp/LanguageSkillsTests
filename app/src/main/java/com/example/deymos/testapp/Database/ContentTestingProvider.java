package com.example.deymos.testapp.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLException;

public class ContentTestingProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.deymos.testapp.ContentTestingProvider/");
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db = new TestingDatabase(getContext()).getWritableDatabase();
        return (db != null);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String orderBy;

        String tableName = uri.toString().substring(CONTENT_URI.toString().length(), uri.toString().length());

        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = null;
        } else {
            orderBy = sortOrder;
        }

        Cursor c = db.query(tableName, projection, selection, selectionArgs, null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ContentValues contentValues = new ContentValues(values);
        String tableName = uri.toString().substring(CONTENT_URI.toString().length(), uri.toString().length());
        long rowId = db.insertWithOnConflict(tableName, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        if (rowId <= 0) {
            try {
                throw new SQLException("Failed to insert row into " + uri);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            Uri url = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(url, null);

            return url;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tableName = uri.toString().substring(0, CONTENT_URI.toString().length());
        int retVal = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return retVal;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName = uri.toString().substring(0, CONTENT_URI.toString().length());
        int retVal = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return retVal;
    }
}