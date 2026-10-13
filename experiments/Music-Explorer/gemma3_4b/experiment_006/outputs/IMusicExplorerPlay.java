```java
package com.envisionate.musicinfolders.interfaces;

/**
 * Defines a contract for components that can play music tracks.
 * This interface provides methods for handling track changes and shutdown events.
 */
public interface IMusicExplorerPlay {

    /**
     * Notifies the component that a music track has been changed.
     *
     * @param trackMS The media seek time (milliseconds) of the new track.
     */
    public void onPlayTrackMSChanged(long trackMS);

    /**
     * Notifies the component that the music explorer is shutting down.
     */
    public void playShuttingDown();

}
```