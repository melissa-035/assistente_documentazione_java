```java
import java.util.*;

public class MySet extends AbstractSet {
    private HashMap<Object, Object> mapObject = null;
    private static final Object temp = new Object();

    public MySet() {
        mapObject = new HashMap<>();
    }

    /**
     * Adds a new element to the set.
     *
     * @param object the element to add to the set.
     * @return {@code true} if the set did not already contain the specified element
     *         before adding it, {@code false} otherwise.
     */
    public boolean add(Object object) {
        return mapObject.put(object, temp)==null;
    }

    /**
     * Returns an iterator for this set.
     *
     * @return an iterator for this set.
     */
    @Override
    public Iterator iterator() {
        return mapObject.keySet().iterator();
    }

    /**
     * Returns the number of elements in this set.
     *
     * @return the number of elements in this set.
     */
    @Override
    public int size() {
        return mapObject.size();
    }

}
```