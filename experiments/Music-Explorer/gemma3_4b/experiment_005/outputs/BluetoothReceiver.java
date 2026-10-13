```java
package com.envisionate.musicinfolders;

import static com.envisionate.musicinfolders.Globals.properties;
import static com.envisionate.musicinfolders.Globals.theMusicExplorer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;

/**
 * This class extends BroadcastReceiver to listen for Bluetooth disconnections.
 * It handles the disconnection event by stopping the music explorer application
 * if configured to do so.
 */
public class BluetoothReceiver extends BroadcastReceiver {

    /**
     * Called when the broadcast receiver is started.
     * This method is called when the system broadcasts an intent
     * that this receiver is interested in.
     *
     * @param context    The application context.
     * @param intent     The intent that triggered the receiver.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the intent action is Bluetooth device ACL disconnected.
        if ( BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(intent.getAction()) ) {
            // Check if the exitOnBTDisconnect property is set to true.
            if ( properties.getExitOnBTDisconnect() ) {
                // If true, broadcast a stop and exit intent to stop the music explorer application.
                Util.broadcast(theMusicExplorer,"com.envisionate.musicinfolders.STOP_AND_EXIT");
            }
        }
    }
}
```