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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import be.thomasmore.fonoapp.Classes.Word;

public class ExerciseTwo extends AppCompatActivity {

    Random rand = new Random();
    int k = 0;
    int teller;
    int fouten;

    SoundPool soundPool;
    int[] sm = new int[2];
    AudioManager amg;

    Timer timer = new Timer();

    Word leftWord;
    Word rightWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        teller = bundle.getInt("teller");
        fouten = bundle.getInt("fouten");


        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(String.valueOf(teller));

        initWords();
        initSound();
        k = rand.nextInt(2);

        final MediaPlayer playSound = MediaPlayer.create(this,R.raw.instructie2);
        playSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player){
                basePictures();
            }
        });
        playSound.start();
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

        sm[0] = soundPool.load(this, getResources().getIdentifier(leftWord.getWordsound(), "raw", getPackageName()), 1);
        sm[1] = soundPool.load(this, getResources().getIdentifier(rightWord.getWordsound(), "raw", getPackageName()), 1);

        amg = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void nextActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt("teller", teller);
        bundle.putInt("fouten", fouten);
        Intent intent = new Intent(this, ExerciseThree.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void basePictures() {
        ImageView imageViewLeft = (ImageView) findViewById(getResources().getIdentifier("left", "id", getPackageName()));
        imageViewLeft.setImageResource(getResources().getIdentifier(leftWord.getMainImg(), "drawable", getPackageName()));
        imageViewLeft.setTag(0);

        ImageView imageViewRight = (ImageView) findViewById(getResources().getIdentifier("right", "id", getPackageName()));
        imageViewRight.setImageResource(getResources().getIdentifier(rightWord.getMainImg(), "drawable", getPackageName()));
        imageViewRight.setTag(1);
    }

    // Geeft score bij juiste antwoorden. Maakt fout antwoord rood, goed antwoord groen
    public void onClickImage(View v) {
        String tag = v.getTag() + "";
        final ImageView imageViewLeft = (ImageView) findViewById(R.id.left);
        final ImageView imageViewRight = (ImageView) findViewById(R.id.right);

        TextView scoreView = (TextView) findViewById(R.id.score);

        if (tag.equals(String.valueOf(k))) {
            v.setBackgroundResource(R.drawable.border_green);
            playSound();
            k = rand.nextInt(2);
            teller++;
            scoreView.setText(String.valueOf(teller));
            if (teller == 16) {
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
        }
    }

    // Speelt het geluid af
    private void playSound() {
        soundPool.play(sm[k], 1, 1, 1, 0, 1f);
    }

    // Speelt het geluid af voor de geluidsknop
    public void playSoundEx2(View v) {
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
                .show();
    }

    // Maakt de SoundPool leeg
    public final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}
