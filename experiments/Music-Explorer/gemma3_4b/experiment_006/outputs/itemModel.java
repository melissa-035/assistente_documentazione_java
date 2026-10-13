```java
package com.envisionate.musicinfolders;

import android.view.View;

public class itemModel {

    private String name;
    private String documentFileUri = null;
    private View itemImageView = null;
    private View itemTextView = null;

    public itemModel(String name, String fileUri) {
        this.name = name;
        itemImageView = null;
        itemTextView = null;
        documentFileUri = fileUri;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name the name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the file URI of the item.
     *
     * @return the file URI of the item.
     */
    public String getURi() {
        return documentFileUri;
    }

    /**
     * Sets the image view for the item.
     *
     * @param view the view to set for the item.
     */
    public void setImageView(View view) {
        this.itemImageView = view;
    }

    /**
     * Returns the image view for the item.
     *
     * @return the image view for the item.
     */
    public View getImageView() {
        return itemImageView;
    }

    /**
     * Sets the text view for the item.
     *
     * @param view the view to set for the item.
     */
    public void setTextView(View view) {
        this.itemTextView = view;
    }

    /**
     * Returns the text view for the item.
     *
     * @return the text view for the item.
     */
    public View getTextView() {
        return itemTextView;
    }

}
```