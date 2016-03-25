package org.h8code.funs.funs;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class RestClient {
    static final String TAG = "REST_CLIENT";
    private static AsyncHttpClient client = new AsyncHttpClient();

    private static final String BASE_URL = "http://192.168.1.102:34666/api/";
    private static final String SCHEDULES_PATH = "schedules";
    private static final String USERS_PATH = "users";
    private static final String AUTH_PATH = "auth";

    public static void login(JSONObject data, AsyncHttpResponseHandler responseHandler) {
        StringEntity entity = null;
        try {
            entity = new StringEntity(data.toString());
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
//        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        client.post(null, abs_url(AUTH_PATH), entity, "application/json", responseHandler);
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
