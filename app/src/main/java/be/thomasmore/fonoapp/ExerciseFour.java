package be.thomasmore.fonoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.Classes.WordPair;
import be.thomasmore.fonoapp.test.TestAPI;

public class ExerciseFour extends AppCompatActivity {

    String correct;
    TestAPI testAPI;
    ArrayList<WordPair> wordPairs;
    int counter;
    Button leftButton;
    Button rightButton;
    TextView text;
    ImageView img;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_four);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        leftButton = (Button) findViewById(R.id.ex4ButtonLeft);
        rightButton = (Button) findViewById(R.id.ex4ButtonRight);
        text = (TextView)findViewById(R.id.textEx4);
        img = (ImageView) findViewById(R.id.imgEx4);

        testAPI = new TestAPI();
        wordPairs = testAPI.getWordpairsByWPTandAge("1","1");
        counter =0;
        score = 0;
        Collections.shuffle(wordPairs);
        setupQuestion();
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                volgende(v);
            }
        });
    }
    public void setupQuestion(){
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setVisibility(View.GONE);
        Word rightword;
        Word wrongword;
        if(Math.random() < 0.5) {
            rightword = testAPI.getWordbyId(wordPairs.get(counter).getRightWordId());
            wrongword = testAPI.getWordbyId(wordPairs.get(counter).getWrongWordId());
        }
        else{
            wrongword = testAPI.getWordbyId(wordPairs.get(counter).getRightWordId());
            rightword = testAPI.getWordbyId(wordPairs.get(counter).getWrongWordId());
        }


        if(Math.random() < 0.5) {
            leftButton.setText(rightword.getWord());
            leftButton.setTag(rightword.getWord());
            rightButton.setText(wrongword.getWord());
            rightButton.setTag(wrongword.getWord());
        }
        else{
            rightButton.setText(wrongword.getWord());
            leftButton.setText(rightword.getWord());
            rightButton.setTag(wrongword.getWord());
            leftButton.setTag(rightword.getWord());
        }

        text.setText(rightword.getSentence());
        img.setImageResource(getResources().getIdentifier(rightword.getMainImg(), "drawable", getPackageName()));
        correct = rightword.getWord();
        leftButton.setEnabled(true);
        leftButton.setBackgroundResource(R.drawable.border_black);
        rightButton.setEnabled(true);
        rightButton.setBackgroundResource(R.drawable.border_black);

    }

    public void antwoord(View v){
        Button leftButton = (Button) findViewById(R.id.ex4ButtonLeft);
        Button rightButton = (Button) findViewById(R.id.ex4ButtonRight);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        String test = v.getTag() + "";
        if(test.equals(correct)){
            score++;

            right(v);
        }
        else{
            wrong(v);
        }
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setVisibility(View.VISIBLE);
    }

    public void right(View v) {
        v.setBackgroundResource(R.drawable.border_green);
    }

    public void wrong(View v) {
        v.setBackgroundResource(R.drawable.border_red);
    }

    public void volgende(View v){
        counter++;
        if(counter == wordPairs.size()) {
            Intent intent = new Intent(this, ExerciseFive.class);
            startActivity(intent);
        }
        else{
            setupQuestion();

        }
    }

}
