```java
package pl.mgodyn.asciiart.utils;

/**
 * Provides screen size information using the platform's Abstract Window Toolkit (AWT).
 * <p>
 * This class implements the {@link ScreenSizeProvider} interface to retrieve the dimensions
 * of the current display device. It delegates the screen size detection to the default
 * {@link java.awt.Toolkit}.
 * </p>
 *
 * @author mgodyn
 * @see ScreenSizeProvider
 */
public class DefaultScreenSizeProvider implements ScreenSizeProvider {

    /**
     * Retrieves the current screen size (dimensions) of the display device.
     * <p>
     * This method uses the system default toolkit to determine the width and height
     * of the primary screen in pixels.
     * </p>
     *
     * @return a {@link java.awt.Dimension} object representing the screen's width and height.
     */
    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
```