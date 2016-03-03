package org.h8code.funs.funs;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

public class FunsProvider extends ContentProvider {
    final String TAG = "FUNS_PROVIDER";

    private static final UriMatcher uri_matcher = new UriMatcher(0);
    private DBHelper db_helper;
    private SQLiteDatabase db;

    public static final String uri_base = "org.h8code.funs.funs";
    private static final String DBNAME = "funs";


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        db_helper = new DBHelper(getContext());
        uri_matcher.addURI(uri_base, "schedules", 1);
        uri_matcher.addURI(uri_base, "schedules/*", 2);
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
                Log.d(TAG, "schedules/" + uri.getLastPathSegment());
                break;
            default:
                Log.d(TAG,"Bad query: " + uri.toString());
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert");
        db = db_helper.getWritableDatabase();
        return null;
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
        DBHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.execSQL();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
