package tsa_sim.person;

import jdk.internal.jline.internal.Nullable;

import java.util.Date;

public class Person {
    private final int id;
    private final String firstName;
    private final String lastName;
    //This is also when they enter the first queue
    private final Date createdAt;
    //Second Queue entered
    @Nullable
    private Date finalQueuedAt = null;
    //Queueing complete
    @Nullable
    private Date completedAt = null;

    public Person(int id, Date createdAt, String firstName, String lastName) {
        this.createdAt = createdAt;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFullName() {
        return firstName + lastName;
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