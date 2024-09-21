package ru.alex.nextpizzaapi.database.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PizzaType {
    TRADITIONAL, THIN;

    @JsonValue
    public int getValue() {
        return this.ordinal();
    }
}
