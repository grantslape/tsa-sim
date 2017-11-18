package tsa_sim.Checker;

import tsa_sim.person.Person;

public interface CheckerInterface extends Runnable {

    void run();

    void process(Person person);
}
