```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Provides a default screen size based on the operating system's screen resolution.
 * This class encapsulates the logic for retrieving the screen size using
 * Toolkit.getDefaultToolkit().getScreenSize().
 */
public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    /**
     * Retrieves the screen size in terms of a Dimension object.
     * 
     * @return A Dimension object representing the screen size.  The width and height
     *         are obtained using Toolkit.getDefaultToolkit().getScreenSize().
     */
    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```