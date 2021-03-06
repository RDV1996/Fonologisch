package be.thomasmore.fonoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import be.thomasmore.fonoapp.Classes.Word;

public class ExerciseTwo extends AppCompatActivity {

    Random rand = new Random();
    int k = 0;
    int teller = 0;
    int fouten = 0;

    SoundPool soundPool;
    int[] sm = new int[2];
    AudioManager amg;

    Timer timer = new Timer();

    Word leftWord;
    Word rightWord;

    MediaPlayer playSound;
    int media_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Collections.shuffle(Global.wordPairs);

        initWords();
        initSound();
        k = rand.nextInt(2);

        playSound = MediaPlayer.create(this,R.raw.instructie2);
        playSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player){
                basePictures();
                playSound();
                TextView scoreView = (TextView) findViewById(R.id.score);
                scoreView.setText(String.valueOf(Global.score));
                ImageView homeView = (ImageView) findViewById(R.id.home);
                homeView.setImageResource(R.drawable.home);
            }
        });
        playSound.start();
    }

    // Pauzeert oefening
    @Override
    protected void onPause() {
        super.onPause();

        if(playSound != null)
        {
            playSound.pause();
            media_length = playSound.getCurrentPosition();
        }
    }

    // Stelt waardes in bij het opnieuw openen van de app
    @Override
    protected void onResume() {
        super.onResume();

        if(playSound != null)
        {
            playSound.seekTo(media_length);
            playSound.start();
        }

    }

    private void initWords() {
        if (Math.random() < 0.5) {
            leftWord = Global.getWordById(Global.wordPairs.get(0).getRightWordId());
            rightWord = Global.getWordById(Global.wordPairs.get(0).getWrongWordId());
        } else {
            leftWord = Global.getWordById(Global.wordPairs.get(0).getWrongWordId());
            rightWord = Global.getWordById(Global.wordPairs.get(0).getRightWordId());
        }
    }

    // Initialiseer het geluid d.m.v. SoundPool
    private void initSound() {
        int maxStreams = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }

        sm[0] = soundPool.load(this, getResources().getIdentifier("word_" + leftWord.getWord().toLowerCase(), "raw", getPackageName()), 1);
        sm[1] = soundPool.load(this, getResources().getIdentifier("word_" +rightWord.getWord().toLowerCase(), "raw", getPackageName()), 1);

        amg = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void backToHome(View v) {
        Intent intent = new Intent(this, CategorySelect.class);
        startActivity(intent);
    }

    public void nextActivity() {
        Intent intent = new Intent(this, ExerciseThree.class);
        startActivity(intent);
    }

    private void basePictures() {
        ImageView imageViewLeft = (ImageView) findViewById(getResources().getIdentifier("left", "id", getPackageName()));
        imageViewLeft.setImageResource(getResources().getIdentifier("main_" + leftWord.getWord().toLowerCase(), "drawable", getPackageName()));
        imageViewLeft.setTag(0);

        ImageView imageViewRight = (ImageView) findViewById(getResources().getIdentifier("right", "id", getPackageName()));
        imageViewRight.setImageResource(getResources().getIdentifier("main_" + rightWord.getWord().toLowerCase(), "drawable", getPackageName()));
        imageViewRight.setTag(1);
    }

    // Geeft score bij juiste antwoorden. Maakt fout antwoord rood, goed antwoord groen
    public void onClickImage(View v) {
        String tag = v.getTag() + "";
        final ImageView imageViewLeft = (ImageView) findViewById(R.id.left);
        final ImageView imageViewRight = (ImageView) findViewById(R.id.right);

        TextView scoreView = (TextView) findViewById(R.id.score);

        if (tag.equals(String.valueOf(k))) {
            playSound();
            v.setBackgroundResource(R.drawable.border_green);
            k = rand.nextInt(2);
            Global.score++;
            teller++;
            scoreView.setText(String.valueOf(Global.score));
            if (teller >= 7) {
                Global.foutenLijst.add(fouten);
                onCompletion();
            } else {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageViewLeft.setBackgroundResource(0);
                                imageViewRight.setBackgroundResource(0);
                            }
                        });
                        playSound();
                    }
                }, 2000);
            }
        } else {
            v.setBackgroundResource(R.drawable.border_red);
            playSound();
            fouten++;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageViewLeft.setBackgroundResource(0);
                            imageViewRight.setBackgroundResource(0);
                        }
                    });
                }
            }, 2000);
        }
    }

    // Speelt het geluid af
    private void playSound() {
        soundPool.play(sm[k], 1, 1, 1, 0, 1f);
    }

    // DialogBox wanneer de oefening voorbij is. Gaat naar de volgende oefening
    public void onCompletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fouten: " + String.valueOf(fouten))
                .setTitle(R.string.title_activity_exercise_two)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nextActivity();
                        cleanUpIfEnd();
                    }
                })
                .setCancelable(false)
                .show();
    }

    // Maakt de SoundPool leeg
    public final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}
