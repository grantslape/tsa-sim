package tsa_sim;

import tsa_sim.person.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class TSASimulator {
    private static final int PASSENGER_COUNT = 50;
    private PersonBuilder personBuilder;
    //Length of a tick in seconds, default to 1 second.
    private int tickValue = 1;

    private Checker checkerA;
    private Checker checkerB;
    private Checker checkerC;
    private Checker initialChecker;
    //TODO: Maybe this should be a priority queue and we assign a pop time at creation?
    private PersonQueue passengerPool;
    private PersonQueue completedPool;
    private PersonQueue queueA;
    private PersonQueue queueB;
    private PersonQueue queueC;

    public TSASimulator(int passengerCount) {
        try {
            personBuilder = new PersonBuilder();
        } catch (FileNotFoundException e) {
            //TODO: log
            e.printStackTrace();
        }
        queueA = new PersonQueue();
        queueB = new PersonQueue();
        queueC = new PersonQueue();
        completedPool = new PersonQueue();
        passengerPool = new PersonQueue();
        initialChecker = new Checker(passengerPool, new PersonQueue[] {queueA, queueB}, tickValue, "Checker I");
        checkerA = new Checker(queueA, new PersonQueue[] {queueC}, tickValue, "Checker A");
        checkerB = new Checker(queueB, new PersonQueue[] {queueC}, tickValue, "Checker B");
        checkerC = new Checker(queueC, new PersonQueue[] {completedPool}, tickValue, "Checker C");

        for(int i = 0; i < passengerCount; i++) {
            passengerPool.add(personBuilder.buildPerson(i+1));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //IF arg.length > 0 then switch on tick value.
        TSASimulator simulator = new TSASimulator(PASSENGER_COUNT);
        Random generator = new Random();

        //TODO: run the simulation
        System.out.println("*** TSA SIMULATOR ***");
        for(Object obj : simulator.passengerPool) {
            Person person = (Person) obj;
            System.out.format(
                    "Id: %d, Name: %s, createdAt: %s%n",
                    person.getId(),
                    person.getFullName(),
                    person.getCreatedAt().toString());
        }

        Thread a = new Thread(simulator.checkerA);
        Thread b = new Thread(simulator.checkerB);
        Thread c = new Thread(simulator.checkerC);
        Thread d = new Thread(simulator.initialChecker);
        d.start();
        a.start();
        b.start();
        c.start();

        //TODO: Process the inoput

//        a.join();
//        b.join();
//        c.join();
    }
}