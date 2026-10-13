```java
/**
 * Binary search tree (BST) that stores Track objects.
 * The tree is ordered by the title of the Track using a case-sensitive comparison.
 * Each node contains a Track and references to its left and right children.
 */
public class BinaryTrackTree {
    /**
     * The root node of the BST. Initially null.
     */
    TreeNode root;

    /**
     * Constructs an empty BinaryTrackTree (root is null).
     */
    BinaryTrackTree() {
        root = null;
    }

    /**
     * Constructs a BinaryTrackTree with a single node containing the given Track.
     *
     * @param value the Track to be placed at the root of the tree.
     */
    BinaryTrackTree(Track value) {
        root = new TreeNode(value);
    }

    /**
     * Inserts a Track into the BST.
     * The insertion maintains the BST property based on Track titles (case-sensitive).
     *
     * @param track the Track to insert into the tree.
     */
    void insert(Track track) {
        root = insertHelper(root, track);
    }

    /**
     * Recursive helper method to insert a Track into the BST.
     * <p>
     * This method is called by {@link #insert(Track)} to perform the actual insertion.
     * </p>
     *
     * @param node the current node in the tree (may be null).
     * @param key  the Track to insert.
     * @return the (possibly new) root of the subtree.
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
     * Searches for a Track by title in the BST.
     * The search is case-insensitive: it uses {@link String#equalsIgnoreCase} to match titles.
     *
     * @param node  the current node in the tree (starting from the root).
     * @param title the title of the Track to find.
     * @return the TreeNode containing the Track with a title that equals {@code title}
     *         (case-insensitive), or {@code null} if no such Track exists in the tree.
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
 * Represents a node in the BinaryTrackTree.
 * Each node holds a Track and references to its left and right child nodes.
 */
class TreeNode {
    /**
     * The Track stored in this node.
     */
    Track key;

    /**
     * Reference to the left child node (Tracks with smaller titles).
     */
    TreeNode left;

    /**
     * Reference to the right child node (Tracks with larger titles).
     */
    TreeNode right;

    /**
     * Constructs a TreeNode with the given Track and sets its child references to null.
     *
     * @param t the Track to be stored in the node.
     */
    public TreeNode(Track t) {
        key = t;
        left = right = null;
    }
}
```