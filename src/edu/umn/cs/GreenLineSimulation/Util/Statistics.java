/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */

package edu.umn.cs.GreenLineSimulation.Util;


import edu.umn.cs.GreenLineSimulation.GreenLineSim;
import edu.umn.cs.GreenLineSimulation.Passenger.Passenger;
import edu.umn.cs.GreenLineSimulation.Stop.Stop;

import java.util.ArrayList;

public class Statistics {
    //Collective Passenger Array
    private static ArrayList<Passenger> passengers = new ArrayList<>();
    private static GreenLineSim gSim;
    public Statistics(GreenLineSim gSim){
        Statistics.gSim = gSim;
    }
    public static void displayStopStats(){
        Stop[] stops = gSim.getStops();
        for(Stop stop : stops){
            System.out.println(stop);
        }
    }

    public static void analyzePassenger(Passenger p) {
        passengers.add(p);
        System.out.println(p);
    }



    private static int getPassengersProcessed(){
        return passengers.size();
    }

    public static void outputPassengerData() {
        //Encompassing Variables for Overall Statistics
        double collectiveAvgTimeWaited = 0.0;

        for(Passenger passenger : passengers){
            collectiveAvgTimeWaited += passenger.getFinalTime() - passenger.getArrivalTime();
        }
        //More Statistics Calculations
        collectiveAvgTimeWaited /= getPassengersProcessed();


        //Overall Statistics Output
        System.out.println("Overall Statistics Output: ");
        System.out.println("Number of Passengers Processed: " + getPassengersProcessed());
        System.out.println("Average Passenger Wait Time: " + collectiveAvgTimeWaited);
        //CAN I GET THESE VARIABLES INTO THIS CLASS WITHOUT CALLING THEM INSIDE TRAIN INSTEAD?
        //Yeah, trainArr is static
        //TODO need create getter and setter
        System.out.println("Number of trains used: " + gSim.getTrains().length);
        System.out.println("Number of cars used: " + gSim.getTrains()[0].getCars());
    }
}
