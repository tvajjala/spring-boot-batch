package com.tvajjala.batch.customer.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Customer Object attributes should match with customer.csv file columns
 */
public class Customer implements Serializable {


    private Integer id;

    private String username;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(username, customer.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username);
    }
}
