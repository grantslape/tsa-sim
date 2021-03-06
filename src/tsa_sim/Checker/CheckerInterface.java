package tsa_sim.Checker;

import tsa_sim.person.Person;

import java.util.Date;

public interface CheckerInterface extends Runnable {

    void run();

    void process(Person person);

    //TODO: Perform this in observer
    static void stamp(Person person) {
        if (person.getQueuedAt() == null) {
            person.setQueuedAt(new Date());
        } else if (person.getFinalQueuedAt() == null) {
            person.setFinalQueuedAt(new Date());
        } else {
            person.setCompletedAt(new Date());
        }
    }
}
