package tsa_sim.Checker;

import javafx.beans.Observable;
import tsa_sim.person.Person;

import java.util.Date;

import static java.lang.Thread.*;

public interface CheckerInterface extends Runnable, Observable {

    void run();

    void process(Person person);

    void notifyObservers(Object person);

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
