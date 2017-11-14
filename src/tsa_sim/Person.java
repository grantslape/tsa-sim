package tsa_sim;

import java.util.Date;

class Person {
    private Date createdAt;

    public Person(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Person() {
        this.createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}