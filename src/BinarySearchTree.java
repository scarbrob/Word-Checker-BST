import java.util.*;
import java.io.*;

/**
 * This is a class that is a Binary Search Tree.
 * 
 * @author Ben Scarbrough
 * @Version 1.1
 *
 * @param <E>
 *            A generic type E.
 */
class BinarySearchTree<E extends Comparable<E>> {

	private Node<E> root; // The root.
	private int size; // The size of the BinarySearchTree.
	private File fileName; // The name of the file.

	// A nested class Node that is used as a helper for BinarySearchTree.
	@SuppressWarnings("hiding")
	private class Node<E> {

		private E data; // A variable that holds the value of the current Node.
		private Node<E> left; // A Node that holds the value of the node to the left.
		private Node<E> right; // A Node that holds the value of the node to the right.

		/**
		 * A constructor for the nested Node class.
		 * 
		 * @param data
		 *            The data for the Node.
		 */
		private Node(E data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	/**
	 * A Constructor for the Binary Search Tree.
	 */
	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	/**
	 * A method which inserts a new value into the binary search tree. If the tree
	 * is empty it is added as the root.
	 * 
	 * @param value
	 *            The value to insert into the tree.
	 */
	public void insert(E value) {
		size++;
		Node<E> newNode = new Node<E>(value);
		if (root == null) {
			root = newNode;
			return;
		}
		Node<E> current = root;
		Node<E> parent = null;
		while (true) {
			parent = current;
			if (value.compareTo(current.data) < 0) {
				current = current.left;
				if (current == null) {
					parent.left = newNode;
					return;
				}
			} else {
				current = current.right;
				if (current == null) {
					parent.right = newNode;
					return;
				}
			}
		}
	}

	/**
	 * A method which returns true if the given value is present on the tree, or
	 * false otherwise.
	 * 
	 * @param value
	 *            The value to search for.
	 * @return True or False.
	 */
	public boolean has(E value) {
		Node<E> current = root;
		while (current != null) {
			if (current.data.equals(value)) {
				return true;
			} else if (current.data.compareTo(value) > 0) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return false;
	}

	/**
	 * A method which returns the minimum value in the tree.
	 * 
	 * @return The minimum value.
	 */
	public E findMin() {
		Node<E> current = root;
		if (root == null)
			return null;
		while (current.left != null) {
			current = current.left;
		}
		return current.data;
	}

	/**
	 * A method which returns the maximum value in the tree.
	 * 
	 * @return The maximum value.
	 */
	public E findMax() {
		Node<E> current = root;
		if (root == null)
			return null;
		while (current.right != null) {
			current = current.right;
		}
		return current.data;
	}

	/**
	 * A method that returns the number of nodes inside the BinarySearchTree.
	 * 
	 * @return The size of the BinarySearchTree.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * A method which returns the value in the tree immediately before the given
	 * value, alphabetically. If the value is not in the tree or is the first
	 * element, return null instead.
	 * 
	 * @param value
	 *            The value to find the predecessor of.
	 * @return The predecessor of value.
	 */
	public E findPredecessor(E value) {
		Node<E> current = root;
		if (root == null)
			return null;
		else if (current.data.equals(value) || !has(value))
			return null;
		else {
			if (current.left != null) {
				current = current.left;
			} else
				return null;
			if (current != null) {
				while (current.right != null) {
					current = current.right;
				}
			}
		}
		return current.data;
	}

	/**
	 * which returns the value in the tree immediately after the given value,
	 * alphabetically, in a similar way to findPredecessor().
	 * 
	 * @param value
	 *            The value to find the successor of.
	 * @return The successor of value.
	 */
	public E findSuccessor(E value) {
		Node<E> current = root;
		if (root == null)
			return null;
		else if (current.data.equals(value) || !has(value))
			return null;
		else {
			if (current.right != null) {
				current = current.right;
			} else
				return null;
			if (current != null) {
				while (current.left != null) {
					current = current.left;
				}
			}
		}
		return current.data;
	}

	/**
	 * If the value is present in the tree, delete it and return true. Otherwise,
	 * return false.
	 * 
	 * @param value
	 *            The value to delete in the BinarySearchTree.
	 * @return True or False.
	 */
	public boolean delete(E value) {
		Node<E> temp = delete(root, value);
		if (temp != null) {
			root = temp;
			size--;
			return true;
		}
		return false;
	}

	/**
	 * A helper delete method.
	 * 
	 * @param start
	 *            The root.
	 * @param data
	 *            The data to remove.
	 * @return null or the new root.
	 */
	private Node<E> delete(Node<E> start, E data) {
		if (start == null || !has(data))
			return null;
		if (data.compareTo(start.data) < 0) {
			start.left = delete(start.left, data);
		} else if (data.compareTo(start.data) > 0) {
			start.right = delete(start.right, data);
		} else {
			if (start.left == null) {
				return start.right;
			} else if (start.right == null) {
				return start.left;
			} else {
				Node<E> left = start.left;
				while (left.right != null) {
					left = left.right;
				}
				left.right = start.right;
				return start.left;
			}
		}
		return start;
	}

	/**
	 * A private method that interacts with the user.
	 * 
	 * @param bTree
	 *            The BinarySearchTree to interact with.
	 */
	private void queryUser(BinarySearchTree<String> bTree) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Testing BinarySearchTree.");
		System.out.println("Loading file " + "\"" + fileName + "\"" + ", which contains " + bTree.getSize()
				+ " words, ranging from " + "\"" + bTree.findMin() + "\"" + " to " + "\"" + bTree.findMax() + "\""
				+ ".");
		System.out.println("Please enter a word, or hit enter to quit:");

		while (true) {
			String nextLine = scan.nextLine().toLowerCase();
			if (nextLine.length() == 0) {
				System.out.println("Goodbye!");
				scan.close();
				break;
			} else if (nextLine.startsWith("<")) {
				String temp = nextLine.substring(1);
				if (bTree.has(temp)) {
					System.out.println("The predecessor of " + "\"" + temp + "\"" + " is " + "\""
							+ bTree.findPredecessor(temp) + "\"" + ".");
				} else
					System.out.println("\"" + temp + "\"" + " is NOT a valid word.");
			} else if (nextLine.startsWith(">")) {
				String temp = nextLine.substring(1);
				if (bTree.has(temp)) {
					System.out.println("The successor of " + "\"" + temp + "\"" + " is " + "\""
							+ bTree.findSuccessor(temp) + "\"" + ".");
				} else
					System.out.println("\"" + temp + "\"" + " is NOT a valid word.");

			} else if (nextLine.startsWith("-")) {
				String temp = nextLine.substring(1);
				if (bTree.has(temp)) {
					if (bTree.delete(temp)) {
						System.out.println("\"" + temp + "\"" + " was successfully deleted from the tree.");
					}
				} else
					System.out.println("\"" + temp + "\"" + " is NOT a valid word.");
			} else if (bTree.has(nextLine))
				System.out.println("\"" + nextLine + "\"" + " is a valid word.");
			else
				System.out.println("\"" + nextLine + "\"" + " is NOT a valid word.");

		}
	}

	/**
	 * A private method to fill the BinarySearchTree with objects.
	 * 
	 * @param fileName
	 *            The name of the file to grab from.
	 * @param bTree
	 *            The name of the BinarySearchTree to add to.
	 */
	private void fillTree(String fileName, BinarySearchTree<String> bTree) {
		List<String> lines = new ArrayList<String>();
		this.fileName = new File(fileName);
		try {
			Scanner file_in = new Scanner(this.fileName);
			while (file_in.hasNext()) {
				String line = file_in.nextLine();
				lines.add(line);
			}
			file_in.close();
		} catch (IOException exception) {
			System.out.println(exception.toString());
		}
		Collections.shuffle(lines);
		for (int i = 0; i < lines.size(); i++) {
			bTree.insert(lines.get(i));
		}
	}

	// Main Method
	public static void main(String[] args) {
		BinarySearchTree<String> bTree = new BinarySearchTree<>();
		bTree.fillTree("english.lex", bTree);
		bTree.queryUser(bTree);
	}
}
