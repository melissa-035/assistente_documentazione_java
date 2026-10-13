```java
package com.envisionate.carservice.screen;

import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.CarText;
import androidx.car.app.model.Header;
import androidx.car.app.model.MessageTemplate;
import androidx.car.app.model.Template;

import com.envisionate.musicinfolders.Util;

import org.jspecify.annotations.NonNull;

/**
 * Represents a screen displaying a message.
 *
 * This class creates a CarApp screen that displays a single message.
 * It provides a basic template with a title and the message content.
 */
public class MessageScreen extends Screen {

    private String theMessage = null;
    private Runnable onGetTemplateRunnable = null;
    private static MessageScreen currentMessageScreen = null;

    /**
     * Constructs a new MessageScreen.
     *
     * @param carContext The CarContext for retrieving resources and performing actions.
     * @param theMessageId The ID of the string resource to display as the message.
     * @param run A Runnable to execute when the template is obtained.  Allows for
     *           dynamic setup of the template.
     */
    public MessageScreen(@NonNull CarContext carContext, int theMessageId, Runnable run) {
        super(carContext);
        this.theMessage = carContext.getString(theMessageId);
        this.onGetTemplateRunnable = run;
        this.currentMessageScreen = this;
    }

    /**
     * Returns a template containing the message.
     *
     * This method is called by the CarApp to create the template for this screen.
     * It constructs a CarText object with the message content and wraps it in
     * a MessageTemplate. The template also includes a header with the title
     * "Note".
     *
     * @return A MessageTemplate containing the message.
     */
    @Override
    public @NonNull Template onGetTemplate() {
        if (! (null == onGetTemplateRunnable)) {
            onGetTemplateRunnable.run();
        }
        CarText theMessage = new CarText.Builder(this.theMessage).build();
        return new MessageTemplate.Builder(theMessage).setHeader(new Header.Builder().setTitle(new CarText.Builder("Note").build()).build()).build();
    }

    /**
     * Returns the currently active MessageScreen instance.
     *
     * @return The MessageScreen instance, or null if none exists.
     */
    public static MessageScreen getCurrentMessageScreen() {
        return currentMessageScreen;
    }
}
```