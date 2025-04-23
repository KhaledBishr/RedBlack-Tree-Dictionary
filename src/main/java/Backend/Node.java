package Backend;

public class Node {
    String word;
    boolean color; // RED = true, BLACK = false
    Node left, right, parent;

    Node(String word) {
        this.word = word;
        this.color = true; // new node is RED
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}