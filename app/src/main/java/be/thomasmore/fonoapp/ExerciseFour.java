package be.thomasmore.fonoapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.rest.APIClient;
import be.thomasmore.fonoapp.rest.APIInterface;
import be.thomasmore.fonoapp.test.TestAPI;

public class ExerciseFour extends AppCompatActivity {

    String correct;
    TestAPI testAPI;
    int counter;
    Button leftButton;
    Button rightButton;
    TextView text;
    ImageView img;
    int score;
    APIInterface apiInetface;
    int max;
    Word rightWord;
    Word wrongWord;

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
        score = 0;
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
        final MediaPlayer playSound = MediaPlayer.create(this,R.raw.instructie4);
        playSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer player){
                setupQuestion();
            }
        });
        playSound.start();
    }
    public void playSound(View v){
        final MediaPlayer playSound = MediaPlayer.create(this, getResources().getIdentifier(rightWord.getSentenceSound(), "raw", getPackageName()));
        playSound.start();
    }

    public void setupQuestion() {
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setVisibility(View.GONE);
        if (Math.random() < 0.5) {
            rightWord = Global.getWordById(Global.wordPairs.get(counter).getRightWordId());
            wrongWord = Global.getWordById(Global.wordPairs.get(counter).getWrongWordId());
        } else {
            rightWord = Global.getWordById(Global.wordPairs.get(counter).getWrongWordId());
            wrongWord = Global.getWordById(Global.wordPairs.get(counter).getRightWordId());
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
        score++;
    }

    public void wrong(View v) {
        v.setBackgroundResource(R.drawable.border_red);
    }

    public void volgende(View v) {
        counter++;
        if (counter == max) {
            Intent intent = new Intent(this, ExerciseFive.class);
            startActivity(intent);
        } else {
            setupQuestion();

        }
    }
}
