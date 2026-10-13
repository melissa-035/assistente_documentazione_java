```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;

/**
 * Provides screen size information for the application.
 * This interface defines a method to retrieve the dimensions of the screen.
 */
public interface ScreenSizeProvider {

    /**
     * Retrieves the screen dimensions.
     *
     * @return the screen size as a {@link Dimension} object, representing width and height in pixels.
     */
    Dimension getScreenSize();
}
```