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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;

import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.rest.APIClient;
import be.thomasmore.fonoapp.rest.APIInterface;

public class ExerciseFour extends AppCompatActivity {

    String correct;
    int counter;
    Button leftButton;
    Button rightButton;
    TextView text;
    ImageView img;
    APIInterface apiInetface;
    int max;
    Word rightWord;
    Word wrongWord;

    int fouten = 0;

    int[] sm;
    SoundPool soundPool;
    AudioManager amg;
    int soundCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_four);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        leftButton = (Button) findViewById(R.id.ex4ButtonLeft);
        leftButton.setEnabled(false);
        rightButton = (Button) findViewById(R.id.ex4ButtonRight);
        rightButton.setEnabled(false);
        text = (TextView) findViewById(R.id.textEx4);
        img = (ImageView) findViewById(R.id.imgEx4);
        apiInetface = APIClient.getClient().create(APIInterface.class);
        counter = 0;
        Collections.shuffle(Global.wordPairs);

        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                volgende(v);
            }
        });
        if (Global.wordPairs.size() >= 5) {
            max = 5;
        } else {
            max = Global.wordPairs.size();
        }
        sm = new int[max*2];
        initSound();
        final MediaPlayer playSound = MediaPlayer.create(this, R.raw.instructie4);
        playSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer player) {
                setupQuestion();
            }
        });
        playSound.start();
    }

    private void initSound() {
        int maxStreams = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }
        int Scounter = 0;
        for(int i = 0; i < max; i++){
            sm[Scounter] = soundPool.load(this, getResources().getIdentifier(Global.getWordById(Global.wordPairs.get(i).getRightWordId()).getSentenceSound(), "raw", getPackageName()), 1);
            Scounter++;
            sm[Scounter] = soundPool.load(this, getResources().getIdentifier(Global.getWordById(Global.wordPairs.get(i).getWrongWordId()).getSentenceSound(), "raw", getPackageName()), 1);
            Scounter++;
        }
        amg = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);

    }
    public void playSound(View v) {
        soundPool.play(sm[soundCounter], 1, 1, 1, 0, 1f);
    }
    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }



    public void setupQuestion() {
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setVisibility(View.GONE);
        soundCounter = counter*2;
        if (Math.random() < 0.5) {
            rightWord = Global.getWordById(Global.wordPairs.get(counter).getRightWordId());
            wrongWord = Global.getWordById(Global.wordPairs.get(counter).getWrongWordId());

        } else {
            rightWord = Global.getWordById(Global.wordPairs.get(counter).getWrongWordId());
            wrongWord = Global.getWordById(Global.wordPairs.get(counter).getRightWordId());
            soundCounter ++;
        }

        if (Math.random() < 0.5) {
            leftButton.setText(rightWord.getWord());
            leftButton.setTag(rightWord.getWord());
            rightButton.setText(wrongWord.getWord());
            rightButton.setTag(wrongWord.getWord());
        } else {
            rightButton.setText(rightWord.getWord());
            leftButton.setText(wrongWord.getWord());
            rightButton.setTag(rightWord.getWord());
            leftButton.setTag(wrongWord.getWord());
        }

        text.setText(rightWord.getSentence());
        img.setImageResource(getResources().getIdentifier(rightWord.getMainImg(), "drawable", getPackageName()));
        correct = rightWord.getWord();
        leftButton.setEnabled(true);
        leftButton.setBackgroundResource(R.drawable.border_black);
        rightButton.setEnabled(true);
        rightButton.setBackgroundResource(R.drawable.border_black);
        playSound(leftButton);
    }

    public void antwoord(View v) {
        Button leftButton = (Button) findViewById(R.id.ex4ButtonLeft);
        Button rightButton = (Button) findViewById(R.id.ex4ButtonRight);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        String test = v.getTag() + "";
        if (test.equals(correct)) {
            right(v);
        } else {
            wrong(v);
        }
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setVisibility(View.VISIBLE);
    }

    public void right(View v) {
        v.setBackgroundResource(R.drawable.border_green);
        // score++;
    }

    public void wrong(View v) {
        v.setBackgroundResource(R.drawable.border_red);
        fouten++;
    }

    public void volgende(View v) {
        counter++;
        if (counter == max) {
            // foutenLijst.add(fouten);
            onCompletion();
        } else {
            setupQuestion();

        }
    }
    public void onCompletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fouten: " + String.valueOf(fouten))
                .setTitle(R.string.title_activity_exercise_four)
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
    public void nextActivity() {
        Intent intent = new Intent(this, ExerciseFive.class);
        startActivity(intent);
    }
}
