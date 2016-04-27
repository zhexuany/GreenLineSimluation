package edu.umn.cs.GreenLineSimulation;

import edu.umn.cs.GreenLineSimulation.Agenda.*;
import edu.umn.cs.GreenLineSimulation.Event.Event;
import edu.umn.cs.GreenLineSimulation.Passenger.Passenger;
import edu.umn.cs.GreenLineSimulation.Stop.Stop;
import edu.umn.cs.GreenLineSimulation.Train.Train;

/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */

public class GreenLineSim {

    //Global Variables
    private static Agenda agenda;
    private static Stop[] stops;
    private static Train[] trains;
    private final int numStops = 23;
    private int numTrains;
    private int numCars;
    private int westBoundTrains;
    private int eastBoundTrains;
    private int eastSpacer;
    private int westSpacer;
    private int interval;
    private final int totalTime = 10000;

    GreenLineSim(int numTrains, int numCars, int interval) {
        this.interval = interval;
        this.numCars = numCars;
        this.numTrains = numTrains;
        agenda = new Agenda();
        stops = new Stop[numStops];
        trains = new Train[numTrains];
        eastBoundTrains = (int) Math.ceil((double) numTrains / 2.0);
        westBoundTrains = numTrains - eastBoundTrains;
        calculateSpacer();
        installStops();
    }
   // calculate how many stop between two neighbour trains
    private void calculateSpacer(){
        eastSpacer = (int) Math.round(numStops / (double) eastBoundTrains);

        westSpacer = (int) Math.round(numStops / (double) westBoundTrains);
    }
    //Format, NameField, west, east, are the queues associated, -10 is a downtain, -5 is a campus stop
    //0 is a regular stop, these are just arrival modifiers due to number of people using it
    //Downtown stops in minneapolis and saint paul on either side of the green line
    private void installStops() {
        stops[0] = new Stop(interval, "Target Field", 0,  this, -10);
        stops[1] = new Stop(interval, "Hennepin Ave", 1,  this, -10);
        stops[2] = new Stop(interval, "Nicollet Mall", 2, this, -10);
        stops[3] = new Stop(interval, "Government Plaza", 3,this, -10);
        stops[4] = new Stop(interval, "US Bank Stadium", 4, this, -10);
        stops[5] = new Stop(interval, "West Bank", 5, this, -5);
        stops[6] = new Stop(interval, "East Bank", 6, this, -5);
        stops[7] = new Stop(interval, "Stadium Village", 7, this, -5);
        stops[8] = new Stop(interval, "Prospect Park", 8, this, 0);
        stops[9] = new Stop(interval, "Westgate", 9, this, 0);
        stops[10] = new Stop(interval, "Raymond Ave", 10, this, 0);
        stops[11] = new Stop(interval, "Fairview Ave", 11, this, 0);
        stops[12] = new Stop(interval, "Snelling Ave", 12, this, 0);
        stops[13] = new Stop(interval, "Hamline Ave", 13, this, 0);
        stops[14] = new Stop(interval, "Lexington Pkwy", 14, this, 0);
        stops[15] = new Stop(interval, "Victoria St", 15, this, 0);
        stops[16] = new Stop(interval, "Dale St", 16, this, 0);
        stops[17] = new Stop(interval, "Western Ave", 17, this, 0);
        stops[18] = new Stop(interval, "Capitol/Rice St", 18, this, -10);
        stops[19] = new Stop(interval, "Robert St", 19, this, -10);
        stops[20] = new Stop(interval, "10th St", 20, this, -10);
        stops[21] = new Stop(interval, "Central", 21, this, -10);
        stops[22] = new Stop(interval, "Union Depot", 22, this, -10);
    }

    private void createTrain(){
        int index = 0;  //index of trains
        int j = 0;  //used for stop number
        //After this comment is where trains start being created
        Train tempTrain;
        //Eastbound Trains
        while (index < eastBoundTrains) {

            //CREATE TRAIN, if train is created at turnaround, direction is switched
            if (j != 22){
                tempTrain = new Train(index, numCars, 1, j, j+1, this);
                trains[index] = tempTrain;
            }
            //Stop 22 is the last stop, so have to switch the way it's going to make sense
            else {
                //j = current stop
                tempTrain = new Train(index, numCars, 0, j, 21, this);
                trains[index] = tempTrain;
            }
            //ADD TRAIN TO STOP
            if(j > numStops) break;
            stops[j].addTrain(tempTrain);

            j += eastSpacer;
            index++;
        }

        //Now starting at westbound
        //22 is stop 22, the last one in the east
        j = 22;  //set stop number to decrease from east stations

        //Westbound Trains

        while (index < numTrains){

            //CREATE TRAIN, if train is created at turnaround, direction is switched
            if (j != 0){
                tempTrain = new Train(index, numCars, 0, j, j-1, this);
                trains[index] = tempTrain;
            }
            else {
                tempTrain = new Train(index, numCars, 1, j, 1, this);
                trains[index] = tempTrain;
            }

            //ADD TRAIN TO STOP

            if(j <=0) break;
            stops[j].addTrain(tempTrain);

            j -= westSpacer;
            index++;
        }

        boolean err = false;
        System.out.println();
        for (Train train : trains) {
            System.out.println(train);
            if (train.getStop() > 22 || train.getStop() < 0) {
                err = true;
            }
        }

        if (err){
            System.out.println("ERROR INVALID STOP");
        }

    }

    public String toString(){
        return String.valueOf(eastBoundTrains) + " east bound trains\n" +
                westBoundTrains + " west bound trains\n" +
                eastSpacer + " eastSpacer\n" +
                westSpacer + " westSpacer\n";
    }

    public Stop[] getStops(){
       return stops;
    }


    public Agenda getAgenda(){
        return agenda;
    }


    public void addEvent(Event event, double time) {
        agenda.add(event, time);
    }

    public void addPassenger(int stopID, Passenger pass){
       stops[stopID].addPassenger(pass);
    }

    public Train [] getTrains(){
       return trains;
    }

    void start(){
        //TODO need fix logic
        //Adds Events
        for(Stop stop : stops){
            stop.createPassenger();
        }
        createTrain();
        for(Train train : trains){
            train.startTrain();
        }
        //Removes Events
        while (agenda.getCurrentTime() <= totalTime){
            agenda.remove().run();
        }
    }
}


