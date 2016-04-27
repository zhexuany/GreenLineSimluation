package edu.umn.cs.GreenLineSimulation;

import edu.umn.cs.GreenLineSimulation.Util.Statistics;

/**
 * Created by Zhexuan Yang on 4/26/16.
 * Copyright (C) 2016
 */
public class GreenLineSimMain {
    public static void main(String[] args) {
        if(args.length != 3){
            throw new IllegalStateException("Only accept two command parameter");
        }
        //Vary numberOfTrains, numberOfCars, and The Load
        int numberOfTrains = Integer.parseInt(args[0]); //range 1 to numStops
        int numOfCars = Integer.parseInt(args[1]); //range 1 to 3
        int interval = Integer.parseInt(args[2]);
        GreenLineSim gSim = new GreenLineSim(numberOfTrains, numOfCars, interval);
        System.out.println(gSim);
        gSim.start();
        //Statistics Output
        new Statistics(gSim);
        Statistics.outputPassengerData();
        System.out.println();
        Statistics.displayStopStats();
    }
}
