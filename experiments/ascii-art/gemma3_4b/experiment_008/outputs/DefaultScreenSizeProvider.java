```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Provides the default screen size based on the system's screen resolution.
 * This class uses the default toolkit to retrieve the screen size, which is then
 * returned as a Dimension object.
 */
public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```