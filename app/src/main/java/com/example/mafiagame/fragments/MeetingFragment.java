package com.example.mafiagame.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafiagame.R;
import com.example.mafiagame.activities.GameActivity;
import com.example.mafiagame.adapters.CheckboxItemAdapter;
import com.example.mafiagame.repos.Repo;

import java.util.ArrayList;
import java.util.List;

public class MeetingFragment extends Fragment {
    private Repo repo;
    private Button btnRemind, btnGo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = Repo.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        btnRemind = view.findViewById(R.id.btnRemind);
        btnGo = view.findViewById(R.id.btnGo);

        List<String> roles = new ArrayList<>();
        roles.add("Мафия познакомилась");
        if (repo.isHasDon()) {
            roles.add("Дон мафии обозначился");
        }
        if (repo.isHasCop()) {
            roles.add("Шериф познакомился");
        }
        if (repo.isHasDoctor()) {
            roles.add("Доктор познакомился");
        }
        if (repo.isHasWhore()) {
            roles.add("Любовница познакомилась");
        }
        if (repo.isHasManiac()) {
            roles.add("Маньяк познакомился");
        }
        if (repo.isHasLucky()) {
            roles.add("Везунчик познакомился");
        }

        RecyclerView recyclerView = view.findViewById(R.id.vlAllPlayers);
        CheckboxItemAdapter adapter = new CheckboxItemAdapter(roles);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        btnRemind.setOnClickListener(v -> {
            Toast.makeText(view.getContext(), "Не работает", Toast.LENGTH_SHORT).show();
        });

        btnGo.setOnClickListener(v -> {
            startActivity(new Intent(view.getContext(), GameActivity.class));
        });

        return view;
    }
}