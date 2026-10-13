```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;

/**
 * Utility interface for retrieving screen dimensions.
 * This interface abstracts the platform-specific logic for obtaining the current screen size.
 */
public interface ScreenSizeProvider {

    /**
     * Retrieves the screen size (width and height) of the display.
     *
     * @return The screen size as a {@link Dimension} object, where width and height are in pixels.
     */
    Dimension getScreenSize();
}
```