package tsa_sim.Checker;

import tsa_sim.person.Person;
import tsa_sim.person.PersonQueue;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class OrderedChecker implements CheckerInterface {
    private final PersonQueue queue;
    private final PersonQueue[] destination;
    private TreeSet<Date> popTimes;
    private int tick;
    private String name;

    public OrderedChecker(PersonQueue origin, PersonQueue[] destination, TreeSet<Date> popTimes, int tick, String name) {
        this.queue = origin;
        this.destination = destination;
        this.name = name;
        this.tick = 1000 * tick;
        this.popTimes = popTimes;
    }
    /*
     * If times aren't in the future, nothing good will happen
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
