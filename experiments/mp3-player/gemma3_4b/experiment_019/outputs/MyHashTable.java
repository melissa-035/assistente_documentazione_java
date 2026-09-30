```java
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple hash table implementation using chaining to handle collisions.
 * 
 * This class provides basic hash table operations such as adding,
 * removing, and getting elements based on their keys. It also includes
 * functionality for resizing the hash table when the load factor exceeds
 * a certain threshold.
 */
public class MyHashTable<K, V> {

	/**
	 * The number of buckets in the hash table.
	 */
	private int size;
	/**
	 * The ArrayList containing the buckets.
	 */
	private ArrayList<Node<K, V> > buckets;
	/**
	 * The number of buckets in the hash table.
	 */
	private int bucketCount;

	/**
	 * Constructs a new MyHashTable with an initial bucket count of 10.
	 */
	public MyHashTable() {
		buckets = new ArrayList<>();
		bucketCount = 10;
		size = 0;

		for (int i = 0; i < bucketCount; i++)
			buckets.add(null);
	}

	/**
	 * Returns the number of elements stored in the hash table.
	 * 
	 * @return The size of the hash table.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns true if the hash table is empty, false otherwise.
	 * 
	 * @return True if the hash table is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Computes the hash code for a given key.
	 * 
	 * @param key The key to compute the hash code for.
	 * @return The hash code for the key.
	 */
	private final int getHash (K key) {
		return Objects.hashCode(key);
	}

	/**
	 * Computes the index within the buckets ArrayList for a given key.
	 * 
	 * @param key The key to compute the index for.
	 * @return The index of the bucket for the key.
	 */
	private int getIndex(K key)
	{
		int hash = getHash(key);
		int id = hash % bucketCount;
		id = id < 0 ? id * -1 : id;
		return id;
	}

	/**
	 * Removes the element associated with the given key from the hash table.
	 * 
	 * @param key The key of the element to remove.
	 * @return The value associated with the removed key, or null if the key
	 *         is not found.
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
	 * Gets the value associated with the given key from the hash table.
	 * 
	 * @param key The key of the element to retrieve.
	 * @return The value associated with the key, or null if the key is not
	 *         found.
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
	 * Adds a new element to the hash table.
	 * 
	 * @param key The key of the new element.
	 * @param value The value of the new element.
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