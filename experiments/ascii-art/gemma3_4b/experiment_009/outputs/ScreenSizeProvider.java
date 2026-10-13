```java
/**
 * Provides the screen size for the ASCII art application.
 * This interface defines a method to retrieve the screen dimensions,
 * allowing the application to dynamically adjust the ASCII art output
 * to fit the user's display.
 */
public interface ScreenSizeProvider {

    /**
     * Retrieves the screen size as a Dimension object.
     *
     * @return A Dimension object representing the width and height of the screen.
     *         The returned Dimension object represents the screen resolution.
     */
    Dimension getScreenSize();
}
```