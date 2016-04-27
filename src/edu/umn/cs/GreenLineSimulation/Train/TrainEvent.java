/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */

package edu.umn.cs.GreenLineSimulation.Train;


import edu.umn.cs.GreenLineSimulation.Event.Event;
import edu.umn.cs.GreenLineSimulation.GreenLineSim;
import edu.umn.cs.GreenLineSimulation.Passenger.Passenger;
import edu.umn.cs.GreenLineSimulation.Stop.Stop;
import edu.umn.cs.GreenLineSimulation.Util.Statistics;

import java.util.ArrayList;

public class TrainEvent implements Event {
    private Train train;
    private Stop currentStop;
    private int currentStopId;
    private GreenLineSim gSim;

    private int waitModifier;  //increments by 2 for each Passenger getting off train, 1 for Passenger getting on.

    public TrainEvent(Train train, GreenLineSim gSim){
        waitModifier = 0;
        this.train = train;
        currentStop = gSim.getStops()[train.getStop()];
        currentStopId = currentStop.getId();
        this.gSim = gSim;
        System.out.println(train);
    }

    private void removePassengersFromTrain(){
        ArrayList<Passenger> passengersGotOff = train.removePassengers();
        for (Passenger pass : passengersGotOff) {
            System.out.println("Passenger getting off. " + pass.getDestination() + " = " + currentStopId);
            pass.setFinalTime(gSim.getAgenda().getCurrentTime());
            Statistics.analyzePassenger(pass); //tempPass isn't added back to TrainCar Queue, processed for statistics
            waitModifier += 2;
        }
    }

    private void addWestPassengersToTrain(){
        // train will return passengers if the train is full
       currentStop.setWestPassengers(train.addPassengers(currentStop.getWestPassengers()));
    }

    private void addEastPassengersToTrain(){
        // train will return passengers if the train is full
        currentStop.setEastPassengers(train.addPassengers(currentStop.getEastPassengers()));
    }

    //Direction: 0 = westbound; 1 = eastbound
    private void addPassengersToTrain(){
        if(currentStop.numPeople() == 0){
            System.out.println("Not one is in stop yet");
        }

        switch (train.getDirection()){
            //Westbound Train, also checks if Eastbound Queue is empty
            case 0:
                addWestPassengersToTrain();
                break;
            //Eastbound Train, also checks if Eastbound Queue is empty
            case 1:
               addEastPassengersToTrain();
                break;
        }

        if (waitModifier < 15){
            waitModifier = 15;
        }

        train.setCurrentStop(train.getNextStop());
        train.generateNextStop();
    }

    @Override
    public void run() {

        //Passengers get off train
        removePassengersFromTrain();

        //Passengers get on train
        addPassengersToTrain();
        //reschedule train event
        gSim.addEvent(new TrainEvent(train, gSim), 180 + waitModifier);

    }
}
