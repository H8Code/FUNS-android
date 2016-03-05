package org.h8code.funs.funs;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RestClient {
    static final String TAG = "REST_CLIENT";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final String BASE_URL = "http://192.168.0.108:34666/api/";
    private static final String SCHEDULES_PATH = "schedules";
    private static final String USERS_PATH = "users";

    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d(TAG, "get " + abs_url(url));
        client.get(abs_url(url), params, responseHandler);
    }

    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d(TAG, "post " + abs_url(url));
        client.post(abs_url(url), params, responseHandler);
    }

    private static String abs_url(String relative_url) {
        return BASE_URL + relative_url;
    }

    private static final class ResponseHandlerSchedule extends JsonHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.d(TAG, response.toString());
        }
    }
}
