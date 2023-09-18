package com.example.mafiagame.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mafiagame.R;
import com.example.mafiagame.repos.Repo;

public class MainActivity extends AppCompatActivity {
    private final String strNumberOfPlayers = "Количество игроков: %d";
    private final String strNumberOfMafia = "Количество мафии: %d";
    private TextView tNumberOfPlayers, tNumberOfMafia;
    private Switch swSportMode, swShowDied;
    private CheckBox chkDon, chkCop, chkDoctor, chkWhore, chkManiac, chkLucky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        tNumberOfPlayers = findViewById(R.id.txtNumberOfPlayers);
        tNumberOfMafia = findViewById(R.id.txtNumberOfMafia);
        swSportMode = findViewById(R.id.swSport);
        swShowDied = findViewById(R.id.swShowDied);
        chkDon = findViewById(R.id.chkDon);
        chkCop = findViewById(R.id.chkCop);
        chkDoctor = findViewById(R.id.chkDoc);
        chkWhore = findViewById(R.id.chkWhore);
        chkManiac = findViewById(R.id.chkManiac);
        chkLucky = findViewById(R.id.chkLucker);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder
                .setTitle("Мафия")
                .setCancelable(false)
                .setMessage("Вы хотите выйти из игры?")
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                })
                .setNegativeButton("Нет", (dialogInterface, i) -> {
                    dialogInterface.cancel();}).create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void clickMinusPlayer(View view) {
        Repo repo = Repo.get();
        if (repo.getNumberOfPlayers() > 5) {
            repo.minusPlayer();
            tNumberOfPlayers.setText(String.format(strNumberOfPlayers, repo.getNumberOfPlayers()));
        }
    }

    public void clickPlusPlayer(View view) {
        Repo repo = Repo.get();
        if (repo.getNumberOfPlayers() < 25) {
            repo.plusPlayer();
            tNumberOfPlayers.setText(String.format(strNumberOfPlayers, repo.getNumberOfPlayers()));
        }
    }

    public void clickMinusMafia(View view) {
        Repo repo = Repo.get();
        if (repo.getNumberOfMafia() > 1) {
            repo.minusMafia();
            tNumberOfMafia.setText(String.format(strNumberOfMafia, repo.getNumberOfMafia()));
        }
    }

    public void clickPlusMafia(View view) {
        Repo repo = Repo.get();
        if (repo.getNumberOfMafia() < 8) {
            repo.plusMafia();
            tNumberOfMafia.setText(String.format(strNumberOfMafia, repo.getNumberOfMafia()));
        }
    }

    public void clickChkDon(View view) {
        Repo repo = Repo.get();
        if (chkDon.isChecked()) {
            repo.setHasDon(true);
        } else {
            repo.setHasDon(false);
        }
    }

    public void clickChkCop(View view) {
        Repo repo = Repo.get();
        if (chkCop.isChecked()) {
            repo.setHasCop(true);
        } else {
            repo.setHasCop(false);
        }
    }

    public void clickChkDoc(View view) {
        Repo repo = Repo.get();
        if (chkDoctor.isChecked()) {
            repo.setHasDoctor(true);
        } else {
            repo.setHasDoctor(false);
        }
    }

    public void clickChkWhore(View view) {
        Repo repo = Repo.get();
        if (chkWhore.isChecked()) {
            repo.setHasWhore(true);
        } else {
            repo.setHasWhore(false);
        }
    }

    public void clickChkManiac(View view) {
        Repo repo = Repo.get();
        if (chkManiac.isChecked()) {
            repo.setHasManiac(true);
        } else {
            repo.setHasManiac(false);
        }

    }

    public void clickChkLucky(View view) {
        Repo repo = Repo.get();
        if (chkLucky.isChecked()) {
            repo.setHasLucky(true);
        } else {
            repo.setHasLucky(false);
        }
    }

    public void clickSportMode(View view) {
        Repo repo = Repo.get();
        if (swSportMode.isChecked()) {
            repo.setSportMode(true);
        } else {
            repo.setSportMode(false);
        }
    }

    public void clickShowDied(View view) {
        Repo repo = Repo.get();
        if (swShowDied.isChecked()) {
            repo.setShowDied(true);
        } else {
            repo.setShowDied(false);
        }
    }

    public void getInfo(View view) {
        startActivity(new Intent(this, RulesActivity.class));
    }

    public void getStart(View view) {
        Repo.get().makeRoles();
        startActivity(new Intent(this, TakesRolesActivity.class));
    }
}