package org.h8code.funs.funs;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FunsService extends Service {
    final String TAG = "FUNS_SERVICE";

    public final class Request {
        public static final String REQUEST = "type";
        public static final int NONE = -1;
        public static final int LOGIN = 1;
        public static final int GET_SCHEDULES = 2;
        public static final int GET_SCHEDULE = 3;
        public static final int GET_USERS = 4;

    }

    private static AbstractServer server;

    public FunsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service onCreate");
        server = new AbstractServer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");
        handleIntent(intent);
        return START_STICKY;//super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Service onBind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleIntent(Intent intent) {
        switch (intent.getIntExtra(Request.REQUEST, Request.NONE)) {
            case Request.NONE:
                return;
            case Request.LOGIN:
                server.login(
                        intent.getStringExtra("login"),
                        intent.getStringExtra("password")
                );
                break;
            default:
                Log.e(TAG, "Unexpected request!", new UnknownError());
        }
    }
}