package com.example.mafiagame.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mafiagame.R;
import com.example.mafiagame.repos.Repo;
import com.example.mafiagame.model.Player;

public class ListingRolesFragment extends Fragment {
    Repo repo;
    private int playerPosition = 0;
    private ImageView imgPlayer;
    private TextView tPlayer;
    private Button btnPrevious;
    private Button btnNext;
    private final String strStart = "Знакомство";
    private final String strNext = "Следующий";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = Repo.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_listing_roles, container, false);

        imgPlayer = view.findViewById(R.id.imgPlayer);
        tPlayer = view.findViewById(R.id.txtPlayer);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);

        setPlayer(repo.getAllPlayers().get(playerPosition));

        btnNext.setOnClickListener(view1 -> {
            if (playerPosition == repo.getAllPlayers().size() - 2) {
                playerPosition++;
                setPlayer(repo.getAllPlayers().get(playerPosition));
                btnNext.setText(strStart);
            } else if (playerPosition == repo.getAllPlayers().size() - 1) {
                MeetingFragment meetingFragment = new MeetingFragment();
                ((AppCompatActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout, meetingFragment)
                        .commit();
            } else {
                playerPosition++;
                setPlayer(repo.getAllPlayers().get(playerPosition));
                btnPrevious.setEnabled(true);
            }
        });

        btnPrevious.setOnClickListener(view1 -> {
            if (playerPosition == repo.getAllPlayers().size() - 1) {
                playerPosition--;
                setPlayer(repo.getAllPlayers().get(playerPosition));
                btnNext.setText(strNext);
            } else if (playerPosition == 1) {
                playerPosition--;
                setPlayer(repo.getAllPlayers().get(playerPosition));
                btnPrevious.setEnabled(false);
            } else {
                playerPosition--;
                setPlayer(repo.getAllPlayers().get(playerPosition));
            }
        });

        return view;
    }

    private void setPlayer(Player player) {
        switch (player.getRole()) {
            case MAFIA:
                imgPlayer.setImageResource(R.drawable.mafia);
                break;
            case DON:
                imgPlayer.setImageResource(R.drawable.don);
                break;
            case DOCTOR:
                imgPlayer.setImageResource(R.drawable.doctor);
                break;
            case WHORE:
                imgPlayer.setImageResource(R.drawable.whore);
                break;
            case COP:
                imgPlayer.setImageResource(R.drawable.cop);
                break;
            case MANIAC:
                imgPlayer.setImageResource(R.drawable.maniac);
                break;
            case CIVILIAN:
                imgPlayer.setImageResource(R.drawable.civilian);
                break;
            case LUCKY:
                imgPlayer.setImageResource(R.drawable.lucky);
                break;
            default:
                imgPlayer.setImageResource(R.drawable.none);
        }
        tPlayer.setText("Игрок " + player.getNumber());
    }
}