package pl.checkersmobile;

import android.app.Application;

import pl.checkersmobile.utils.Logger;

/**
 * Created by Syron on 2015-11-05.
 */
public class CheckerApplication extends Application {
    public static final String TAG = CheckerApplication.class.getName();
    private static CheckerApplication sInstance;

    public static synchronized CheckerApplication getInstance() {
        if (sInstance == null) {
            sInstance = new CheckerApplication();
        }
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
    }
}
