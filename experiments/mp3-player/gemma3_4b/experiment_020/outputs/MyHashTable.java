```java
/**
 * A simple hash table implementation.
 *
 * This hash table uses separate chaining to handle collisions. It dynamically
 * resizes when the load factor exceeds 0.7.
 *
 * @param <K> the type of the keys in the hash table.
 * @param <V> the type of the values in the hash table.
 */
public class MyHashTable<K, V> {
	private int size;
	private ArrayList<Node<K, V> > buckets;
	private int bucketCount;

	public MyHashTable()
	{
		buckets = new ArrayList<>();
		bucketCount = 10;
		size = 0;

		for (int i = 0; i < bucketCount; i++)
			buckets.add(null);
	}

	/**
     * Returns the number of key-value pairs stored in this hash table.
     *
     * @return the number of key-value pairs stored in this hash table.
     */
	public int size() {
		return size;
	}

	/**
     * Returns true if this hash table contains no key-value pairs,
     * false otherwise.
     *
     * @return true if this hash table contains no key-value pairs,
     *         false otherwise.
     */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
     * Returns the hash code for the specified key.
     *
     * @param key the key whose hash code is to be returned.
     * @return the hash code for the specified key.
     */
	private final int getHash (K key) {
		return Objects.hashCode(key);
	}

	/**
     * Returns the bucket index for the specified key.
     *
     * @param key the key for which to return the bucket index.
     * @return the bucket index for the specified key.
     */
	private int getIndex(K key)
	{
		int hash = getHash(key);
		int id = hash % bucketCount;
		id = id < 0 ? id * -1 : id;
		return id;
	}

	/**
     * Removes the mapping for the specified key from this hash table.
     *
     * @param key the key whose mapping is to be removed.
     * @return the value associated with the specified key, or null if the
     *         key was not present in this hash table.
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
     * Returns the value to which the specified key is mapped, or null if
     * this hash table does not contain the specified key.
     *
     * @param key the key whose associated value is to be returned.
     * @return the value to which the specified key is mapped, or null if
     *         this hash table does not contain the specified key.
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
     * Adds a new mapping to the hash table.
     *
     * @param key   the key of the new mapping.
     * @param value the value associated with the key.
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