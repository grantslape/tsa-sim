package tsa_sim;

class Checker {
    private final PersonQueue queue;
    //How many ticks should the queue be expedited by.
    private int timeModifier;

    public Checker(PersonQueue q) {
        this.queue = q;
        timeModifier = 0;
    }

    private class processQueue implements Runnable {
        public void run() {
            //TODO: Actually process the queue's contents.
        }
    }

    //TODO: might not need this, could just do it in the queue processing.
    private class checkQueueLength implements Runnable {
        public void run() {
            //Check the queue's length and adjust processing time if it is increasing
        }
    }

}