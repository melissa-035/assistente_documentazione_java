```java
/**
 * A generic hash table implementation using separate chaining (linked lists) for collision resolution.
 * This table maintains a load factor threshold (0.7). When the load factor is reached or exceeded,
 * the bucket capacity is automatically doubled to maintain performance characteristics.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
import java.util.ArrayList;
import java.util.Objects;

public class MyHashTable<K, V> {
    /**
     * The number of key-value pairs currently stored in the hash table.
     */
    private int size;

    /**
     * The array of buckets. Each bucket acts as the head of a linked list of {@link Node}s.
     * The index of a bucket is determined by the hash code of the key modulo the bucket count.
     */
    private ArrayList<Node<K, V>> buckets;

    /**
     * The current number of buckets (capacity) in this hash table.
     * This value is doubled during resizing to accommodate more entries.
     */
    private int bucketCount;

    /**
     * Constructs an empty hash table with an initial capacity of 10.
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
     * Returns the number of key-value pairs in this hash table.
     *
     * @return the number of elements in the table
     */
	public int size() {
		return size;
	}

    /**
     * Returns {@code true} if this hash table contains no key-value pairs.
     *
     * @return {@code true} if this hash table contains no entries
     */
	public boolean isEmpty() {
		return size() == 0;
	}

    /**
     * Computes the hash code of the specified key using {@link Objects#hashCode}.
     * This method is marked {@code final} to ensure consistency and prevent overriding
     * which could break the hash table contract.
     *
     * @param key the key to hash
     * @return the hash code value
     */
	private final int getHash (K key) {
		return Objects.hashCode(key);
	}

    /**
     * Calculates the bucket index for a given key.
     * <p>
     * The index is derived by taking the hash code of the key modulo the current
     * bucket count. If the resulting hash is negative, its absolute value is used
     * to ensure the index is within the valid range of array indices.
     *
     * @param key the key to determine the bucket index for
     * @return the index of the bucket where the key should be stored
     */
	private int getIndex(K key)
	{
		int hash = getHash(key);
		int id = hash % bucketCount;
		id = id < 0 ? id * -1 : id;
		return id;
	}

    /**
     * Removes the mapping for the specified key from this hash table if present.
     *
     * @param key the key whose mapping is to be removed from the hash table
     * @return the previous {@code V} value associated with the specified {@code key},
     *         or {@code null} if the key did not have a mapping
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
     * Adds or updates a key-value pair in the hash table.
     * <p>
     * If the key already exists in the table, its value is updated.
     * If the key does not exist, a new {@link Node} is inserted at the head of the
     * appropriate bucket's linked list.
     * <p>
     * If the load factor {@code (size / bucketCount)} reaches or exceeds 0.7, the table
     * undergoes resizing. The bucket capacity is doubled, all existing entries are
     * re-hashed, and they are reinserted into the new buckets.
     *
     * @param key   the key to insert or update
     * @param value the value to associate with the key
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
            // Resize logic: Create new buckets with double capacity
            ArrayList<Node<K, V> > tempBucket = buckets;
			buckets = new ArrayList<>();
			bucketCount = 2 * bucketCount;
			size = 0;
			for (int i = 0; i < bucketCount; i++)
				buckets.add(null);

			// Re-hash all existing nodes into the new buckets
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
 * A node in the linked list structure used by {@link MyHashTable} to handle collisions.
 * Each node stores a key-value pair, its pre-calculated hash code, and a reference
 * to the next node in the chain.
 *
 * @param <K> the type of key stored in this node
 * @param <V> the type of value stored in this node
 */
class Node<K, V> {
    /** The key stored in this node. */
	K key;
    /** The value associated with the key. */
	V value;
    /** The hash code of the key, computed once upon creation. */
	final int hash;
    /** Reference to the next node in the chain (for collision resolution). */
	Node<K, V> next;

	/**
     * Constructs a new node with the specified key, value, and hash code.
     *
     * @param key   the key to store
     * @param value the value to store
     * @param hash  the pre-calculated hash code of the key
     */
	public Node(K key, V value, int hash)
	{
		this.key = key;
		this.value = value;
		this.hash = hash;
	}
}
```