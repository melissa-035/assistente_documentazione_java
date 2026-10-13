```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Provides the default screen size by utilizing the system's {@link java.awt.Toolkit}.
 * <p>
 * This implementation returns the physical dimensions of the primary screen.
 * </p>
 *
 * @see pl.mgodyn.asciiart.utils.ScreenSizeProvider
 */
public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    /**
     * Retrieves the current screen size.
     *
     * @return A {@link java.awt.Dimension} object representing the width and height of the screen.
     */
    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```