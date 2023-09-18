package com.example.mafiagame.model;

import com.example.mafiagame.enums.Role;

public class Player {
    private int number;
    private boolean mafia;
    private Role role;
    private boolean alive;
    private int faults;

    public Player(boolean mafia, Role role) {
        this.mafia = mafia;
        this.role = role;
        this.alive = true;
        this.faults = 0;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMafia() {
        return mafia;
    }

    public void setMafia(boolean mafia) {
        this.mafia = mafia;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void addFault() {
        this.faults++;
    }

    public void minusFault() {
        this.faults--;
    }

    public void clearFaults() {
        this.faults = 0;
    }

    public int getFaults() {
        return this.faults;
    }
}
