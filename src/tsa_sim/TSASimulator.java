package tsa_sim;

import tsa_sim.person.*;
import tsa_sim.Checker.*;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TSASimulator {
    private static final int PASSENGER_COUNT = 50;
    //max ticks for initial checker.  This needs to be higher than the passengerCount * tickValue.
    private static final int MAX_INITIAL_TIME = 100;
    private static final int BASE_TICK_VALUE = 1000;
    private final static Logger LOGGER = Logger.getLogger(TSASimulator.class.getName());
    private PersonBuilder personBuilder;
    //Length of a tick in milliseconds.
    private int tickValue;
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

    public TSASimulator(int passengerCount, int initialTime, int tickValue) {
        this.tickValue = tickValue;
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
                "Checker I");
        checkerA = new Checker(queueA, new PersonQueue[] {queueC}, this.tickValue, "Checker A");
        checkerB = new Checker(queueB, new PersonQueue[] {queueC}, this.tickValue, "Checker B");
        checkerC = new Checker(queueC, new PersonQueue[] {completedPool}, this.tickValue, "Checker C");

        for(int i = 0; i < passengerCount; i++) {
            passengerPool.add(personBuilder.buildPerson(i+1));
        }
    }

    public TreeSet<Date> generateTimes(int count, int length) {
        TreeSet<Date> times = new TreeSet<>();
        //5 second buffer for sim to begin
        long floor = System.currentTimeMillis();
        while(times.size() < count) {
            times.add(generateTime(new Date(floor), new Date(floor + tickValue * length)));
        }

        return times;
    }

    private static Date generateTime(Date floor, Date ceiling) {
        Random generator = new Random();
        return new Date(generator.nextInt((int)(ceiling.getTime() - floor.getTime())) + floor.getTime());
    }

    private void printPassengers() {
        for(Person person : this.passengerPool) {
            LOGGER.log(Level.INFO, String.format(
                    "Id: %d, Name: %s, createdAt: %s%n",
                    person.getId(),
                    person.getFullName(),
                    person.getCreatedAt().toString()));
        }
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillis = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillis,TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        //TODO: IF arg.length > 0 then switch on tick value.  Inject this.
        int tickValue = BASE_TICK_VALUE;
        int maxInitTime = MAX_INITIAL_TIME;
        int passCount = PASSENGER_COUNT;
        if (args.length >= 3) {
            try {
                tickValue = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Cannot parse tick value, using default");
                tickValue = BASE_TICK_VALUE;
            }
            try {
                maxInitTime = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Cannot parse initial time value, using default");
                maxInitTime = MAX_INITIAL_TIME;
            }
            try {
                passCount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Cannot parse passenger count, using default");
                passCount = PASSENGER_COUNT;
            }
        }

        TSASimulator simulator = new TSASimulator(passCount, maxInitTime, tickValue);

        simulator.printPassengers();
        Thread a = new Thread(simulator.checkerA);
        Thread b = new Thread(simulator.checkerB);
        Thread c = new Thread(simulator.checkerC);
        Thread d = new Thread(simulator.initialChecker);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date start = new Date();
        d.start();
        a.start();
        b.start();
        c.start();

        //Wait for initial checker to end
        while (d.isAlive()) {
            try {
                d.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                //try and recover
                System.out.println("Restarting: " + d.getName());
                d.start();
            }
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

        Date end = new Date();
        System.out.println("\n*** TSA SIMULATOR ***");
        System.out.println("Execution completed!");
        System.out.println("\nSTART: " + dateFormat.format(start));
        System.out.println("END: " + dateFormat.format(end));
        System.out.print("TIME ELAPSED: ");
        System.out.print(getDateDiff(start, end, TimeUnit.SECONDS) + " seconds");
    }
}