```java
package com.envisionate.musicinfolders;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class tracks the last launched activity within the application.
 * It extends Application to listen to Activity lifecycle callbacks and
 * record the name of the activity when it's created, resumed, or paused.
 */
public class TrackLaunchedActivities extends Application implements Application.ActivityLifecycleCallbacks {

    /**
     * Stores the name of the last launched activity.
     */
    private String lastLaunchedActivity = null;

    /**
     * Constructs a new instance of TrackLaunchedActivities.
     */
    public TrackLaunchedActivities() {
        super();
        Globals.trackLaunchedActivities = this;
        //registerActivityLifecycleCallbacks(this); //This line is commented out, but is intended to register this class as an ActivityLifecycleCallbacks
    }

    /**
     * Called when the application is being created.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    /**
     * Returns the name of the last launched activity.
     *
     * @return The name of the last launched activity, or null if none has been launched.
     */
    public String getLastLaunchedActivity() {
        return lastLaunchedActivity;
    }

    /**
     * Called when an activity is being created.
     *
     * @param activity       The activity being created.
     * @param savedInstanceState The saved instance state of the activity, or null if there is none.
     */
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Called when an activity is being destroyed.
     *
     * @param activity The activity being destroyed.
     */
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if ( ! ( null == lastLaunchedActivity ) )
            lastLaunchedActivity = null;
    }

    /**
     * Called when an activity is being paused.
     *
     * @param activity The activity being paused.
     */
    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Called when an activity is being resumed.
     *
     * @param activity The activity being resumed.
     */
    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Called when an activity is being saved its instance state.
     *
     * @param activity       The activity being saved.
     * @param outState       The bundle to save the instance state in.
     */
    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    /**
     * Called when an activity is being started.
     *
     * @param activity The activity being started.
     */
    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    /**
     * Called when an activity is being stopped.
     *
     * @param activity The activity being stopped.
     */
    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }
}
```