```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;

/**
 * Provides a way to obtain the screen size.
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