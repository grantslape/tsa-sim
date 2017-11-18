package tsa_sim;

import tsa_sim.person.Person;

import java.util.concurrent.LinkedBlockingQueue;

class PersonQueue extends LinkedBlockingQueue<Person> {

    public PersonQueue() {
        super();
    }
}