package tsa_sim.Checker;

import tsa_sim.person.Person;
import tsa_sim.person.PersonQueue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class OrderedChecker implements CheckerInterface {
    private final PersonQueue queue;
    private final PersonQueue[] destination;
    private TreeSet<Date> popTimes;
    private String name;
    private DateFormat dateFormat;

    public OrderedChecker(PersonQueue origin, PersonQueue[] destination, TreeSet<Date> popTimes, String name) {
        this.queue = origin;
        this.destination = destination;
        this.name = name;
        this.popTimes = popTimes;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
    /*
     * If times are in the past objects will pop as soon as execution starts
     */
    public void setProcessTimes(TreeSet<Date> times) {
        this.popTimes = times;
    }

    public void run() {
        Thread.currentThread().setName(name);
        while (!popTimes.isEmpty()) {
            if( System.currentTimeMillis() >= popTimes.first().getTime()) {
                popTimes.remove(popTimes.first());
                try {
                    process(queue.take());
                } catch (InterruptedException e) {
                    CheckerInterface.threadMessage("Ending by interrupt");
                    return;
                }
            }
        }

        CheckerInterface.threadMessage("No more times, ending execution.");
    }

    public void process(Person person) {
        //Set the earliest null timestamp
        CheckerInterface.stamp(person);
        CheckerInterface.threadMessage(String.format(
                "%s processed: Id: %d, Name: %s, createdAt: %s, queuedAt: %s",
                Thread.currentThread().getName(),
                person.getId(),
                person.getFullName(),
                dateFormat.format(person.getCreatedAt()),
                person.getQueuedAt() == null ? null : dateFormat.format(person.getQueuedAt())));
        if (destination.length > 1) {
            destination[ThreadLocalRandom.current().nextInt(destination.length)].add(person);
        } else {
            destination[0].add(person);
        }

    }
}
