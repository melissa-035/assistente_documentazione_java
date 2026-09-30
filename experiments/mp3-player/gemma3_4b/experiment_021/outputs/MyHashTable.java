```java
import java.util.ArrayList;
import java.util.Objects;

public class MyHashTable<K, V> {
	private int size; // The number of key-value pairs stored in the hash table.
	private ArrayList<Node<K, V> > buckets; // An ArrayList of Node objects, where each Node represents a bucket in the hash table.
	private int bucketCount; // The number of buckets in the hash table.

	public MyHashTable()
	{
		buckets = new ArrayList<>(); // Initializes the buckets ArrayList with an initial capacity.
		bucketCount = 10; // Sets the initial bucket count to 10.
		size = 0; // Initializes the size to 0.

		for (int i = 0; i < bucketCount; i++)
			buckets.add(null); // Adds null nodes to each bucket in the ArrayList, creating an empty hash table.
	}

	public int size() {
		return size; // Returns the number of key-value pairs stored in the hash table.
	}

	public boolean isEmpty() {
		return size() == 0; // Returns true if the hash table is empty, false otherwise.
	}

	private final int getHash (K key) {
		return Objects.hashCode(key); // Returns the hash code of the key.
	}

	private int getIndex(K key)
	{
		int hash = getHash(key); // Calls the getHash method to get the hash code of the key.
		int id = hash % bucketCount; // Calculates the bucket index using the modulo operator.
		id = id < 0 ? id * -1 : id; // Ensures the bucket index is always positive.
		return id; // Returns the calculated bucket index.
	}

	public V remove(K key)
	{
		int bucketId = getIndex(key); // Calculates the bucket ID for the given key.
		int hash = getHash(key); // Calls the getHash method to get the hash code of the key.
		Node<K, V> topNode = buckets.get(bucketId); // Gets the first node in the bucket.
		Node<K, V> prevNode = null; // Keeps track of the previous node in the bucket.

		while (topNode != null) {
			if (topNode.key.equals(key) && hash == topNode.hash)
				break; // If the key matches and the hash codes match, the node is found.

			prevNode = topNode; // Updates the previous node.
			topNode = topNode.next; // Moves to the next node in the bucket.
		}

		if (topNode == null)
			return null; // If the node is not found, return null.

		size--; // Decrements the size of the hash table.

		if (prevNode != null)
			prevNode.next = topNode.next; // If the node is not the first in the bucket, update the next pointer of the previous node.
		else
			buckets.set(bucketId, topNode.next); // If the node is the first in the bucket, update the bucket's head.

		return topNode.value; // Returns the value of the removed node.
	}

	public V get(K key)
	{
		int bucketId = getIndex(key); // Calculates the bucket ID for the given key.
		int hash = getHash(key); // Calls the getHash method to get the hash code of the key.

		Node<K, V> topNode = buckets.get(bucketId); // Gets the first node in the bucket.

		while (topNode != null) {
			if (topNode.key.equals(key) && topNode.hash == hash)
				return topNode.value; // If the key matches and the hash codes match, return the value.
			topNode = topNode.next; // Moves to the next node in the bucket.
		}

		return null; // If the node is not found, return null.
	}

	public void add(K key, V value)
	{
		int bucketId = getIndex(key); // Calculates the bucket ID for the given key.
		int hash = getHash(key); // Calls the getHash method to get the hash code of the key.
		Node<K, V> topNode = buckets.get(bucketId); // Gets the first node in the bucket.

		while (topNode != null) {
			if (topNode.key.equals(key) && topNode.hash == hash) {
				topNode.value = value; // If the key matches and the hash codes match, update the value.
				return; // Returns to exit the method.
			}
			topNode = topNode.next; // Moves to the next node in the bucket.
		}

		size++; // Increments the size of the hash table.
		topNode = buckets.get(bucketId); // Gets the first node in the bucket.
		Node<K, V> tempNode
				= new Node<K, V>(key, value, hash); // Creates a new node with the given key, value, and hash code.
		tempNode.next = topNode; // Sets the next pointer of the new node to the current head of the bucket.
		buckets.set(bucketId, tempNode); // Updates the head of the bucket to the new node.

		if ((1.0 * size) / bucketCount >= 0.7) {
			ArrayList<Node<K, V> > tempBucket = buckets; // Creates a copy of the current buckets ArrayList.
			buckets = new ArrayList<>(); // Creates a new empty ArrayList to store the buckets.
			bucketCount = 2 * bucketCount; // Doubles the number of buckets.
			size = 0; // Resets the size to 0.
			for (int i = 0; i < bucketCount; i++)
				buckets.add(null); // Adds null nodes to each bucket in the ArrayList, creating an empty hash table.

			for (Node<K, V> headNode : tempBucket) {
				while (headNode != null) {
					add(headNode.key, headNode.value); // Recursively calls the add method for each node in the old bucket.
					headNode = headNode.next; // Moves to the next node in the bucket.
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
		this.key = key; // Sets the key of the node.
		this.value = value; // Sets the value of the node.
		this.hash = hash; // Sets the hash code of the node.
	}
}
```