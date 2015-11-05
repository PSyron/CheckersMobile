package pl.checkersmobile.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Syron on 2015-11-05.
 */
public class HttpRequestHelper {

    public static final String TAG = HttpRequestHelper.class.getName();
    private static Context mCtx;
    private static HttpRequestHelper mInstance;
    private RequestQueue mRequestQueue;

    private HttpRequestHelper(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized HttpRequestHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpRequestHelper(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void postLogin() {
        String url = "www.wojtka_stara.pl";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logE(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        // Access the RequestQueue through your singleton class.
        addToRequestQueue(jsObjRequest);
    }
}
