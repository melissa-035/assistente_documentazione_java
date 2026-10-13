```java
/**
 * A generic hash table implementation using separate chaining for collision resolution.
 * This table maintains a mapping of keys to values and allows {@code null} values.
 * 
 * <p>This implementation provides constant-time performance for the basic operations
 * {@code add}, {@code get}, and {@code remove} assuming the hash function disperses
 * the keys properly across the buckets. Construction of the table is O(n) for the
 * initial size and O(n) for resizing when the load factor is exceeded.</p>
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class MyHashTable<K, V> {
    /**
     * The number of key-value pairs currently stored in the hash table.
     */
    private int size;

    /**
     * An array of buckets. Each bucket is a linked list of {@code Node} objects
     * storing entries that hash to the same index.
     */
    private ArrayList<Node<K, V> > buckets;

    /**
     * The number of buckets (capacity) in this hash table.
     * The table resizes when the load factor exceeds 0.7.
     */
    private int bucketCount;

    /**
     * Constructs an empty {@code MyHashTable} with an initial capacity of 10.
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
     * @return {@code true} if this map contains no mappings
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Computes the hash code of the given key using {@link Objects#hashCode(Object)}.
     * This is a private helper method for internal use.
     *
     * @param key the key to hash
     * @return the hash code
     */
    private final int getHash (K key) {
        return Objects.hashCode(key);
    }

    /**
     * Calculates the bucket index for the given key.
     * Uses modulo arithmetic to map the hash to an array index, ensuring
     * the index is non-negative.
     *
     * @param key the key to find the index for
     * @return the bucket index
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
     * <p>If the key was found, its value is returned. Otherwise, {@code null} is returned.
     * This operation may require traversing a linked list if collisions occur.</p>
     *
     * @param key the key whose mapping is to be removed
     * @return the previous {@code value} associated with {@code key}, or {@code null}
     *         if there was no mapping for {@code key}
     * @throws NullPointerException if the key is null (due to the internal call to {@code key.equals()})
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
     * Returns the value to which the specified key is mapped, or {@code null} if this
     * map contains no mapping for the key.
     *
     * <p>This method searches the linked list in the appropriate bucket based on the key's hash.</p>
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null} if there is no mapping
     * @throws NullPointerException if the key is null (due to the internal call to {@code key.equals()})
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
     * If the key already exists in the map, its value is updated.
     *
     * <p>This method handles collision resolution via separate chaining. If the load factor
     * (size / bucketCount) exceeds or equals 0.7, the table is resized to double its current
     * capacity and all entries are rehashed to the new buckets.</p>
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @throws NullPointerException if the key is null (due to the internal call to {@code key.equals()})
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
 * A node in the linked list used by {@code MyHashTable} to handle collisions.
 * Each node stores a key-value pair and a reference to the next node in the chain.
 *
 * @param <K> the type of keys stored in this node
 * @param <V> the type of values stored in this node
 */
class Node<K, V> {
    /**
     * The key stored in this node.
     */
    K key;

    /**
     * The value associated with the key.
     */
    V value;

    /**
     * The hash code of the key (stored for quick lookup during rehashing).
     */
    final int hash;

    /**
     * A reference to the next node in the linked list.
     */
    Node<K, V> next;

    /**
     * Constructs a new node with the specified key, value, and hash.
     *
     * @param key   the key to be stored
     * @param value the value to be stored
     * @param hash  the hash code of the key
     */
    public Node(K key, V value, int hash)
    {
        this.key = key;
        this.value = value;
        this.hash = hash;
    }
}
```