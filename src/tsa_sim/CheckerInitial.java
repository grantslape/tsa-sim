//package tsa_sim;
//
//import tsa_sim.person.Person;
//
//import java.util.Date;
//import java.util.Random;
//
//public class CheckerInitial extends Checker {
//
//      private final PersonQueue queue;
//      private final PersonQueue[] destination;
////    //How many ticks should the queue be expedited by.
////    private int timeModifier;
////    private int previousLength;
////    private int tick;
////    private String name;
//
//    public CheckerInitial(PersonQueue q, PersonQueue[] destination, int tick, String name) {
//        this.queue = q;
//        this.destination = destination;
//        this.name = name;
//        timeModifier = 0;
//        previousLength = 0;
//        //tick is is seconds on front-end, milliseconds on backend
//        this.tick = 1000 * tick;
//    }
//
//
//    private void process(Person person) {
//        String threadName =
//                Thread.currentThread().getName();
//        System.out.format(
//                "Checker: %s Id: %d, Name: %s, createdAt: %s%n",
//                threadName,
//                person.getId(),
//                person.getFullName(),
//                person.getCreatedAt().toString());
//        //TODO: fix this when adding another queueing timestamp
//        if (person.getFinalQueuedAt() == null) {
//            person.setFinalQueuedAt(new Date());
//        } else {
//            person.setCompletedAt(new Date());
//        }
//        Random generator = new Random();
//        destination[generator.nextInt(destination.length)].add(person);
//    }
//}
