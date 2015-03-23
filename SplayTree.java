// Nick Cacace
// Splay Tree
// Advanced Data Structures
// Prof. Li
// 3 March 2015

//========================================================================//
// 								Splay Tree 								  //
// Public functions:													  //
// void erase()				--> Erase the tree							  //
// BTNode getRoot()			--> Get refernce to root					  //
// boolean insert(int x)	--> Insert x								  //
// boolean remove(int x)    --> Remove x								  //
// boolean search(int x		--> Search for node with key x				  //
// void printTree()			--> 90 degree rotation print				  //
// boolean splay(int x)		--> Splay at x								  //
// int manyNodes()			--> Number of nodes in tree					  //
// boolean isEmpty()		--> Test for empty tree						  //
// int treeHeight()			--> Height of tre							  //
//========================================================================//


public class SplayTree
{
	private BTNode<Integer> treeRoot;

	// Initialization
	public SplayTree()
	{
		treeRoot = null;
	}

	// Erases the tree
	public void erase()
	{
		treeRoot = null;
	}

	// Returns a reference to the root of the tree
	public BTNode<Integer> getRoot()
	{
		return treeRoot;
	}
//========================================================================//
//								Insertion 								  //
// Inserts a new node into the tree containing E "data" 				  //
// Returns true if successful											  //
// Else returns false (key already exists) 								  //
//========================================================================//
	public boolean insert(int data)
	{
		// Empty tree, base case
		if (treeRoot == null) {
			treeRoot = new BTNode<Integer>(data, null, null);
			return true;
		}
		else
			return insert(data, treeRoot);
	}
	private boolean insert(int data, BTNode<Integer> root)
	{
		// Creates the new node to be inserted
		BTNode<Integer> newNode = new BTNode<Integer>(data, null, null);
		if (data < root.getData()) {
			if (root.getLeft() == null) {
				root.setLeft(newNode);
				splay(data);
				return true;
			}
			else // root.left is not null
				return insert(data, root.getLeft());
		}
		else if (data > root.getData()) {
			if (root.getRight() == null) {
				root.setRight(newNode);
				splay(data);
				return true;
			}
			else // root.right is not null
				return insert(data, root.getRight());
		}
		else if (data == root.getData());
			treeRoot = splay(data);
			return false;

//========================================================================//
//						Alternate Method for insertion					  //
//========================================================================//
		// else { 
		// 	treeRoot = splay(data); // Splay the tree at data
		// 	if (data < treeRoot.getData()){
		// 		newNode.setLeft(treeRoot.getLeft());
		// 		newNode.setRight(treeRoot);
		// 		treeRoot.setLeft(null);
		// 		treeRoot = newNode;
		// 		return true;
		// 	}
		// 	else if (data > treeRoot.getData()) {
		// 		newNode.setRight(treeRoot.getRight());
		// 		newNode.setLeft(treeRoot.getLeft());
		// 		treeRoot.setRight(null);
		// 		treeRoot = newNode;
		// 		return true;
		// 	}
		// 	else // data == treeRoot.getData()
		// 		return false; // Element already exist
		// }
	}
//========================================================================//
//								Deletion 								  //
// Removes the node with key "target" 									  //
// Returns true if node was removed sucessfully 						  //
// Else returns false													  //
//========================================================================//
	public boolean remove(int target)
	{
		// Empty tree
		if (treeRoot == null)
			return false;

		BTNode<Integer> newTree;

		// If target is found, it will be at the root
		splay(target);
		if (target == treeRoot.getData()) {
			if (treeRoot.getLeft() == null) {
				treeRoot = treeRoot.getRight();
			}
			else {
				BTNode<Integer> x = treeRoot.getRight();
				treeRoot = treeRoot.getLeft();
				treeRoot = splay(treeRoot, target);
				treeRoot.setRight(x);
			}
			return true;
		}
		else // It wasn't in the tree
			return false;
	}
//========================================================================//
//								Search									  //
// Searches for a node with key "target" 								  //
// Returns true if found 												  //
// Else returns false 													  //
//========================================================================//
	public boolean search(int target)
	{
		splay(target);
		return (target == treeRoot.getData());	
	}

//========================================================================//
//								Print 									  //
// Prints a visual represntation of the tree starting with the treeRoot   //
//========================================================================//
	public void printTree()
	{
		printTree(treeRoot, 0);
	}
	private static void printTree(BTNode<Integer> root, int depth)
	{
        int level = depth;
        if (root.getRight() != null)
            printTree(root.getRight(), ++depth);
        for (int i = 0; i < level; i++)
            System.out.print("        "); // Spacer
        System.out.println(root.getData());
        if (root.getLeft() != null)
            printTree(root.getLeft(), ++level);
    }


//========================================================================//
//							Splay Function 								  //
// Splays the tree at int "target" 										  //
// If a node with that key exists, it is splayed to the root of the tree. //
// If not, the last node along the search path is splayed to the root.	  //
//========================================================================//
	public BTNode<Integer> splay(int target)
	{
		treeRoot = splay(treeRoot,target);
		return treeRoot;
	}
	private BTNode<Integer> splay(BTNode<Integer> root, int target)
	{
		//Empty tree
		if (root == null) return null;

		if (target < root.getData()) {
			// Key not in tree
			if (root.getLeft() == null) {
				return root;
			}
			if (target < root.getLeft().getData()) {
				root.getLeft().setLeft(splay(root.getLeft().getLeft(), target));
				root = rotateRight(root);
			}
			else if (target > root.getLeft().getData()) {
				root.getLeft().setRight(splay(root.getLeft().getRight(), target));
				if (root.getLeft().getRight() != null)
					root.setLeft(rotateLeft(root.getLeft()));
			}

			if (root.getLeft() == null)
				return root;
			else
				return rotateRight(root);
		}

		else if (target > root.getData()){
			// Key not in tree
			if (root.getRight() == null)
				return root;

			if (target < root.getRight().getData()) {
				root.getRight().setLeft(splay(root.getRight().getLeft(), target));
				if (root.getRight().getLeft() != null)
					root.setRight(rotateRight(root.getRight()));
			}
			else if (target > root.getRight().getData()) {
				root.getRight().setRight(splay(root.getRight().getRight(), target));
				root = rotateLeft(root);
			}

			if (root.getRight() == null)
				return root;
			else
				return rotateLeft(root);
		}

		else return root;
	}

//========================================================================//
//						Supporting Functions			 				  //
//========================================================================//

    // Right Rotation
	private BTNode<Integer> rotateRight(BTNode<Integer> target)
	{
		BTNode<Integer> temp = target.getLeft();
		target.setLeft(temp.getRight());
		temp.setRight(target);
		return temp;
	}

	// Left Rotation
	private BTNode<Integer> rotateLeft(BTNode<Integer> target)
	{
		BTNode<Integer> temp = target.getRight();
		target.setRight(temp.getLeft());
		temp.setLeft(target);
		return temp;
	}

	public int manyNodes()
	{
		return manyNodesInternal(treeRoot);
	}
    private static int manyNodesInternal(BTNode<Integer> root)
    {
    	if (root == null)
    		return 0;
    	else
    		return (1 + manyNodesInternal(root.getLeft()) + manyNodesInternal(root.getRight()));
    }

    // Returns true if there is nothing in the tree
	// Else returns false
	public boolean isEmpty()
	{
		return treeRoot == null;
	}

    // Returns the height of the tree
    public int treeHeight()
    {
    	return treeHeight(treeRoot);
    }
    private static int treeHeight(BTNode<Integer> root)
    {
    	if (root == null)
    		return -1;
    	return 1 + Math.max(treeHeight(root.getLeft()), treeHeight(root.getRight()));
    }
}