```java
package com.envisionate.musicinfolders.interfaces;

/**
 * Defines methods for interaction related to music playback within the music folder explorer.
 */
public interface IMusicExplorerPlay {

    /**
     * Notifies that a track has been successfully played.
     *
     * @param trackMS The Media Service (MS) identifier of the played track.
     */
    public void onPlayTrackMSChanged(long trackMS);

    /**
     * Notifies that the application is shutting down, allowing for any necessary cleanup or final actions.
     */
    public void playShuttingDown();

}
```