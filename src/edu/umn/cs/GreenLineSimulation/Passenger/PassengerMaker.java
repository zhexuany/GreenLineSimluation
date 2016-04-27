package edu.umn.cs.GreenLineSimulation.Passenger;

import edu.umn.cs.GreenLineSimulation.Agenda.Agenda;
import edu.umn.cs.GreenLineSimulation.Event.Event;
import edu.umn.cs.GreenLineSimulation.GreenLineSim;
import edu.umn.cs.GreenLineSimulation.Stop.Stop;

/**
 * Created by Zhexuan Yang on 4/15/16.
 * Copyright (C) 2016
 */

//        10% of the time: 75% above average arrival interval (30 + .75 * 30)
//        15% of the time: 50% above average arrival interval (30 + .50 * 30)
//        20% of the time: 20% above average arrival interval (30 + .20 * 30)
//        10% of the time: right at average arrival interval (30)
//        20% of the time: 20% below average arrival interval (30 - .20 * 30)
//        15% of the time: 50% below average arrival interval (30 - .50 * 30)
//        10% of the time: 75% below average arrival interval (30 - .75 * 30)

public class PassengerMaker implements Event {

    private int interval;  //average arrival interval
    private int prob;  //probability used with arrival interval (see comments above)
    private int departure;
    private int destination;
    private Stop stopIndex;
    private GreenLineSim gSim;

    public PassengerMaker(int departureStop, int averageInterval, Stop s, GreenLineSim gSim){
        departure = departureStop;
        destination = departureStop;
        interval = averageInterval;
        stopIndex = s;
        this.gSim = gSim;
    }

    private double arrivalModifier(){
        prob = intRandomInterval(1, 100);
        if (prob >= 1 && prob <= 10){
            return .75;
        }
        else if (prob >= 11 && prob <= 25){
            return .5;
        }
        else if (prob >= 26 && prob <= 45){
            return .2;
        }
        else if (prob >= 46 && prob <= 55){
            return 0;
        }
        else if (prob >= 56 && prob <= 75){
            return -.2;
        }
        else if (prob >= 76 && prob <= 90){
            return -.5;
        }
        else if (prob >= 91 && prob <= 100){
            return -.75;
        }
        return 0;
    }

    private int destinationGenerate(){
        prob = intRandomInterval(1, 69);
        if (prob >= 1 && prob <= 50){  //10 downtown stops, 5 times more likely than other stops

            prob = intRandomInterval(1, 10);
            int stopNum;
            switch (prob) {
                case 1:
                    stopNum = 0;
                break;
                case 2:
                    stopNum = 1;
                break;
                case 3:
                    stopNum = 2;
                break;
                case 4:
                    stopNum = 3;
                break;
                case 5:
                    stopNum = 4;
                break;
                case 6:
                    stopNum = 18;
                break;
                case 7:
                    stopNum = 19;
                break;
                case 8:
                    stopNum = 20;
                break;
                case 9:
                    stopNum = 21;
                break;
                case 10:
                    stopNum = 22;
                break;
                default:
                    stopNum = -1;
                break;
            }
            return stopNum;
        }

        else if (prob >= 51 && prob <= 59){   //3 campus stops, 3 times as likely than other stops
            return intRandomInterval(5, 7);
        }

        else if (prob >= 60 && prob <= 69){   //10 other stops
            return intRandomInterval(8, 17);
        }

        else{
            System.out.println("destinationGenerate error, prob: " + prob);
            return -1;
        }
    }

    private int intRandomInterval(int low, int high) {
        return (int) Math.floor((high - low) * Math.random() + low + 0.5);
    }

    @Override
    public void run() {

        //makes sure passengers don't have duplicate starting points and departures
        while (destination == departure){
            destination = destinationGenerate();
        }

        //create new passenger
        gSim.addPassenger(departure, new Passenger(gSim.getAgenda().getCurrentTime(), 0.0,
                departure, destination));

        //schedule new PassengerMaker according to intervalModifier
        gSim.addEvent(new PassengerMaker(departure, interval, null, gSim), interval + arrivalModifier() * interval);
    }
}
