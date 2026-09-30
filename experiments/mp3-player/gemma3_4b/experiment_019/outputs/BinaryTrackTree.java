```java
/**
 * Represents a node in a Binary Search Tree for tracks.
 */
public class BinaryTrackTree {

	/**
	 * The root node of the binary tree.
	 */
	TreeNode root;

	/**
	 * Default constructor for BinaryTrackTree. Initializes the root to null.
	 */
	BinaryTrackTree() {
		root = null;
	}

	/**
	 * Constructor for BinaryTrackTree.  Initializes the root node with a provided track.
	 * @param track The track to be inserted as the root of the tree.
	 */
	BinaryTrackTree(Track track) {
		root = new TreeNode(track);
	}

	/**
	 * Inserts a new track into the BinaryTrackTree.
	 * @param track The track to be inserted.
	 */
	void insert(Track track) {
		root = insertHelper(root, track);
	}

	/**
	 * Recursive helper method to insert a new track into the binary tree.
	 * @param node The current node being examined.
	 * @param key The track to be inserted.
	 * @return The updated node after insertion.
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
	 * Searches for a track in the BinaryTrackTree.
	 * @param node The current node being examined.
	 * @param title The title of the track to search for.
	 * @return The TreeNode representing the track if found, or null if not found.
	 */
	public TreeNode find(TreeNode node, String title) {
		if (node == null || node.key.getTitle().equalsIgnoreCase(title))
			return node;

		if (node.key.getTitle().compareTo(title) < 0)
			return find(node.right, title);

		return find(node.left, title);
	}
}


/**
 * Represents a track with a title.
 */
class TreeNode {
	/**
	 * The track data associated with this node.
	 */
	Track key;

	/**
	 * The left child node in the tree.
	 */
	TreeNode left;

	/**
	 * The right child node in the tree.
	 */
	TreeNode right;

	/**
	 * Constructor for TreeNode. Initializes the key, left, and right children to null.
	 * @param t The track data to be stored in this node.
	 */
	public TreeNode(Track t) {
		key = t;
		left = right = null;
	}
}
```