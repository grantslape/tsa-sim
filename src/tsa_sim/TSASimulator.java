package tsa_sim;

import tsa_sim.person.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class TSASimulator {
    private final int PASSENGER_COUNT = 50;
    //Length of a tick in seconds, default to 1 second.
    private int tickValue = 1;

    private Checker checkerA;
    private Checker checkerB;
    private Checker checkerC;
    //TODO: Maybe this should be a priority queue and we assign a pop time at creation?
    private ArrayBlockingQueue<Person> passengerPool;
    //We don't need a synchronized data structure for the end, because there is only one entry point.
    private ArrayList<Person> completedPool;
    private PersonQueue queueA;
    private PersonQueue queueB;
    private PersonQueue queueC;


    public static void main(String[] args) {
        //TODO: set up the objects
        //IF arg.length > 0 then switch on tick value.
        //TODO: run the simulation
        System.out.println("*** TSA SIMULATOR ***");
    }
}