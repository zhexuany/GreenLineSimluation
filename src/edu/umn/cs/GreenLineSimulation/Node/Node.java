// N.java
// A *simplified* node class for use with the Stack1 class
// and other uses as desired
// Posted previously, but used for simulation

package edu.umn.cs.GreenLineSimulation.Node;

public class Node{
    // constructors
    public Node() {}

    public Node(Object o, Node next) {
        data = o;
        this.next = next;
    }

    // selectors

    public Object getData() {
        return data;
    }

    public void setData(Object o) {
        data = o;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    // instance variables

    private Object data;
    private Node next;

}  // N class
