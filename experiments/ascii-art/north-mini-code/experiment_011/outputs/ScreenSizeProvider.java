FILE: src\main\java\pl\mgodyn\asciiart\utils\ScreenSizeProvider.java

```java
package pl.mgodyn.asciiart.utils;

import java.awt.Dimension;

/**
 * Provides screen size information. This interface abstracts the retrieval of the
 * current screen dimensions for the application.
 */
public interface ScreenSizeProvider {

    /**
     * Returns the size of the screen.
     *
     * @return a Dimension object representing the width and height of the screen.
     * @see Dimension
     */
    Dimension getScreenSize();
}
```