package tsa_sim.Checker;

import tsa_sim.person.Person;

import java.util.Date;

import static java.lang.Thread.*;

public interface CheckerInterface extends Runnable {

    void run();

    void process(Person person);

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
