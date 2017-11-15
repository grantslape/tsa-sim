package tsa_sim;

import tsa_sim.person.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class TSASimulator {
    private static final int PASSENGER_COUNT = 50;
    private PersonBuilder personBuilder;
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

    public TSASimulator(int passengerCount) {
        //TODO: set up the objects
        try {
            personBuilder = new PersonBuilder();
        } catch (FileNotFoundException e) {
            //TODO: log
            e.printStackTrace();
        }
        queueA = new PersonQueue();
        queueB = new PersonQueue();
        queueC = new PersonQueue();
        checkerA = new Checker(queueA);
        checkerB = new Checker(queueB);
        checkerC = new Checker(queueC);

        completedPool = new ArrayList<>();
        passengerPool = new ArrayBlockingQueue<>(passengerCount);
        for(int i = 0; i < passengerCount; i++) {
            passengerPool.add(personBuilder.buildPerson(i+1));
        }
    }

    public static void main(String[] args) {
        //IF arg.length > 0 then switch on tick value.
        TSASimulator simulator = new TSASimulator(PASSENGER_COUNT);

        //TODO: run the simulation
        System.out.println("*** TSA SIMULATOR ***");
        for(Person person : simulator.passengerPool) {
            System.out.printf(
                    "Id: %d, Name: %s, createdAt: %s",
                    person.getId(),
                    person.getFullName(),
                    person.getCreatedAt().toString());
            System.out.print('\n');
        }
    }
}