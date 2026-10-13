```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;

/**
 * Provides information about the screen size.
 * This interface is used to get the screen dimensions (width and height)
 * which are needed to generate ASCII art that fits the screen.
 */
public interface ScreenSizeProvider {

    /**
     * Returns the screen size as a Dimension object.
     * The Dimension object represents the width and height of the screen.
     *
     * @return A Dimension object representing the screen size.
     */
    Dimension getScreenSize();
}
```