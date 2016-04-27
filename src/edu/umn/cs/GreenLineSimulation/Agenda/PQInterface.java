/**
 * Created by Zhexuan Yang on 4/10/16.
 * Copyright (C) 2016
 */

// Priority Queue and Simulation


package edu.umn.cs.GreenLineSimulation.Agenda;


import edu.umn.cs.GreenLineSimulation.Event.Event;

interface PQInterface {

    // Priority Queue Interface

    public void add(Event o, double time);

    /* Places an event object into priority queue with priority given
       by time */

    public Event remove();

    /* Removes and returns the highest priority event in a priority
       queue; returns null if the priority queue is empty */

    public boolean isEmpty();

    /* Returns true is the priority queue is empty, false otherwise */

    public double getCurrentTime();

    /* Returns the priority associated with the top priority event in the
       priority queue */

}  // PQInterface
