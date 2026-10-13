```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    @Override
    /**
     * Returns the default screen size for the system.
     * This provides a convenient way to obtain the screen dimensions
     * without needing to query the operating system.
     *
     * @return a Dimension object representing the default screen size.
     */
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```