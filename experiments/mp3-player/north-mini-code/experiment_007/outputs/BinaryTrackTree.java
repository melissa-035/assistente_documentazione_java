```java
/**
 * A binary search tree that stores Track objects. Tracks are ordered based on their
 * title (case-sensitive for tree structure), allowing for efficient insertion and
 * retrieval operations.
 *
 * @author Generated for BinaryTrackTree
 * @see Track
 * @see TreeNode
 */
public class BinaryTrackTree {
	/**
	 * The root node of the binary tree. This serves as the entry point for all
	 * insertion and search operations.
	 */
	TreeNode root;

	/**
	 * Constructs an empty BinaryTrackTree. The root of the tree is initialized to
	 * {@code null}.
	 */
	BinaryTrackTree()
	{
		root = null;
	}

	/**
	 * Constructs a BinaryTrackTree with a single Track as the root node.
	 *
	 * @param value The track to be stored as the root of the new tree.
	 */
	BinaryTrackTree(Track value) {
		root = new TreeNode(value);
	}

	/**
	 * Inserts a Track into the tree. This method initiates the insertion process
	 * by calling the recursive helper method {@code insertHelper}.
	 *
	 * @param track The track to insert into the tree.
	 */
	void insert(Track track) {
		root = insertHelper(root, track);
	}

	/**
	 * Recursively inserts a track into the binary search tree rooted at {@code node}.
	 * This helper method determines the appropriate position for the new track by
	 * comparing track titles.
	 *
	 * @param node The root of the current subtree.
	 * @param key  The track to insert.
	 * @return The root of the (possibly) modified subtree.
	 */
	TreeNode insertHelper(TreeNode node, Track key)
	{
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
	 * Searches for a Track within the tree based on the specified title.
	 * <p>
	 * This method performs a search starting from the given node. It prioritizes
	 * case-insensitive matching. If the title matches (ignoring case), the method
	 * returns immediately. Otherwise, it traverses the tree in a case-sensitive manner
	 * to find the correct subtree.
	 *
	 * @param node  The current node to examine in the search.
	 * @param title The title of the track to find.
	 * @return The {@code TreeNode} containing the matching track if found,
	 * otherwise {@code null}.
	 */
	public TreeNode find(TreeNode node, String title)
	{
		if (node == null || node.key.getTitle().equalsIgnoreCase(title))
			return node;

		if (node.key.getTitle().compareTo(title) < 0)
			return find(node.right, title);

		return find(node.left,title);
	}
}

class TreeNode {
	/**
	 * The Track object stored within this tree node. This acts as the data payload
	 * for the node.
	 */
	Track key;

	/**
	 * The left and right child nodes of this node. These pointers establish the
	 * binary tree structure.
	 */
	TreeNode left, right;

	/**
	 * Constructs a TreeNode and initializes it with the specified Track.
	 *
	 * @param t The track to be stored in this node.
	 */
	public TreeNode(Track t)
	{
		key = t;
		left = right = null;
	}
}
```