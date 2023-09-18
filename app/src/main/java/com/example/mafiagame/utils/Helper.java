package com.example.mafiagame.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mafiagame.R;
import com.example.mafiagame.model.Player;
import com.example.mafiagame.repos.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Helper {
    public static int random(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public static MediaPlayer getMediaPlayer(Context context) {
        switch (Helper.random(5)) {
            case 0: return MediaPlayer.create(context, R.raw.devchonka);
            case 1: return MediaPlayer.create(context, R.raw.business);
            case 2: return MediaPlayer.create(context, R.raw.lyubov);
            case 3: return MediaPlayer.create(context, R.raw.monako);
            case 4: return MediaPlayer.create(context, R.raw.pohuj);
            default: return MediaPlayer.create(context, R.raw.povelo);
        }
    }

    public static List<Player> getDiscusQueue() {
        Repo repo = Repo.get();
        List<Player> result = new ArrayList<>();

        if (repo.getSumShottedPlayers() > 0) {
            result.addAll(repo.getShottedPlayers());
        }

        int currentPlayerNumber = repo.getCurrentPlayer().getNumber();

        List<Player> firstPart = repo.getAllPlayers().stream()
                .filter(p -> p.getNumber() >= currentPlayerNumber)
                .filter(Player::isAlive)
                .collect(Collectors.toList());

        if (firstPart.size() > 0) {
            result.addAll(firstPart);
        }

        List<Player> secondPart = repo.getAllPlayers().stream()
                .filter(p -> p.getNumber() < currentPlayerNumber)
                .filter(Player::isAlive)
                .collect(Collectors.toList());

        if (secondPart.size() > 0) {
            result.addAll(secondPart);
        }

        return result;
    }

    public static void clearPlayerFaults(int numberOfPlayer) {
        Repo.get().getAllPlayers().stream()
                .filter(p -> p.getNumber() == numberOfPlayer)
                .findFirst().ifPresent(Player::clearFaults);
    }
}
