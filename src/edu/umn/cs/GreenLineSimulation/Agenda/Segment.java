
package edu.umn.cs.GreenLineSimulation.Agenda;

import edu.umn.cs.GreenLineSimulation.Queue.LinkedListQueue;

/**
 * Created by Zhexuan Yang on 4/10/16.
 * Copyright (C) 2016
 */
// Priority Queue and Simulation

public class Segment {

    private double time;
    private LinkedListQueue event;
    private Segment next;

    // constructor

    public Segment(double t) {
        time = t;
        event = new LinkedListQueue();
        next = null;
    }

    // methods

    public double getTime() {
        return time;
    }

    LinkedListQueue getEvents() {
        return event;
    }

    Segment getNext() {
        return next;
    }

    void setNext(Segment nextSegment) {
        next = nextSegment;
    }

}  // Segment class
