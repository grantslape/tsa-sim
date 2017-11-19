package tsa_sim.Checker;

import tsa_sim.person.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.max;

public class Checker implements CheckerInterface {
    private final PersonQueue queue;
    private final PersonQueue[] destination;
    //How many ticks should the queue be expedited by.
    private int timeModifier;
    private int previousLength;
    private int tick;
    private String name;

    public Checker(PersonQueue origin, PersonQueue[] destination, int tick, String name) {
        this.queue = origin;
        this.destination = destination;
        this.name = name;
        timeModifier = 0;
        previousLength = 0;
        //tick is is seconds on front-end, milliseconds on backend
        this.tick = 1000 * tick;
    }

    public void run() {
        Thread.currentThread().setName(name);
        while (true) {
            try {
                if (queue.isEmpty()) {
                    Thread.sleep(tick);
                } else {
                    Thread.sleep(tick * max((ThreadLocalRandom.current().nextInt(15)+ 1 - timeModifier), 1));
                    process(queue.take());
                }

                if (previousLength < queue.size()) {
                    timeModifier++;
                } else if (previousLength > queue.size()) {
                    if (timeModifier > 0) {
                        timeModifier--;
                    }
                }
                previousLength = queue.size();
            } catch (InterruptedException e) {
                CheckerInterface.threadMessage("Ending execution by interrupt");
                return;
            }
        }
    }

    public void process(Person person) {
        //Set the earliest null timestamp
        CheckerInterface.stamp(person);
        System.out.format(
                "Checker: %s processed: Id: %d, Name: %s, createdAt: %s, queuedAt: %s, finalQueuedAt: %s, completedAt: %s%n",
                Thread.currentThread().getName(),
                person.getId(),
                person.getFullName(),
                person.getCreatedAt().toString(),
                person.getQueuedAt() == null ? null : person.getQueuedAt().toString(),
                person.getFinalQueuedAt() == null ? null : person.getFinalQueuedAt().toString(),
                person.getCompletedAt() == null ? null : person.getCompletedAt().toString());
        if (destination.length > 1) {
            destination[ThreadLocalRandom.current().nextInt(destination.length)].add(person);
        } else {
            destination[0].add(person);
        }
    }
}