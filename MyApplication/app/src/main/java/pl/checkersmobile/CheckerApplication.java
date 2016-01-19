package pl.checkersmobile;

import android.app.Application;
import android.os.Handler;

import pl.checkersmobile.communication.HttpRequestHelper;
import pl.checkersmobile.utils.Logger;
import pl.checkersmobile.utils.PrefsHelper;

/**
 * Created by Syron on 2015-11-05.
 */
public class CheckerApplication extends Application {
    public static final String TAG = CheckerApplication.class.getName();
    private final static int INTERVAL = 3000;
    private static CheckerApplication sInstance;
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            //TODO ask database for playerList
            HttpRequestHelper.getInstance(CheckerApplication.this).getAcceptedInvitations
                    (PrefsHelper.getSessionToken(), PrefsHelper.getGameId());
            mHandler.postDelayed(mUpdateTimeTask, INTERVAL);
        }
    };

    public static synchronized CheckerApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.logE(TAG, "onLowMemory");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public void startLookingForPlayers() {
        mUpdateTimeTask.run();
    }

    public void stopLookingForPlayers() {
        if (mHandler != null && mUpdateTimeTask != null) {
            mHandler.removeCallbacks(mUpdateTimeTask);
        }
    }
}
