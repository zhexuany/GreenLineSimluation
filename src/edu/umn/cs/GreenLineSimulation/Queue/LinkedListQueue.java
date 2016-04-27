
package edu.umn.cs.GreenLineSimulation.Queue;

// Queue implementation using a linked list of nodes (see Node.java)
// Posted previously, but used for simulation


import edu.umn.cs.GreenLineSimulation.Node.Node;

public class LinkedListQueue implements Queue {
    // constructor
    private int size;
    private Node front;
    private Node rear;

    public LinkedListQueue() {}

    // selectors
    public void add(Object o) {
        if (size == 0) {
            front = new Node(o, null);
            rear = front;
        }
        else {
            rear.setNext(new Node(o, null));
            rear = rear.getNext();
        }
        size++;
    }

    public Object remove() {
        Object result;

        if (size == 0)
            return null;

        result = front.getData();
        front = front.getNext();
        size--;
        if (size == 0)
            rear = null;
        return result;
    }

    public int length() {
        return size;
    }

    //added methods for GreenLineSim
    public boolean isFull() {
        //since the implementation is linked list, queue will be never full
        return false;
    }
}

