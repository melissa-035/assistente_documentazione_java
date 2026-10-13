```java
package com.envisionate.musicinfolders;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class tracks the last launched activity in the application.
 * It extends {@link Application} and implements {@link Application.ActivityLifecycleCallbacks}
 * to receive callbacks for activity lifecycle events.
 */
public class TrackLaunchedActivities extends Application implements Application.ActivityLifecycleCallbacks {

    private String lastLaunchedActivity = null;

    /**
     * Constructs a new {@link TrackLaunchedActivities} instance.
     */
    public TrackLaunchedActivities() {
        super();
        Globals.trackLaunchedActivities = this;
        //registerActivityLifecycleCallbacks(this);
    }

    /**
     * Called when the application is first created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    /**
     * Returns the name of the last launched activity.
     *
     * @return The name of the last launched activity, or null if no activity has been launched.
     */
    public String getLastLaunchedActivity() {
        return lastLaunchedActivity;
    }

    /**
     * Called when an activity is created.
     *
     * @param activity The created activity.
     * @param savedInstanceState The saved instance state, or null if none.
     */
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Called when an activity is destroyed.
     *
     * @param activity The destroyed activity.
     */
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if ( ! ( null == lastLaunchedActivity ) )
            lastLaunchedActivity = null;
    }

    /**
     * Called when an activity is paused.
     *
     * @param activity The paused activity.
     */
    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Called when an activity is resumed.
     *
     * @param activity The resumed activity.
     */
    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Called when an activity is saved.
     *
     * @param activity The activity being saved.
     * @param outState The out state bundle.
     */
    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    /**
     * Called when an activity is started.
     *
     * @param activity The started activity.
     */
    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    /**
     * Called when an activity is stopped.
     *
     * @param activity The stopped activity.
     */
    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }
}
```