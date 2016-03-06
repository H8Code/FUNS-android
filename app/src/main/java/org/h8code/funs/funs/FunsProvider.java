package org.h8code.funs.funs;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import static org.h8code.funs.funs.FunsContentProviderUri.*;
import static org.h8code.funs.funs.FunsDBScheme.*;

public class FunsProvider extends ContentProvider {
    private static final UriMatcher uri_matcher;

    static {
        uri_matcher = new UriMatcher(0);
        uri_matcher.addURI(AUTHORITY, SCHEDULES_PATH, 1);
        uri_matcher.addURI(AUTHORITY, SCHEDULES_PATH + "/*", 2);
    }

    final String TAG = "FUNS_PROVIDER";
    private DBHelper db_helper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        db_helper = new DBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match__ = uri_matcher.match(uri);
        if (match__ == UriMatcher.NO_MATCH)
            return null;

        db = db_helper.getReadableDatabase();
        switch (match__) {
            case 1:
                Log.d(TAG, "schedules");
                break;
            case 2:
                String id_ = uri.getLastPathSegment();
                Log.d(TAG, "schedules/" + id_);
                if (TextUtils.isEmpty(selection)) {
                    selection = SCHEDULES_ID + " = " + id_;
                } else {
                    selection = selection + " AND " + SCHEDULES_ID + " = " + id_;
                }
                break;
            default:
                Log.d(TAG, "Bad query: " + uri.toString());
        }
        Cursor cursor = db.query(SCHEDULES_TABLE, projection, selection, selectionArgs,
                null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                SCHEDULES_URI);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert");
        int match__ = uri_matcher.match(uri);
        if (match__ == UriMatcher.NO_MATCH)
            throw new IllegalArgumentException("Wrong URI: " + uri);
        db = db_helper.getWritableDatabase();
        db.beginTransaction();
        Uri result_uri;
        try {
            long row_id = db.insert(SCHEDULES_TABLE, null, values);
            db.setTransactionSuccessful();
            result_uri = ContentUris.withAppendedId(SCHEDULES_URI, row_id);
            getContext().getContentResolver().notifyChange(result_uri, null);
        } finally {
            db.endTransaction();
        }
        return result_uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update");
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete");
        return 0;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    protected static final class DBHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "funs";
        private static final int DB_VERSION = 1;

        private static final String type__ = " text, ";
        private static final String DB_SCHEDULES_TABLE_CREATE =
                "create table " + SCHEDULES_TABLE + "("
                        + SCHEDULES_ID + " text primary key, "
                        + SCHEDULES_NAME + type__
                        + SCHEDULES_INSTITUTION + type__
                        + SCHEDULES_GROUP + type__
                        + SCHEDULES_PERIOD_START + type__
                        + SCHEDULES_PERIOD_END + type__
                        + SCHEDULES_CREATOR + type__
                        + SCHEDULES_SUBJECTS + type__
                        + SCHEDULES_ODD + type__
                        + SCHEDULES_EVEN + type__
                        + SCHEDULES_UNUSUAL + " text);";

        DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_SCHEDULES_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
