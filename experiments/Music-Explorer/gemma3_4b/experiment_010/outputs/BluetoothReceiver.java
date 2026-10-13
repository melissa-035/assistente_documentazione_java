```java
package com.envisionate.musicinfolders;

import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This class extends {@link BroadcastReceiver} to listen for Bluetooth disconnections.
 * When a Bluetooth disconnection occurs, it checks a configuration property
 * and, if enabled, sends a stop and exit signal through the Music Explorer.
 */
public class BluetoothReceiver extends BroadcastReceiver {

    /**
     * Called when the broadcast receiver is invoked.
     *
     * @param context   The context.
     * @param intent    The intent that triggered the receiver.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the intent action is Bluetooth device ACL disconnection.
        if ( BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction()) ) {
            // Check if the configuration property to exit on Bluetooth disconnection is enabled.
            if ( properties.getExitOnBTDisconnect() ) {
                // If enabled, broadcast a stop and exit signal through the Music Explorer.
                Util.broadcast(theMusicExplorer, "com.envisionate.musicinfolders.STOP_AND_EXIT");
            }
        }
    }
}
```