package com.example.mafiagame.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mafiagame.R;
import com.example.mafiagame.fragments.DayActivityFragment;
import com.example.mafiagame.fragments.NightFragment;
import com.example.mafiagame.fragments.VoteFragment;
import com.example.mafiagame.repos.Repo;
import com.example.mafiagame.utils.Helper;

public class GameActivity extends AppCompatActivity implements DayActivityFragment.OnFragmentSendDataListener {
    public static final String DAY_FRAGMENT = "day", VOTE_FRAGMENT = "vote";
    public static final String FAULTS_FRAGMENT = "faults", NIGHT_FRAGMENT = "night";
    public static final String START_TIMER = "start", STOP_TIMER = "stop";
    private DayActivityFragment dayActivityFragment;
    private NightFragment nightFragment;
    private VoteFragment voteFragment;
    private MediaPlayer mediaPlayer;
    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);

        dayActivityFragment = new DayActivityFragment();
        nightFragment = new NightFragment();
        voteFragment = new VoteFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.flGame, dayActivityFragment, DAY_FRAGMENT)
                    .commit();
        }
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

    public void startNightFragment() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.flGame, nightFragment, NIGHT_FRAGMENT)
                .commit();
    }

    public void startVoteFragment() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.flGame, voteFragment, VOTE_FRAGMENT)
                .commit();
    }

    public void startDayFragment() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.flGame, dayActivityFragment, DAY_FRAGMENT)
                .commit();
    }

    public void clickOnFaults(View view) {

    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onSendData(String data, DayActivityFragment fragment) {
        switch (data) {
            case DAY_FRAGMENT:
                startDayFragment();
                stopMusic();
                break;
            case NIGHT_FRAGMENT:
                mediaPlayer = Helper.getMediaPlayer(this);
                mediaPlayer.start();
                startNightFragment();
                break;
            case VOTE_FRAGMENT:
                stopMusic();
                startVoteFragment();
                break;
            case START_TIMER:
                timer = new CountDownTimer(fragment.getTimeToSpeak() * 1000L, 1000L) {
                    @Override
                    public void onTick(long l) {
                        fragment.getBtnStartStopTimer().setTextSize(30);
                        fragment.getBtnStartStopTimer().setText(String.valueOf(l / 1000L));
                        fragment.setTimeToSpeak((int) (l / 1000L));
                    }

                    @Override
                    public void onFinish() {
                        fragment.getBtnStartStopTimer().setEnabled(false);
                        fragment.setTimeToSpeak(0);
                    }
                };
                timer.start();
                break;
            case STOP_TIMER:
                if (timer != null) {
                    timer.cancel();
                }
                timer = null;
                break;
        }
    }
}