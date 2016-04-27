
package edu.umn.cs.GreenLineSimulation.Queue;
// Queue Interface
// Posted before, but used for simulation

public interface Queue {

// Interface for a Queue

   void add(Object o);

    /* adds an object o to a queue placing it in the order of arrival
       relative to other items added to the queue--first in, first out
       (FIFO) */

   Object remove();

    /* removes and returns the object placed in a queue prior
       to any other items presently in the queue */

   int length();

    /* returns the integer quantity of items currently present in the
       queue */

}
