package org.h8code.funs.funs;

import android.app.Service;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class AbstractServer {
    final String TAG = "FUNS_ABSTRACT_SERVER";

    private static final String port = "34666";
    private static final String host = "localhost";

    private final Service service;
    private InsertSchedules schedulesTask;

    private ContentResolver getContentResolver() {
        return service.getContentResolver();
    }

    public AbstractServer(Service service__) {
        service = service__;
    }

    public void login(String login, String password) {
        JSONObject data = new JSONObject();
        try {
            data.put("login", login);
            data.put("password", password);
            RestClient.login(data, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d(TAG, responseBody.toString() + " code " + statusCode);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG, responseBody.toString() + " code " + statusCode);
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

    }

    public void schedules() {
        RestClient.schedules(new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (schedulesTask != null && schedulesTask.getStatus() == AsyncTask.Status.RUNNING) {
                    try {
                        schedulesTask.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                schedulesTask = new InsertSchedules(getContentResolver());
                String answer = new String(responseBody, StandardCharsets.UTF_8);
                schedulesTask.execute(answer);
                Log.d(TAG, answer + " code " + statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, responseBody.toString() + " code " + statusCode);
            }
        });
    }

    class InsertSchedules extends AsyncTask<String,Void,Void> {
        private final static String TAG = "FUNS_INSERT_SCHEDULES";

        private final ContentResolver cr;

        public InsertSchedules(ContentResolver cr__) {
            cr = cr__;
        }
        @Override
        protected Void doInBackground(String... params) {
            for (String data: params) {
                try {
                    JSONArray array = new JSONObject(data).getJSONArray("schedules");
                    for (int i = 0; i < array.length(); i++) {
                        ContentValues v = new ContentValues();
                        JSONObject o = (JSONObject) array.get(i);
                        v.put(FunsDBScheme.SCHEDULES_ID,
                                o.getJSONObject("_id").getString("$oid"));
                        put(v, FunsDBScheme.SCHEDULES_NAME, o);
                        put(v, FunsDBScheme.SCHEDULES_INSTITUTION, o);
                        v.put(FunsDBScheme.SCHEDULES_GROUP,
                                o.getString("group"));
                        put(v, FunsDBScheme.SCHEDULES_PERIOD_START, o);
                        put(v, FunsDBScheme.SCHEDULES_PERIOD_END, o);
                        put(v, FunsDBScheme.SCHEDULES_CREATOR, o);

                        cr.insert(FunsContentProviderUri.SCHEDULES_URI, v);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        private void put(ContentValues v, String s, JSONObject o) throws JSONException {
            v.put(s, o.getString(s));
        }
    }
}
