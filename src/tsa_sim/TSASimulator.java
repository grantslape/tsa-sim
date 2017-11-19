package tsa_sim;

import tsa_sim.person.*;
import tsa_sim.Checker.*;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Random;
import java.util.TreeSet;

public class TSASimulator {
    private static final int PASSENGER_COUNT = 50;
    //max ticks for initial checker.
    private static final int MAX_INITIAL_TIME = 10;
    private PersonBuilder personBuilder;
    //Length of a tick in seconds, default to 1 second.
    private int tickValue = 1;

    private Checker checkerA;
    private Checker checkerB;
    private Checker checkerC;
    private OrderedChecker initialChecker;
    //TODO: Maybe this should be a priority queue and we assign a pop time at creation?
    private PersonQueue passengerPool;
    private PersonQueue completedPool;
    private PersonQueue queueA;
    private PersonQueue queueB;
    private PersonQueue queueC;

    public TSASimulator(int passengerCount, int initialTime) {
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
        initialChecker = new OrderedChecker(
                passengerPool,
                new PersonQueue[] {queueA, queueB},
                generateTimes(passengerCount, initialTime),
                tickValue,
                "Checker I");
        checkerA = new Checker(queueA, new PersonQueue[] {queueC}, tickValue, "Checker A");
        checkerB = new Checker(queueB, new PersonQueue[] {queueC}, tickValue, "Checker B");
        checkerC = new Checker(queueC, new PersonQueue[] {completedPool}, tickValue, "Checker C");

        for(int i = 0; i < passengerCount; i++) {
            passengerPool.add(personBuilder.buildPerson(i+1));
        }
    }

    public TreeSet<Date> generateTimes(int count, int length) {
        TreeSet<Date> times = new TreeSet<>();
        //5 second buffer for sim to begin
        long floor = System.currentTimeMillis();
        while(times.size() < count) {
            times.add(generateTime(new Date(floor), new Date(floor + this.tickValue * length * 1000)));
        }

        for(Date time : times) {
            System.out.println(time.toString());
        }

        return times;
    }

    private static Date generateTime(Date floor, Date ceiling) {
        Random generator = new Random();
        return new Date(generator.nextInt((int)(ceiling.getTime() - floor.getTime())) + floor.getTime());
    }

    public static void main(String[] args) throws InterruptedException {
        //TODO: IF arg.length > 0 then switch on tick value.
        TSASimulator simulator = new TSASimulator(PASSENGER_COUNT, MAX_INITIAL_TIME);
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

        //Wait for initial checker to end
        while (d.isAlive()) {
            d.join();
        }

        while ( a.isAlive() || b.isAlive()) {
            if(a.isAlive() && simulator.queueA.isEmpty()) {
                a.interrupt();
            }
            if(b.isAlive() && simulator.queueB.isEmpty()) {
                b.interrupt();
            }
        }
        while (c.isAlive()) {
            if (simulator.queueC.isEmpty()) {
                c.interrupt();
            }
        }

        System.out.println("\nExecution completed!");
        //clean up
        d.interrupt();
    }
}