```java
package com.envisionate.musicinfolders;

import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothReceiver extends BroadcastReceiver {

    /**
     * This BroadcastReceiver listens for Bluetooth disconnections.
     * When a disconnection occurs and the configured setting allows it,
     * it broadcasts a stop and exit intent to terminate the application.
     *
     * @param context The Context object for accessing resources and services.
     * @param intent  The Intent object containing the action and data
     *                of the received broadcast.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if ( BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction()) ) {
            if ( properties.getExitOnBTDisconnect() )
                Util.broadcast(theMusicExplorer,"com.envisionate.musicinfolders.STOP_AND_EXIT");
        }
    }
}
```