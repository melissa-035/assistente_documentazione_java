```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Provides a default implementation of ScreenSizeProvider that returns
 * the screen size of the primary display.
 *
 * @see ScreenSizeProvider
 */
public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```