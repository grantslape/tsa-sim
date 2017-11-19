package tsa_sim.Checker;

import tsa_sim.person.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private DateFormat dateFormat;

    public Checker(PersonQueue origin, PersonQueue[] destination, int tick, String name) {
        this.queue = origin;
        this.destination = destination;
        this.name = name;
        timeModifier = 0;
        previousLength = 0;
        //tick is is seconds on front-end, milliseconds on backend
        this.tick = tick;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public void run() {
        Thread.currentThread().setName(name);
        while (true) {
            try {
                if (queue.isEmpty()) {
                    Thread.sleep(tick);
                } else {
                    Thread.sleep(tick * max((ThreadLocalRandom.current().nextInt(15) + 1 - timeModifier), 1));
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
        CheckerInterface.threadMessage(String.format(
                "%s processed: Id: %d, Name: %s, createdAt: %s, queuedAt: %s, finalQueuedAt: %s, completedAt: %s",
                Thread.currentThread().getName(),
                person.getId(),
                person.getFullName(),
                dateFormat.format(person.getCreatedAt()),
                person.getQueuedAt() == null ? null : dateFormat.format(person.getQueuedAt()),
                person.getFinalQueuedAt() == null ? null : dateFormat.format(person.getFinalQueuedAt()),
                person.getCompletedAt() == null ? null : dateFormat.format(person.getCompletedAt())));
        if (destination.length > 1) {
            destination[ThreadLocalRandom.current().nextInt(destination.length)].add(person);
        } else {
            destination[0].add(person);
        }
    }
}