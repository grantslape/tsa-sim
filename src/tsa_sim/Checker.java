package tsa_sim;

class Checker {
    private final PersonQueue queue;

    public Checker(PersonQueue q) {
        this.queue = q;
    }

    private class processQueue implements Runnable {
        public void run() {
            //TODO: Actually process the queue's contents.
        }
    }

    private class checkQueueLength implements Runnable {
        public void run() {
            //TODO: Check the queue's length and adjust processing time if it is increasing
        }
    }

}