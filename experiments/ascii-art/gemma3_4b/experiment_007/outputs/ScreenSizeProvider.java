```java
/**
 * Provides the screen size for generating ASCII art.
 * This interface allows different implementations to determine the screen size
 * based on the user's environment.
 */
public interface ScreenSizeProvider {

    /**
     * Returns the screen size as a Dimension object.
     * The Dimension object represents the width and height of the screen.
     * @return a Dimension object representing the screen size.
     */
    Dimension getScreenSize();
}
```