package tsa_sim;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TSASimulator {

    private Checker checkerA;
    private Checker checkerB;
    private Checker checkerC;
    private ArrayBlockingQueue<Person> passengerPool;
    private LinkedBlockingQueue<Person> queueA;
    private LinkedBlockingQueue<Person> queueB;
    private LinkedBlockingQueue<Person> queueC;


    public static void main(String[] args) {
        //TODO: run the simulation
        System.out.println("do things");
    }
}