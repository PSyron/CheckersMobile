package pl.checkersmobile.communication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.checkersmobile.CheckerApplication;
import pl.checkersmobile.CheckersMove;
import pl.checkersmobile.Constants;
import pl.checkersmobile.communication.event.AcceptInvitationEvent;
import pl.checkersmobile.communication.event.BaseEvent;
import pl.checkersmobile.communication.event.GetActivePlayersEvent;
import pl.checkersmobile.communication.event.GetEnemyMovesEvent;
import pl.checkersmobile.communication.event.GetInvitesEvent;
import pl.checkersmobile.communication.event.OnAcceptedInviteEvent;
import pl.checkersmobile.gui.GameTableActivity;
import pl.checkersmobile.model.Invite;
import pl.checkersmobile.model.Move;
import pl.checkersmobile.model.User;
import pl.checkersmobile.utils.Logger;
import pl.checkersmobile.utils.PrefsHelper;
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
                        try {
                            if (response.has("Authorized") && response.getBoolean("Authorized")) {
                                EventBus.getDefault().post(new BaseEvent(ResponseStatus.SUCCESS));
                            } else {
                                EventBus.getDefault().post(new BaseEvent(ResponseStatus.FAILURE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        EventBus.getDefault().post(new BaseEvent(ResponseStatus.FAILURE));
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
                        try {
                            if (response.has("Session") && response.getString("Session").length() > 0
                                    ) {
                                PrefsHelper.setSessionToken(response.getString("Session"));
                                EventBus.getDefault().post(new BaseEvent(ResponseStatus.SUCCESS));
                            } else {
                                EventBus.getDefault().post(new BaseEvent(ResponseStatus.FAILURE));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventBus.getDefault().post(new BaseEvent(ResponseStatus.FAILURE));
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        EventBus.getDefault().post(new BaseEvent(ResponseStatus.FAILURE));
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
                        JSONArray array = null;

                        List<User> users = new ArrayList<>();
                        try {
                            array = response.getJSONArray("Users");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = (JSONObject) array.get(i);
                                users.add(new User(object.getString("name")));
                            }
                            EventBus.getDefault().post(new GetActivePlayersEvent(ResponseStatus
                                    .SUCCESS, users));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Logger.logD("checkActivePlayers", users.size() + "");
                        Logger.logD("checkActivePlayers", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE("checkActivePlayers", "" + error.getMessage());
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


    public void getInvites(String session) {
        String url = Constants.SERVICES_DOMAIN + Constants.TABLE_GET_INVITATIONS + session;
        Logger.logD(TAG, "getInvites " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("Successful")) {
                                List<Invite> invites = new ArrayList<>();
                                JSONArray array = response.getJSONArray("Invites");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = (JSONObject) array.get(i);
                                    Invite invite = new Invite(object.getString("dateString"), object
                                            .getString("idGame"), object.getString("playerName"));
                                    invites.add(invite);
                                }
                                EventBus.getDefault().post(new GetInvitesEvent(ResponseStatus
                                        .SUCCESS, invites));
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().post(new GetInvitesEvent(ResponseStatus
                                    .FAILURE, null));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        EventBus.getDefault().post(new GetInvitesEvent(ResponseStatus
                                .FAILURE, null));
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void refuseInvitation(String sessionToken, String idGame) {
        String url = Constants.SERVICES_DOMAIN + Constants.TABLE_REFUSE_INVITATION + sessionToken
                + "/" + idGame;
        Logger.logD(TAG, "refuseInvitation " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, "refuseInvitation success");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, "refuseInvitation error");
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void acceptInvitation(String sessionToken, String idGame) {
        String url = Constants.SERVICES_DOMAIN + Constants.TABLE_ACCEPT_INVITATION + sessionToken
                + "/" + idGame;
        Logger.logD(TAG, "acceptInvitation " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("Successful") && response.getBoolean("Successful")) {
                                Logger.logD(TAG, "acceptInvitation success");
                                EventBus.getDefault().post(new AcceptInvitationEvent(ResponseStatus
                                        .SUCCESS, response.getString("Message")));
                            } else {
                                EventBus.getDefault().post(new AcceptInvitationEvent(ResponseStatus.FAILURE));
                            }
                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, "acceptInvitation error");
                        EventBus.getDefault().post(new AcceptInvitationEvent(ResponseStatus.FAILURE));
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void createTable(String sessionToken) {
        String url = Constants.SERVICES_DOMAIN + Constants.TABLE_CREATE + sessionToken;
        Logger.logD(TAG, "createTable " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, "createTable success");
                        try {
                            PrefsHelper.setGameId(Integer.valueOf(response.getString("Message")) + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, "createTable error");
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void getAcceptedInvitations(String sessionToken) {
        String url = Constants.SERVICES_DOMAIN + Constants.GAME_GET_ENEMY + sessionToken;
        Logger.logD(TAG, "getAcceptedInvitations " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        try {
                            if (response.has("Games")) {
                                JSONArray array = response.getJSONArray("Games");
                                if (array.length() > 0) {
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = (JSONObject) array.get(i);
                                        if (object.has("idGame") && object.getString("idGame")
                                                .equals(PrefsHelper
                                                        .getGameId())) {
                                            CheckerApplication.getInstance().stopLookingForPlayers();
                                            EventBus.getDefault().post(new OnAcceptedInviteEvent
                                                    (ResponseStatus.SUCCESS, object.getString
                                                            ("Player2name")));
                                            Logger.logE(TAG, "getAcceptedInvitations success");
                                            break;
                                        }
                                    }
                                }
                            }
                            //Else szukaj dalej
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Logger.logE(TAG, "getAcceptedInvitations failure");
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        EventBus.getDefault().post(new OnAcceptedInviteEvent
                                (ResponseStatus.FAILURE));
                        Logger.logE(TAG, "getAcceptedInvitations error");
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void sendInvitations(String sessionToken, String name, String gameId) {
        String url = Constants.SERVICES_DOMAIN + Constants.TABLE_SEND_INVITATION + sessionToken
                + "/" + name + "/" + gameId;
        Logger.logD(TAG, "sendInvitations " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, "sendInvitations success");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, "sendInvitations error");
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void getLastMoves(String sessionToken, String gameId, String idLastMove) {
        String url = Constants.SERVICES_DOMAIN + Constants.GAME_GET_LAST_MOVES + sessionToken
                + "/" + gameId + "/" + idLastMove;
        Logger.logD(TAG, "getLastMoves " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("Successful")) {
                                List<Move> moves = new ArrayList<>();
                                JSONArray array = response.getJSONArray("Moves");
                                if (array.length() > 0) {
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = (JSONObject) array.get(i);
                                        Move move = new Move(object.getInt("idGame"), object.getInt
                                                ("postColumn") - 1, object.getInt("postRow") - 1, object
                                                .getInt("preRow") - 1, object.getInt
                                                ("preColumn") - 1, object.getInt("idMove"));
                                        moves.add(move);
                                    }
                                    EventBus.getDefault().post(new GetEnemyMovesEvent(ResponseStatus
                                            .SUCCESS, moves));
                                }
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().post(new GetEnemyMovesEvent(ResponseStatus
                                    .FAILURE));
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Logger.logE(TAG, "getLastMoves error " + error.getMessage());
                    }
                });
        addToRequestQueue(jsObjRequest);
    }


    public void finishMove(String sessionToken, String gameID) {
        String url = Constants.SERVICES_DOMAIN + Constants.GAME_FINISH_MOVE + sessionToken
                + "/" + gameID;
        Logger.logD(TAG, "finishMove " + url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, "finishMove success");
                        try {
                            int id = Integer.valueOf(response.getString
                                    ("Message"));
                            if (id > GameTableActivity.LAST_MOVE_ID)
                                GameTableActivity.LAST_MOVE_ID = id;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Logger.logE(TAG, "finishMove error");
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    public void makeMove(final String sessionToken, final String gameID, final CheckersMove move) {
        String url = Constants.SERVICES_DOMAIN + Constants.GAME_MAKE_MOVE + sessionToken
                + "/" + gameID + "/" + (move.fromRow + 1) + "/" + (move.fromCol + 1) + "/" + (move
                .toRow + 1) + "/" + (move.toCol + 1);
        Logger.logD(TAG, "makeMove " + url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.logD(TAG, "makeMove success");
                        if (response.has("Message")) {
                            try {
                                int id = Integer.valueOf(response.getString
                                        ("Message"));
                                if (id > GameTableActivity.LAST_MOVE_ID)
                                    GameTableActivity.LAST_MOVE_ID = id;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Logger.logE(TAG, "makeMove  error " + error.getMessage());
                        makeMove(sessionToken, gameID, move);
                    }
                });
        addToRequestQueue(jsObjRequest);
    }

    //endregion
}
