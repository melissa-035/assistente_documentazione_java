```java
package com.envisionate.musicinfolders;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TrackLaunchedActivities extends Application implements Application.ActivityLifecycleCallbacks {

    private String lastLaunchedActivity = null;

    public TrackLaunchedActivities() {
        super();
        Globals.trackLaunchedActivities = this;
        //registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    /**
     * Returns the name of the last launched activity.
     * This class maintains a record of the last activity that was launched.
     * This is useful for tracking activity lifecycles.
     *
     * @return the name of the last launched activity, or null if no activity has been launched.
     */
    public String getLastLaunchedActivity() {
        return lastLaunchedActivity;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    /**
     * Resets the last launched activity to null.
     * This is called when an activity is destroyed.
     *
     * @param activity the destroyed activity.
     */
    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if ( ! ( null == lastLaunchedActivity ) )
            lastLaunchedActivity = null;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        lastLaunchedActivity = activity.getLocalClassName();
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }


}
```