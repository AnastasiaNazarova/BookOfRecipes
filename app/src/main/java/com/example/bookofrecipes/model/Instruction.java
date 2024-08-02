package com.example.bookofrecipes.model;

import android.widget.TextView;

public class Instruction {

    String instruction;
    int number;

    public Instruction( int number,String instruction) {
        this.instruction = instruction;
        this.number = number;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "{" +
                "\"instruction\":\"" + instruction + "\"" +
                ", \"number\":\"" + number + "\"" +
                '}';
    }
}
