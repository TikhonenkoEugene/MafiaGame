package com.example.mafiagame.repos;

import com.example.mafiagame.enums.Role;
import com.example.mafiagame.model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repo {
    private static Repo repo;
    private List<Player> allPlayers;
    private List<Player> shottedPlayers;
    private List<Player> votedPlayers;
    private int numberOfPlayers;
    private int numberOfMafia;
    private int numberOfDay;
    private Player currentPlayer;
    private boolean sportMode;
    private boolean showDied;
    private boolean hasDon;
    private boolean hasCop;
    private boolean hasDoctor;
    private boolean hasWhore;
    private boolean hasManiac;
    private boolean hasLucky;

    private Repo() {
        this.allPlayers = new ArrayList<>();
        this.shottedPlayers = new ArrayList<>();
        this.votedPlayers = new ArrayList<>();
        this.numberOfPlayers = 10;
        this.numberOfMafia = 3;
        this.numberOfDay = 6;
        this.hasDon = true;
        this.hasCop = true;
        this.hasDoctor = false;
        this.hasWhore = false;
        this.hasManiac = false;
        this.hasLucky = false;
        this.sportMode = false;
        this.showDied = false;
    }

    public static Repo get() {
        if (repo == null) {
            repo = new Repo();
        }
        return repo;
    }

    public void destroy() {
        repo = null;
    }

    public void makeRoles() {
        int counterCivilianRoles = 0;
        if (hasDon) {
            allPlayers.add(new Player(true, Role.DON));
        }
        if (hasCop) {
            allPlayers.add(new Player(false, Role.COP));
            counterCivilianRoles++;
        }
        if (hasDoctor) {
            allPlayers.add(new Player(false, Role.DOCTOR));
            counterCivilianRoles++;
        }
        if (hasWhore) {
            allPlayers.add(new Player(false, Role.WHORE));
            counterCivilianRoles++;
        }
        if (hasManiac) {
            allPlayers.add(new Player(false, Role.MANIAC));
            counterCivilianRoles++;
        }
        if (hasLucky) {
            allPlayers.add(new Player(false, Role.LUCKY));
            counterCivilianRoles++;
        }

        int numberOfCivilian = numberOfPlayers - numberOfMafia - counterCivilianRoles;
        for (int i = 1; i <= numberOfCivilian; i++) {
            allPlayers.add(new Player(false, Role.CIVILIAN));
        }

        int mafiaCounter = hasDon ? numberOfMafia - 1 : numberOfMafia;
        for (int i = 1; i <= mafiaCounter; i++) {
            allPlayers.add(new Player(true, Role.MAFIA));
        }

        Collections.shuffle(allPlayers);

        int playerNumber = 0;
        for (Player p : allPlayers) {
            playerNumber++;
            p.setNumber(playerNumber);
        }

        this.currentPlayer = allPlayers.get(0);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getNumberOfMafia() {
        return numberOfMafia;
    }

    public void minusPlayer() {
        this.numberOfPlayers--;
    }

    public void plusPlayer() {
        this.numberOfPlayers++;
    }

    public void minusMafia() {
        this.numberOfMafia--;
    }

    public void plusMafia() {
        this.numberOfMafia++;
    }

    public boolean isHasDon() {
        return hasDon;
    }

    public void setHasDon(boolean hasDon) {
        this.hasDon = hasDon;
    }

    public boolean isHasCop() {
        return hasCop;
    }

    public void setHasCop(boolean hasCop) {
        this.hasCop = hasCop;
    }

    public boolean isHasDoctor() {
        return hasDoctor;
    }

    public void setHasDoctor(boolean hasDoctor) {
        this.hasDoctor = hasDoctor;
    }

    public boolean isHasWhore() {
        return hasWhore;
    }

    public void setHasWhore(boolean hasWhore) {
        this.hasWhore = hasWhore;
    }

    public boolean isHasManiac() {
        return hasManiac;
    }

    public void setHasManiac(boolean hasManiac) {
        this.hasManiac = hasManiac;
    }

    public boolean isHasLucky() {
        return hasLucky;
    }

    public void setHasLucky(boolean hasLucky) {
        this.hasLucky = hasLucky;
    }

    public boolean isSportMode() {
        return sportMode;
    }

    public void setSportMode(boolean sportMode) {
        this.sportMode = sportMode;
    }

    public boolean isShowDied() {
        return showDied;
    }

    public void setShowDied(boolean showDied) {
        this.showDied = showDied;
    }

    public List<Player> getAllPlayers() {
        return allPlayers;
    }

    public void nextDay() {
        this.numberOfDay++;
    }

    public int getDay() {
        return this.numberOfDay;
    }

    public void nextPlayer() {
        currentPlayer = allPlayers.stream()
                .filter(p -> p.getNumber() > currentPlayer.getNumber() && p.isAlive())
                .findFirst().orElse(allPlayers.stream()
                                .filter(p -> p.getNumber() >= 1 && p.isAlive())
                                .findFirst().get());
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void addShottedPlayer(Player player) {
        this.shottedPlayers.add(player);
    }

    public void clearShottedPlayers() {
        this.shottedPlayers = new ArrayList<>();
    }

    public List<Player> getShottedPlayers() {
        return this.shottedPlayers;
    }

    public int getSumShottedPlayers() {
        return this.shottedPlayers.size();
    }

    public void addVotedPlayer(Player player) {
        this.votedPlayers.add(player);
    }

    public List<Player> getVotedPlayers() {
        return this.votedPlayers;
    }

    public void clearVotedPlayers() {
        this.votedPlayers = new ArrayList<>();
    }

    public boolean votedPlayersIsNotEmpty() {
        return votedPlayers.size() > 0;
    }

    public int getSumVotedPlayers() {
        return votedPlayers.size();
    }
}
