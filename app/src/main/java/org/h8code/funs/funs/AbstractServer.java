package org.h8code.funs.funs;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AbstractServer {
    final String TAG = "FUNS_ABSTRACT_SERVER";

    private static final String port = "34666";
    private static final String host = "localhost";

    public AbstractServer() {
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
                    Log.d(TAG, responseBody.toString() + " code " + statusCode);
                }
            });
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }

    }
}
