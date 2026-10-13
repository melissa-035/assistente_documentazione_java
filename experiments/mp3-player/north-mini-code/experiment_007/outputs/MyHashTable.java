```java
/**
 * Implements a hash table using separate chaining (linked lists) to handle collisions.
 * This class provides O(1) average time complexity for basic operations such as
 * {@link #add add}, {@link #get get}, and {@link #remove remove}.
 *
 * <p>The hash table maintains a load factor threshold of 0.7. When the number of
 * entries (size) divided by the number of buckets (bucketCount) exceeds this
 * threshold, the table automatically resizes by doubling the bucket count and
 * rehashes all existing entries.</p>
 *
 * <p>This implementation is not thread-safe.</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @see Node
 */
import java.util.ArrayList;
import java.util.Objects;

public class MyHashTable<K, V> {
    private int size;
    private ArrayList<Node<K, V> > buckets;
    private int bucketCount;

    /**
     * Constructs an empty hash table with an initial bucket capacity of 10.
     */
    public MyHashTable()
    {
        buckets = new ArrayList<>();
        bucketCount = 10;
        size = 0;

        for (int i = 0; i < bucketCount; i++)
            buckets.add(null);
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of mappings
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Computes the hash code for the given key using {@link Objects#hashCode}.
     *
     * @param key the key to hash
     * @return the hash code
     */
    private final int getHash (K key) {
        return Objects.hashCode(key);
    }

    /**
     * Determines the bucket index for a given key.
     * Uses the modulus of the hash code to determine the bucket.
     *
     * @param key the key to locate
     * @return the index of the bucket
     */
    private int getIndex(K key)
    {
        int hash = getHash(key);
        int id = hash % bucketCount;
        id = id < 0 ? id * -1 : id;
        return id;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or {@code null} if there was no mapping for {@code key}
     */
    public V remove(K key)
    {
        int bucketId = getIndex(key);
        int hash = getHash(key);
        Node<K, V> topNode = buckets.get(bucketId);
        Node<K, V> prevNode = null;

        while (topNode != null) {
            if (topNode.key.equals(key) && hash == topNode.hash)
                break;

            prevNode = topNode;
            topNode = topNode.next;
        }

        if (topNode == null)
            return null;

        size--;

        if (prevNode != null)
            prevNode.next = topNode.next;
        else
            buckets.set(bucketId, topNode.next);

        return topNode.value;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null} if no mapping exists
     */
    public V get(K key)
    {
        int bucketId = getIndex(key);
        int hash = getHash(key);

        Node<K, V> topNode = buckets.get(bucketId);

        while (topNode != null) {
            if (topNode.key.equals(key) && topNode.hash == hash)
                return topNode.value;
            topNode = topNode.next;
        }

        return null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the key already exists, the value is updated. If the load factor
     * (size / bucketCount) exceeds or equals 0.7, the hash table is resized
     * (bucket count is doubled) and all entries are rehashed.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void add(K key, V value)
    {
        int bucketId = getIndex(key);
        int hash = getHash(key);
        Node<K, V> topNode = buckets.get(bucketId);

        while (topNode != null) {
            if (topNode.key.equals(key) && topNode.hash == hash) {
                topNode.value = value;
                return;
            }
            topNode = topNode.next;
        }

        size++;
        topNode = buckets.get(bucketId);
        Node<K, V> tempNode
                = new Node<K, V>(key, value, hash);
        tempNode.next = topNode;
        buckets.set(bucketId, tempNode);

        if ((1.0 * size) / bucketCount >= 0.7) {
            ArrayList<Node<K, V> > tempBucket = buckets;
            buckets = new ArrayList<>();
            bucketCount = 2 * bucketCount;
            size = 0;
            for (int i = 0; i < bucketCount; i++)
                buckets.add(null);

            for (Node<K, V> headNode : tempBucket) {
                while (headNode != null) {
                    add(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }
}

/**
 * A private node class used for separate chaining within {@code MyHashTable}.
 * Each node stores a key-value pair, its precomputed hash code, and a
 * reference to the next node in the chain.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
class Node<K, V> {
    K key;
    V value;
    final int hash;
    Node<K, V> next;

    public Node(K key, V value, int hash)
    {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }
}
```