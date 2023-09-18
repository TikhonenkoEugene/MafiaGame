package com.example.mafiagame.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mafiagame.R;
import com.example.mafiagame.fragments.DayActivityFragment;
import com.example.mafiagame.repos.Repo;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.flGame, DayActivityFragment.class, null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder
                .setTitle("Мафия")
                .setCancelable(false)
                .setMessage("Хотите вернуться в главное меню и начать игру сначала?")
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    Repo.get().destroy();
                    startActivity(new Intent(this, MainActivity.class));
                })
                .setNegativeButton("Нет", (dialogInterface, i) -> {
                    dialogInterface.cancel();}).create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void clickOnFaults(View view) {
//        getSupportFragmentManager().beginTransaction()
//                .setReorderingAllowed(true)
//
//                .add(R.id.flGame, DayActivityFragment.class, null)
//                .commit();

    }
}