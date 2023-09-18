package com.example.mafiagame.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mafiagame.R;
import com.example.mafiagame.fragments.ListingRolesFragment;
import com.example.mafiagame.repos.Repo;
import com.example.mafiagame.utils.Helper;

public class TakesRolesActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_takes_roles);

        mediaPlayer = Helper.getMediaPlayer(this);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.frameLayout, ListingRolesFragment.class, null)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TakesRolesActivity.this);
        builder
                .setTitle("Мафия")
                .setCancelable(false)
                .setMessage("Хотите вернуться в главное меню и начать игру сначала?")
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    Repo.get().destroy();
                    mediaPlayer.stop();
                    startActivity(new Intent(this, MainActivity.class));
                })
                .setNegativeButton("Нет", (dialogInterface, i) -> {
                    dialogInterface.cancel();}).create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}