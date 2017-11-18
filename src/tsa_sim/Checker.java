package tsa_sim;

import tsa_sim.person.Person;

import java.util.Date;
import java.util.Random;

class Checker implements Runnable {
    private final PersonQueue queue;
    private final PersonQueue[] destination;
    //How many ticks should the queue be expedited by.
    private int timeModifier;
    private int previousLength;
    private int tick;
    private String name;
    //TODO: use threaded random generator.
    private Random generator;

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

    public void run() {
        Thread.currentThread().setName(name);
        while (true) {
            try {
                if (queue.isEmpty()) {
                    Thread.sleep(tick);
                } else {
                    Thread.sleep(tick * generator.nextInt(15)+1-timeModifier);
                    process(queue.take());
                }
            } catch (InterruptedException e) {
                threadMessage("Done");
            }
        }
    }

    private void process(Person person) {
        String threadName = Thread.currentThread().getName();
        //Set the earliest null timestamp
        if (person.getQueuedAt() == null) {
            person.setQueuedAt(new Date());
        } else if (person.getFinalQueuedAt() == null) {
            person.setFinalQueuedAt(new Date());
        } else {
            person.setCompletedAt(new Date());
        }
        System.out.format(
                "Checker: %s processed: Id: %d, Name: %s, createdAt: %s, queuedAt: %s, finalQueuedAt: %s, completedAt: %s%n",
                threadName,
                person.getId(),
                person.getFullName(),
                person.getCreatedAt().toString(),
                person.getQueuedAt() == null ? null : person.getQueuedAt().toString(),
                person.getFinalQueuedAt() == null ? null : person.getFinalQueuedAt().toString(),
                person.getCompletedAt() == null ? null : person.getFinalQueuedAt().toString());
        //TODO: use threaded random generator.
        if (destination.length > 1) {
            destination[generator.nextInt(destination.length)].add(person);
        } else {
            destination[0].add(person);
        }
    }

    // Display a message, preceded by
    // the name of the current thread
    //TODO: Do we need this?
    static void threadMessage(String message) {
        String threadName =
                Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                threadName,
                message);
    }
}