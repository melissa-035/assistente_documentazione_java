```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    @Override
    /**
     * Returns the default screen size based on the operating system.
     * This provides a reasonable default for generating ASCII art.
     *
     * @return A Dimension object representing the default screen size.
     */
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```