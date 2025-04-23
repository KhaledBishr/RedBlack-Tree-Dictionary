package Backend;

import java.io.*;

public class RedBlackTree {
    private final boolean RED = true;
    private final boolean BLACK = false;
    private Node root;

    public void insert(String word) {
        Node node = new Node(word.toLowerCase());
        root = bstInsert(root, node);
        fixInsert(node);
    }

    public boolean search(String word) {
        return searchHelper(root, word.toLowerCase());
    }

    private boolean searchHelper(Node node, String word) {
        if (node == null) return false;
        if (word.equals(node.word)) return true;
        if (word.compareTo(node.word) < 0) return searchHelper(node.left, word);
        return searchHelper(node.right, word);
    }

    private Node bstInsert(Node root, Node node) {
        if (root == null) return node;

        if (node.word.compareTo(root.word) < 0) {
            root.left = bstInsert(root.left, node);
            root.left.parent = root;
        } else if (node.word.compareTo(root.word) > 0) {
            root.right = bstInsert(root.right, node);
            root.right.parent = root;
        }
        return root;
    }

    private void rotateLeft(Node node) {
        Node right = node.right;
        node.right = right.left;
        if (right.left != null) right.left.parent = node;
        right.parent = node.parent;
        if (node.parent == null) root = right;
        else if (node == node.parent.left) node.parent.left = right;
        else node.parent.right = right;
        right.left = node;
        node.parent = right;
    }

    private void rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        if (left.right != null) left.right.parent = node;
        left.parent = node.parent;
        if (node.parent == null) root = left;
        else if (node == node.parent.left) node.parent.left = left;
        else node.parent.right = left;
        left.right = node;
        node.parent = left;
    }

    private void fixInsert(Node node) {
        Node parent, grandParent;

        while (node != root && node.parent != null && node.parent.color == RED) {
            parent = node.parent;
            grandParent = parent.parent;

            if (grandParent == null) break;

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;

                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        rotateLeft(node);
                    }
                    parent = node.parent;
                    grandParent = parent.parent;
                    if (grandParent != null) {
                        parent.color = BLACK;
                        grandParent.color = RED;
                        rotateRight(grandParent);
                    }
                }
            } else {
                Node uncle = grandParent.left;

                if (uncle != null && uncle.color == RED) {
                    grandParent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rotateRight(node);
                    }
                    parent = node.parent;
                    grandParent = parent.parent;
                    if (grandParent != null) {
                        parent.color = BLACK;
                        grandParent.color = RED;
                        rotateLeft(grandParent);
                    }
                }
            }
        }
        if (root != null) root.color = BLACK;
    }

    public int getTreeHeight() {
        return computeHeight(root);
    }

    private int computeHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(computeHeight(node.left), computeHeight(node.right));
    }

    public int getBlackHeight() {
        int count = 0;
        Node curr = root;
        while (curr != null) {
            if (!curr.color) count++;
            curr = curr.left;
        }
        return count;
    }

    public int getSize() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                if (!word.isEmpty() && !search(word)) insert(word);
            }
        } catch (IOException e) {
            System.err.println("Failed to load dictionary: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void appendToFile(String filename, String word) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // Ensure the file ends with a newline before appending
            File file = new File(filename);
            if (file.length() > 0) {
                try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                    raf.seek(file.length() - 1);
                    int lastByte = raf.read();
                    if (lastByte != '\n' && lastByte != '\r') {
                        writer.newLine(); // Add newline if the file doesn't end with one
                    }
                }
            }
            writer.write(word.toLowerCase());
            writer.newLine(); // Always add a newline after the word
        } catch (IOException e) {
            System.err.println("Failed to write to dictionary: " + e.getMessage());
        }
    }
}