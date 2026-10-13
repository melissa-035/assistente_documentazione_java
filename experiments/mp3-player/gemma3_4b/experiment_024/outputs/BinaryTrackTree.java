```java
/**
 * Represents a Binary Search Tree (BST) data structure that stores Track objects.
 * Each node in the tree contains a Track object and references to its left and
 * right children.
 */
public class BinaryTrackTree {

	/**
	 * The root node of the BinaryTrackTree.
	 */
	TreeNode root;

	/**
	 * Default constructor for BinaryTrackTree. Initializes the root to null.
	 */
	public BinaryTrackTree() {
		root = null;
	}

	/**
	 * Constructor for BinaryTrackTree that initializes the tree with a given Track object.
	 *
	 * @param value The Track object to be inserted as the root of the tree.
	 */
	public BinaryTrackTree(Track value) {
		root = new TreeNode(value);
	}

	/**
	 * Inserts a new Track object into the BinaryTrackTree.
	 * The insertion is performed using a recursive helper method.
	 *
	 * @param track The Track object to be inserted.
	 */
	void insert(Track track) {
		root = insertHelper(root, track);
	}

	/**
	 * Recursive helper method for inserting a Track object into the BinaryTrackTree.
	 *
	 * @param node The current node being examined.
	 * @param key  The Track object to be inserted.
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
	 * Searches for a Track object within the BinaryTrackTree.
	 * The search is performed using a recursive helper method.
	 *
	 * @param node    The current node being examined.
	 * @param title   The title of the Track object to search for.
	 * @return The TreeNode object if the title is found, otherwise null.
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