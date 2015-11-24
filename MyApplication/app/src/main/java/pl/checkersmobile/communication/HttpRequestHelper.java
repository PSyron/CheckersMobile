package pl.checkersmobile.communication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import pl.checkersmobile.Constants;
import pl.checkersmobile.utils.Logger;
import pl.checkersmobile.utils.Utils;

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

    //region "login"
    public void connect(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.LOGIN_CONNECT + session;
        Logger.logD(TAG, "connect " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    public void disconnect(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.LOGIN_DISCONNECT + session;
        Logger.logD(TAG, "disconnect " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void register(String name, String login, String password) {
        String url = Constants.SERVICES_DOMAIN + Constants.LOGIN_REGISTER + name + "/" + login + "/" + Utils.SHA1(password);
        Logger.logD(TAG, "register " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Prefs save Login
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void login(String login, String password) {
        String url = Constants.SERVICES_DOMAIN + Constants.LOGIN_LOGIN + login + "/" + Utils.SHA1(password);
        Logger.logD(TAG, "login " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO Prefs save Login
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void logout(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.LOGIN_LOGOUT + session;
        Logger.logD(TAG, "logout " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    //endregion
    //region "community"
    public void checkActivePlayers(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.COMMUNITY_CHECK_ACTIVE_PLAYERS + session;
        Logger.logD(TAG, "checkActivePlayers " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    public void checkActiveFriends(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.COMMUNITY_CHECK_ACTIVE_FRIENDS + session;
        Logger.logD(TAG, "checkActiveFriends " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    public void getFriends(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.COMMUNITY_GET_FRIENDS + session;
        Logger.logD(TAG, "getFriends " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    public void addFriend(String session, String friendName) {
        String url = Constants.SERVICES_DOMAIN + Constants.COMMUNITY_ADD_FRIEND + session + "/" + friendName;
        Logger.logD(TAG, "addFriend " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    public void removeFriend(String session, String friendName) {
        String url = Constants.SERVICES_DOMAIN + Constants.COMMUNITY_REMOVE_FRIEND + session + "/" + friendName;
        Logger.logD(TAG, "removeFriend " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, error.getMessage());
                    }
                });

        addToRequestQueue(jsObjRequest);
    }
    //endregion
}
