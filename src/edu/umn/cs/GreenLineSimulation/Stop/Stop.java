package edu.umn.cs.GreenLineSimulation.Stop;


import edu.umn.cs.GreenLineSimulation.Agenda.Agenda;
import edu.umn.cs.GreenLineSimulation.GreenLineSim;
import edu.umn.cs.GreenLineSimulation.Passenger.Passenger;
import edu.umn.cs.GreenLineSimulation.Passenger.PassengerMaker;
import edu.umn.cs.GreenLineSimulation.Queue.*;
import edu.umn.cs.GreenLineSimulation.Train.Train;

/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */


public class Stop {

    private String name;
    private LinkedListQueue westPassengers = new LinkedListQueue();
    private LinkedListQueue eastPassengers = new LinkedListQueue();
    private LinkedListQueue trains = new LinkedListQueue();
    private GreenLineSim gSim;

    private int stopID;
    private int interval; //average arrival interval, changes based on downtown/campus stops via intervalModifier

    public Stop(int interval, String stopName,
                int idNum, GreenLineSim gSim,
                int intervalModifier){
        this.interval = interval;
        name = stopName;
        this.westPassengers = new LinkedListQueue();
        this.eastPassengers = new LinkedListQueue();
        stopID = idNum;
        this.gSim = gSim;
        this.interval += intervalModifier;  //-10 for downtown stops, -5 for campus stops
    }

    public void addTrain(Train t){
        trains.add(t);
    }

    public int getId(){
        return stopID;
    }

    public void addPassenger(Passenger p){
        //System.out.println(p);
        if (p.getDirection() == 0){
            westPassengers.add(p);
        }
        else if (p.getDirection() == 1){
            eastPassengers.add(p);
        }
        else{
            throw new IllegalStateException("Stop.java: Direction can only be 0 or 1");
        }

    }

    public void setWestPassengers(LinkedListQueue passengers){
       westPassengers = passengers;
    }

    public void setEastPassengers(LinkedListQueue passengers){
       eastPassengers = passengers;
    }

    public LinkedListQueue getWestPassengers(){
        return westPassengers;
    }
    public LinkedListQueue getEastPassengers(){
       return eastPassengers;
    }

    public void createPassenger(){
        //every stop will create passenger after each interval
        gSim.addEvent(new PassengerMaker(stopID, interval, null, gSim), 0);
    }

    private String getName(){
        return name;
    }

    public int numPeople(){
        return westPassengers.length() + eastPassengers.length();
    }

    public String toString(){
       return numPeople() + " passengers" + " currently at Stop: " + getName() + " "+ getId();
    }
}
