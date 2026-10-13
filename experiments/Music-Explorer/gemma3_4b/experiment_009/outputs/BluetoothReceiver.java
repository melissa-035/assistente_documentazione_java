```java
package com.envisionate.musicinfolders;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

public class BluetoothReceiver extends BroadcastReceiver {

    /**
     * This BroadcastReceiver listens for Bluetooth device connection disconnections.
     * When an ACL disconnection event occurs, it checks a property to determine
     * if the application should stop and exit. If so, it broadcasts an intent
     * to trigger the stop and exit sequence.
     *
     * @param context   The Context object for the receiver.
     * @param intent    The Intent object containing the broadcast data.
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