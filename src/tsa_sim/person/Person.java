package tsa_sim.person;

import java.util.Date;

public class Person {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final Date createdAt;
    //TODO: These should not be in this class for portability, an event listener should really record this.
    //Extend person.
    //First Queue entered (A or B)
    private Date queuedAt = null;
    //Second Queue entered
    private Date finalQueuedAt = null;
    //Queueing complete
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public Date getQueuedAt() {
        return queuedAt;
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

    public void setQueuedAt(Date queuedAt) {
        this.queuedAt = queuedAt;
    }

    public void setFinalQueuedAt(Date finalQueuedAt) {
        this.finalQueuedAt = finalQueuedAt;
    }
}