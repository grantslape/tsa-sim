package tsa_sim;

import tsa_sim.person.Person;

import java.util.Date;
import java.util.Random;

class Checker implements Runnable {
    protected final PersonQueue queue;
    protected final PersonQueue[] destination;
    //How many ticks should the queue be expedited by.
    protected int timeModifier;
    protected int previousLength;
    protected int tick;
    protected String name;
    protected Random generator;

    public Checker(PersonQueue q, PersonQueue[] destination, int tick, String name) {
        this.queue = q;
        this.destination = destination;
        this.name = name;
        timeModifier = 0;
        previousLength = 0;
        //tick is is seconds on front-end, milliseconds on backend
        this.tick = 1000 * tick;
        this.generator = new Random();
    }

//    public Checker() {
//        this.queue = null;
//        this.destination = null;
//        timeModifier = 0;
//        previousLength = 0;
//        this.tick = 0;
//        this.name = "default name";
//    }

    public void run() {
        Thread.currentThread().setName(name);
        while (true) {
            try {
                if (queue.isEmpty()) {
                    Thread.sleep(tick);
                } else {
                    Thread.sleep(tick * generator.nextInt(15)+1-timeModifier);
                    process((Person) queue.take());
                }
            } catch (InterruptedException e) {
                threadMessage("Done");
            }
        }
    }

    private void process(Person person) {
        String threadName =
                Thread.currentThread().getName();
        System.out.format(
                "Checker: %s Id: %d, Name: %s, createdAt: %s%n",
                threadName,
                person.getId(),
                person.getFullName(),
                person.getCreatedAt().toString());
        //TODO: fix this when adding another queueing timestamp
        if (person.getFinalQueuedAt() == null) {
            person.setFinalQueuedAt(new Date());
        } else {
            person.setCompletedAt(new Date());
        }
        if (destination.length > 1) {
            destination[generator.nextInt(destination.length)].add(person);
        } else {
            destination[0].add(person);
        }
    }

    // Display a message, preceded by
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName =
                Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                threadName,
                message);
    }

    public void setName(String name) {
        this.name = name;
    }
}