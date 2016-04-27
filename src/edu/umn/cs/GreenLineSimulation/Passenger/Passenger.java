/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */

package edu.umn.cs.GreenLineSimulation.Passenger;

public class Passenger {
    private double arrivalTime;
    private double finalTime;
    private int departureStop;
    private int destinationStop;
    private int direction;  //0 = west, 1 = east

    Passenger( double t, double fin, int departureStop, int destinationStop){
        arrivalTime = t;
        finalTime = fin;

        this.departureStop = departureStop;
        this.destinationStop = destinationStop;

        if (destinationStop < departureStop){
            direction = 0;
        }
        else if (destinationStop > departureStop){
            direction = 1;
        }
        else {
            throw new IllegalArgumentException("Passenger's departure Stop and destination stop can't be same");
        }
    }

    public void setFinalTime(double time){
        finalTime = time;
    }

    public double getFinalTime(){
        return finalTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public int getDirection(){
        return direction;
    }

    public int getDestination(){
        return destinationStop;
    }

    private int getDepartureStop(){
        return departureStop;
    }

    //Added in to analyze passengerArray Data
    public String toString() {
        return "Passenger Info: " +
                "\n=================" +
                "\nDeparture: " + getDepartureStop() +
                "\nArrived at Depart Stop: " + getArrivalTime() +
                "\nDestination: " + getDestination() +
                "\nArrived at Destination Stop: " + getFinalTime() +
                "\nTotal time waited: " + (getFinalTime() - getArrivalTime()) +
                "\n--------------------\n\n";
    }

}
