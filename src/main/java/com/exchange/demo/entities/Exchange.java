package com.exchange.demo.entities;

import javax.persistence.*;

/**
 * Created by vidur on 20/01/18.
 */
@Entity
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    public Exchange() {

    }

    public Exchange(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
