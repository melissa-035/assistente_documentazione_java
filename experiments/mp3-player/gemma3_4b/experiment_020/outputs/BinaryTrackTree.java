```java
/**
 * Represents a node in a binary search tree that stores Track objects.
 * This class provides methods for inserting, finding, and managing
 * the tree structure.
 */
public class BinaryTrackTree {

	/**
	 * The root node of the binary search tree.
	 */
	TreeNode root;

	/**
	 * Default constructor for BinaryTrackTree. Initializes the root to null.
	 */
	BinaryTrackTree() {
		root = null;
	}

	/**
	 * Constructor for BinaryTrackTree. Initializes the root node with a given Track object.
	 *
	 * @param value The Track object to be stored in the root node.
	 */
	BinaryTrackTree(Track value) {
		root = new TreeNode(value);
	}

	/**
	 * Inserts a new Track object into the binary search tree.
	 *
	 * @param track The Track object to be inserted.
	 */
	void insert(Track track) {
		root = insertHelper(root, track);
	}

	/**
	 * Recursive helper method for inserting a Track object into the binary search tree.
	 *
	 * @param node The current node being examined.
	 * @param key The Track object to be inserted.
	 * @return The modified node after insertion.
	 */
	TreeNode insertHelper(TreeNode node, Track key) {
		if (node == null) {
			node = new TreeNode(key);
			return node;
		}

		if (key.getTitle().compareTo(node.key.getTitle()) < 0)
			node.left = insertHelper(node.left, key);
		else if (key.getTitle().compareTo(node.key.getTitle()) > 0)
			node.right = insertHelper(node.right, key);
		return node;
	}

	/**
	 * Searches for a Track object in the binary search tree.
	 *
	 * @param node The current node being examined.
	 * @param title The title of the Track object to be searched for.
	 * @return The TreeNode object if the Track object is found, otherwise null.
	 */
	public TreeNode find(TreeNode node, String title) {
		if (node == null || node.key.getTitle().equalsIgnoreCase(title))
			return node;

		if (node.key.getTitle().compareTo(title) < 0)
			return find(node.right, title);

		return find(node.left, title);
	}
}
```