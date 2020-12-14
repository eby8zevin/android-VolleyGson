package com.ahmadabuhasan.volleygson;

import android.app.Application;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/*
    jika sudah membuat ini jangan lupa tambahkan di manifest
    <application
        android:name=".AppController" <============ yang ini ya!
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        ....
    </application>
 */

/*
 * Created by Ahmad Abu Hasan on 14/12/2020
 */

public class AppController extends Application {

    private String TAG = AppController.class.getSimpleName();

    private static AppController mInstance;
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public <T> void addToQueue(@NonNull Request<T> request, @NonNull String tag) {
        if (tag.equals("")) {
            request.setTag(TAG);
        } else {
            request.setTag(tag);
        }

        request.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        getRequestQueueeue().add(request);
    }

    private RequestQueue getRequestQueueeue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

}
