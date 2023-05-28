import java.io.FileWriter;
import java.io.IOException;

public class BinarySearchTree<E extends Comparable<E>> {
    private LinkedBinaryTree<E, LinkedBinaryTree.Node<E>> tree;

    public BinarySearchTree() {
        tree = new LinkedBinaryTree<>();
    }

    public LinkedBinaryTree.Node<E> getRoot(){
        return tree.root;
    }
    protected void setRoot(LinkedBinaryTree.Node<E> node) {
        tree.root = node;
    }

    public E findMin() {
        if (tree.isEmpty()) {
            return null; // Tree is empty
        }

        LinkedBinaryTree.Node<E> current = tree.root;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getElement();
    }

    public boolean search(E x) {
        return search(x, tree.root);
    }

    private boolean search(E x, LinkedBinaryTree.Node<E> current) {
        if (current == null) {
            return false; // Element not found
        }

        int compareResult = x.compareTo(current.getElement());
        if (compareResult < 0) {
            return search(x, current.getLeft()); // Search in the left subtree
        } else if (compareResult > 0) {
            return search(x, current.getRight()); // Search in the right subtree
        } else {
            return true; // Element found
        }
    }

    public void insert(E x) {
        tree.root = insert(x, tree.root, null);
    }

    private LinkedBinaryTree.Node<E> insert(E x, LinkedBinaryTree.Node<E> current, LinkedBinaryTree.Node<E> parent) {
        if (current == null) {
            LinkedBinaryTree.Node<E> newNode = new LinkedBinaryTree.Node<>(x, parent, null, null);
            tree.size++;
            return newNode;
        }

        int compareResult = x.compareTo(current.getElement());
        if (compareResult < 0) {
            current.left = insert(x, current.getLeft(), current); // Insert in the left subtree
        } else if (compareResult > 0) {
            current.right = insert(x, current.getRight(), current); // Insert in the right subtree
        }
        return current;
    }

    public void delete(E x) {
        tree.root = delete(x, tree.root);
    }

    private LinkedBinaryTree.Node<E> delete(E x, LinkedBinaryTree.Node<E> current) {
        if (current == null) {
            return null; // Element not found
        }

        int compareResult = x.compareTo(current.getElement());
        if (compareResult < 0) {
            current.left = delete(x, current.getLeft()); // Delete from the left subtree
        } else if (compareResult > 0) {
            current.right = delete(x, current.getRight()); // Delete from the right subtree
        } else {
            // Element found, perform deletion
            if (current.getLeft() == null && current.getRight() == null) {
                // Case 1: No children
                tree.size--;
                return null;
            } else if (current.getLeft() == null) {
                // Case 2: Only right child
                tree.size--;
                return current.getRight();
            } else if (current.getRight() == null) {
                // Case 3: Only left child
                tree.size--;
                return current.getLeft();
            } else {
                // Case 4: Two children
                LinkedBinaryTree.Node<E> successor = findMinNode(current.getRight());
                current.element = successor.getElement();
                current.right = delete(successor.getElement(), current.getRight());
            }
        }
        return current;
    }

    private LinkedBinaryTree.Node<E> findMinNode(LinkedBinaryTree.Node<E> current) {
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    private LinkedBinaryTree.Node<E> insert(E x, LinkedBinaryTree.Node<E> current) {
        if (current == null) {
            LinkedBinaryTree.Node<E> newNode = new LinkedBinaryTree.Node<>(x, null, null, null);
            tree.size++;
            return newNode;
        }

        int compareResult = x.compareTo(current.getElement());
        if (compareResult < 0) {
            current.left = insert(x, current.left); // Recursively insert in the left subtree
        } else if (compareResult > 0) {
            current.right = insert(x, current.right); // Recursively insert in the right subtree
        }
        return current;
    }

    // Phương thức in-order ghi vào file
    void printInOrderToFile(String filename) {
        System.out.println(filename);
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("In-order traversal: ");
            printInOrderToFile(getRoot(), writer);
            System.out.println(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Phương thức print in-order
    void printInOrderToFile(LinkedBinaryTree.Node<E> root, FileWriter writer) throws IOException {
        if (root == null)
            return;

        printInOrderToFile(root.getLeft(), writer);
        writer.write(root.getElement() + " ");
        printInOrderToFile(root.getRight(), writer);
    }

    // Phương thức pre-order ghi vào file
    void printPreOrderToFile(String filename) {
        System.out.println(filename);
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Pre-order traversal: ");
            printPreOrderToFile(getRoot(), writer);
            //System.out.println(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printPreOrderToFile(LinkedBinaryTree.Node<E> root, FileWriter writer) throws IOException {
        if (root == null)
            return;
        writer.write(root.getElement() + " ");
        printPreOrderToFile(root.getLeft(), writer);      
        printPreOrderToFile(root.getRight(), writer);
    }

    // Phương thức print post-order
    void printPostOrderToFile(String filename) {
        System.out.println(filename);
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Pre-order traversal: ");
            printPostOrderToFile(getRoot(), writer);
            //System.out.println(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printPostOrderToFile(LinkedBinaryTree.Node<E> root, FileWriter writer) throws IOException {
        if (root == null)
            return;
        printPostOrderToFile(root.getLeft(), writer);
        printPostOrderToFile(root.getRight(), writer);
        writer.write(root.getElement() + " ");
    }
}