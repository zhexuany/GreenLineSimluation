/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */

package edu.umn.cs.GreenLineSimulation.Train;


import edu.umn.cs.GreenLineSimulation.GreenLineSim;
import edu.umn.cs.GreenLineSimulation.Passenger.Passenger;
import edu.umn.cs.GreenLineSimulation.Queue.LinkedListQueue;
import java.util.ArrayList;

public class Train {
    private int trainID;
    private int cars;
    private int direction;  //0 = west, 1 = east
    private int currentStop;
    private int nextStop;
    private TrainCar[] carArr;
    private GreenLineSim gSim;

    public Train (int ID, int numCars, int direction, int currentStop, int nextStop, GreenLineSim gSim){
        trainID = ID;
        cars = numCars;
        carArr = new TrainCar[cars];
        for(int i = 0; i < cars; i++){
            carArr[i] = new TrainCar();
        }
        this.direction = direction;
        this.currentStop = currentStop;
        this.nextStop = nextStop;
        this.gSim = gSim;
    }

    void setCurrentStop(int s){
        currentStop = s;
    }

    public int getStop(){
        return currentStop;
    }

    int getNextStop(){
        return nextStop;
    }

    int getDirection() { return direction; }

    void generateNextStop(){
        //sets next stop. If train is at a turnaround, changes direction and sets next stop accordingly
        switch (direction){
            //westbound Trains
            case 0:
                if (currentStop != 0){
                    nextStop --;
                }
                else {
                    direction = 1;
                    nextStop = 1;
                }
                break;
            //eastbound Trains
            case 1:
                if (currentStop != 22){
                    nextStop++;
                }
                else {
                    direction = 0;
                    nextStop = 21;
            }
                break;
            default:
                throw new IllegalStateException("Direction can only be 0 or 1");
        }
    }

    public String toString(){
        return  "Train: " + trainID + " currentStop: " + currentStop + " direction: " + directionToString() + " nextStop: " + nextStop;
    }

    private String directionToString(){
        if (direction == 0){
            return  "west";
        }
        else if (direction == 1){
             return  "east";
        }
        return "";
    }

    public int getCars() {
        return cars;
    }

    public void startTrain() {
        gSim.addEvent(new TrainEvent(this, gSim), 0);
    }

    ArrayList<Passenger> removePassengers(){
        ArrayList<Passenger> res = new ArrayList<>();
        for(TrainCar car : carArr){
           res.addAll(car.removePassengers(currentStop));
        }
        return res;
    }

    LinkedListQueue addPassengers(LinkedListQueue passengers){
        for(TrainCar car : carArr){
            while(!car.isFull()){
                Passenger pass = (Passenger) passengers.remove();
                if(pass == null)
                    break;
                car.addPassengers((Passenger) passengers.remove());
            }
        }
        //return left passengers
        return passengers;
    }

}
