package tsa_sim;

import jdk.internal.jline.internal.Nullable;

import java.util.Date;

class Person {
    private final int id;
    //This is also when they enter the first queue
    private final Date createdAt;
    //Second Queue entered
    @Nullable
    private Date finalQueuedAt = null;
    //Queueing complete
    @Nullable
    private Date completedAt = null;

    public Person(int id, Date createdAt) {
        this.createdAt = createdAt;
        this.id = id;
    }

    public Person() {
        this.createdAt = new Date();
        this.id = 0;
    }

    /*
     * GETTERS
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public Date getFinalQueuedAt() {
        return finalQueuedAt;
    }

    /*
     * SETTERS
     */

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public void setFinalQueuedAt(Date finalQueuedAt) {
        this.finalQueuedAt = finalQueuedAt;
    }
}