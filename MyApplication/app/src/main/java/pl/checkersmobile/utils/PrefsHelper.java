package pl.checkersmobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

import pl.checkersmobile.CheckerApplication;

/**
 * Created by paulc_000 on 2015-11-24.
 */
public class PrefsHelper {
    public static final String SHARED_PREFERENCES = "CheckersPrefs";
    private static final SharedPreferences sSharedPreferences = CheckerApplication.getInstance().getSharedPreferences(
            SHARED_PREFERENCES, Context.MODE_PRIVATE);

    private static final String SHARED_SESSION = SHARED_PREFERENCES + ".Session";
    private static final String SHARED_LOGIN = SHARED_PREFERENCES + ".Login";
    private static final String SHARED_GAME_ID = SHARED_PREFERENCES + ".GameId";

    public static String getSessionToken() {
        return sSharedPreferences.getString(SHARED_SESSION, "");
    }

    public static void setSessionToken(String sessionToken) {
        sSharedPreferences.edit().putString(SHARED_SESSION, sessionToken).apply();
    }

    public static String getUserLogin() {
        return sSharedPreferences.getString(SHARED_LOGIN, "");
    }

    public static void setUserLogin(String userLogin) {
        sSharedPreferences.edit().putString(SHARED_LOGIN, userLogin).apply();
    }

    public static String getGameId() {
        return sSharedPreferences.getString(SHARED_GAME_ID, "");
    }

    public static void setGameId(String gameId) {
        sSharedPreferences.edit().putString(SHARED_GAME_ID, gameId).apply();
        Logger.logD("setGameId", gameId);
    }

}
