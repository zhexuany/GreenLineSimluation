
package edu.umn.cs.GreenLineSimulation.Train;


import edu.umn.cs.GreenLineSimulation.Passenger.Passenger;

import java.util.ArrayList;

/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */
public class TrainCar {

    private ArrayList<Passenger> passengers;
    private final int numSeats = 50;
    public TrainCar(){
        passengers = new ArrayList<>();
    }

    ArrayList<Passenger> removePassengers(int currentStop){
        //iterate all passengers, if any passenger's destination is current stop, remove it
        ArrayList<Passenger> res = new ArrayList<>();
        for(int i = 0; i < passengers.size(); i++){
            Passenger pass = passengers.get(i);
            if(pass != null && pass.getDestination() == currentStop ){
                res.add(pass);
                passengers.remove(pass);
            }
        }
        return res;
    }

   void addPassengers(Passenger pass){
       passengers.add(pass);
   }

    boolean isFull(){
        return (passengers.size() == numSeats);
    }

}
