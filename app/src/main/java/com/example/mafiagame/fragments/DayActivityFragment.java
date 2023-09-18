package com.example.mafiagame.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mafiagame.R;
import com.example.mafiagame.enums.State;
import com.example.mafiagame.model.Player;
import com.example.mafiagame.repos.Repo;
import com.example.mafiagame.utils.Helper;

import java.util.List;

public class DayActivityFragment extends Fragment {
    private Repo repo;
    private List<Player> discusQueue;
    private State state = State.DAY;
    private int playerPos = 0;
    private int playerVotePos = 0;
    private int timeToSpeak = 60;
    private CheckBox chkVotePlayer;
    private TextView header, numOfMafInGame, summaryAfterNight, playerHeader, playerInfo;
    private Button btnNextPlayer, btnMinusVotePlayer, btnPlusVotePlayer, btnStartStopTimer;
    private Button btnGoVoteOrNight;
    private final String headerTxt = "В городе %d день";
    private final String chkVoteText = "Выставляю игрока: %d";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = Repo.get();
        discusQueue = Helper.getDiscusQueue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_activity, container, false);
        header = view.findViewById(R.id.txtHeaderDayActivity);
        numOfMafInGame = view.findViewById(R.id.txtCountMafia);
        summaryAfterNight = view.findViewById(R.id.txtSummaryBody);
        playerHeader = view.findViewById(R.id.txtPlayerHeader);
        playerInfo = view.findViewById(R.id.txtPlayerAddInfo);
        btnNextPlayer = view.findViewById(R.id.btnShowNextPlayer);
        btnStartStopTimer = view.findViewById(R.id.btnStartStopTimer);
        btnMinusVotePlayer = view.findViewById(R.id.btnMinusVotePlayer);
        btnPlusVotePlayer = view.findViewById(R.id.btnPlusVotePlayer);
        chkVotePlayer = view.findViewById(R.id.chkVotePlayer);
        btnGoVoteOrNight = view.findViewById(R.id.btnGoNightOrVote);

        header.setText(String.format(headerTxt, repo.getDay()));
        numOfMafInGame.setText(numOfMafInGame.getText().toString() + " " + repo.getAllPlayers()
                .stream().filter(p -> p.isMafia()).count());

        StringBuilder builder;
        if (repo.getSumShottedPlayers() == 0) {
            builder = new StringBuilder("Прошлой ночью убийств в городе не было!");
        } else if (repo.getSumShottedPlayers() == 1) {
            builder = new StringBuilder("Прошлой ночью в городе был убит:");
        } else {
            builder = new StringBuilder("Прошлой ночью в городе были убиты:");
        }
        summaryAfterNight.setText(getListOfShottedPlayers(builder));
        showCurrentPlayer();
        setVotePlayer();

        btnNextPlayer.setOnClickListener(v -> {
            if (timeToSpeak >= 60) {
                int numPlayer = discusQueue.get(playerPos).getNumber();
                Toast.makeText(getContext(), "Игрок " + numPlayer + " не поговорил!", Toast.LENGTH_SHORT).show();
            } else {
                clickButtonNextPlayer();
            }
        });

        btnNextPlayer.setOnLongClickListener(v -> {
            clickButtonNextPlayer();
            return true;
        });

        btnMinusVotePlayer.setOnClickListener(v -> {
            if (playerVotePos > 0) {
                playerVotePos--;
            } else {
                playerVotePos = discusQueue.size() - 1;
            }
            setVotePlayer();
        });

        btnPlusVotePlayer.setOnClickListener(v -> {
            if (playerVotePos < discusQueue.size() - 1) {
                playerVotePos++;
            } else {
                playerVotePos = 0;
            }
            setVotePlayer();
        });

        btnGoVoteOrNight.setOnClickListener(v -> {
            switch (state) {
                case DAY:
                    disableButtons();
                    if (chkVotePlayer.isChecked()) {
                        addVotedPlayer();
                        displayVotedPlayers();
                    }
                    if (repo.votedPlayersIsNotEmpty()) {
                        if (repo.getSumVotedPlayers() == repo.getDay()) {
                            printLnSummary("В первый день один выставленный игрок не голосуется");
                            btnGoVoteOrNight.setText("В городе ночь!");
                            state = State.NIGHT;
                        } else {
                            printLnSummary("В таком порядке будем голосовать");
                            btnGoVoteOrNight.setText("В городе голосование");
                            state = State.VOTE;
                        }
                    } else {
                        printLnSummary("Никто не выставлен на голосование!");
                        btnGoVoteOrNight.setText("В городе ночь!");
                        state = State.NIGHT;
                    }
                    break;
                case VOTE:
                    Toast.makeText(getContext(), "Голосование", Toast.LENGTH_SHORT).show();
                    // TODO: 18.09.2023
                    break;
                case NIGHT:
                    Toast.makeText(getContext(), "Ночь", Toast.LENGTH_SHORT).show();
                    // TODO: 18.09.2023
                    break;
            }
        });



        return view;
    }

    private void setVotePlayer() {
        chkVotePlayer.setText(String.format(chkVoteText, discusQueue.get(playerVotePos).getNumber()));
    }

    private void printLnSummary(String s) {
        StringBuilder sb = new StringBuilder(summaryAfterNight.getText().toString());
        sb.append("\n").append(s);
        summaryAfterNight.setText(sb.toString());
    }

    private void clickButtonNextPlayer() {
        if (chkVotePlayer.isChecked()) {
            addVotedPlayer();
            chkVotePlayer.setChecked(false);
        }
        displayVotedPlayers();

        playerPos++;
        if (playerPos >= discusQueue.size() - 1) {
            // TODO: 18.09.2023 block next
            btnNextPlayer.setEnabled(false);
            btnGoVoteOrNight.setEnabled(true);
        }
        showCurrentPlayer();
    }

    private void addVotedPlayer() {
        Player p = discusQueue.get(playerVotePos);
        if (!repo.getVotedPlayers().contains(p)) {
            repo.addVotedPlayer(discusQueue.get(playerVotePos));
        }
    }

    private void displayVotedPlayers() {
        if (repo.votedPlayersIsNotEmpty()) {
            StringBuilder sb = new StringBuilder("Уже выставленные игроки:\n --->   ");
            for (Player p : repo.getVotedPlayers()) {
                sb.append(p.getNumber()).append("  ");
            }
            summaryAfterNight.setText(sb.toString().trim());
        }
    }

    private void showCurrentPlayer() {
        playerInfo.setText("");
        chkVotePlayer.setEnabled(true);
        btnStartStopTimer.setText("Старт");
        btnStartStopTimer.setEnabled(true);
        Player p = discusQueue.get(playerPos);
        playerHeader.setText("Игрок " + p.getNumber());
        if (playerPos == 0 && repo.getDay() == 1) {
            playerInfo.setText("открывает стол");
        }
        if (!p.isAlive()) {
            playerInfo.setText("речь выбывшего игрока");
            chkVotePlayer.setEnabled(false);
        }
        else if (p.getFaults() >= 3) {
            playerInfo.setText("не может говорить из-за фолов");
            timeToSpeak = 0;
            btnStartStopTimer.setEnabled(false);
            Helper.clearPlayerFaults(p.getNumber());
            // TODO: 18.09.2023 block timer
        }

    }

    private void disableButtons() {
        btnStartStopTimer.setEnabled(false);
        btnPlusVotePlayer.setEnabled(false);
        btnMinusVotePlayer.setEnabled(false);
        chkVotePlayer.setEnabled(false);
    }

    private String getListOfShottedPlayers(StringBuilder sb) {
        if (repo.getSumShottedPlayers() > 0) {
            for (Player p : repo.getShottedPlayers()) {
                sb.append("\n");
                sb.append("  - Игрок: ");
                sb.append(p.getNumber());
                if (repo.isShowDied()) {
                    if (p.isMafia()) {
                        sb.append(" (мафия)");
                    } else {
                        sb.append(" (мирный)");
                    }
                }
            }
        }
        if (checkMafiaIsWin()) {
            sb.append("\nПоздравляю мафию с победой!");
        }
        return sb.toString();
    }

    private boolean checkMafiaIsWin() {
        long countOfMafia = repo.getAllPlayers().stream()
                .filter(Player::isAlive)
                .filter(Player::isMafia)
                .count();
        long countOfCivilian = repo.getAllPlayers().stream()
                .filter(Player::isAlive)
                .count() - countOfMafia;
        return countOfMafia > countOfCivilian;
    }

}