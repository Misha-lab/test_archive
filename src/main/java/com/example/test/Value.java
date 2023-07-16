package com.example.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "val")
public class Value {
    @Id
    @GeneratedValue
    private int id;

    private int val;

    public Value() {}

    public Value(int value) {
        this.val = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setVal(String value) {
        this.val = Integer.parseInt(value);
    }

    public String toString() {
        return String.valueOf(val);
    }

    public Value increment() {
        val++;
        return this;
    }
}
