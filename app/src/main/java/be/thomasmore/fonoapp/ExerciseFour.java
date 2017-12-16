package be.thomasmore.fonoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.Classes.WordPair;
import be.thomasmore.fonoapp.rest.APIClient;
import be.thomasmore.fonoapp.rest.APIInterface;
import be.thomasmore.fonoapp.test.TestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        rightButton = (Button) findViewById(R.id.ex4ButtonRight);
        text = (TextView) findViewById(R.id.textEx4);
        img = (ImageView) findViewById(R.id.imgEx4);
        wordPairs = Global.wordPairs;
        apiInetface = APIClient.getClient().create(APIInterface.class);
        counter = 0;
        score = 0;
        Collections.shuffle(wordPairs);

        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                volgende(v);
            }
        });
        if (wordPairs.size() >= 5) {
            max = 5;
        } else {
            max = wordPairs.size();
        }
        setupQuestion();
    }

    public void setupQuestion() {
        Button button = (Button) findViewById(R.id.volgendeEx4);
        button.setVisibility(View.GONE);
        if (Math.random() < 0.5) {
            setWords(wordPairs.get(counter).getRightWordId(), wordPairs.get(counter).getWrongWordId());
        } else {
            setWords(wordPairs.get(counter).getWrongWordId(), wordPairs.get(counter).getRightWordId());
        }
    }

    public void continueSetup() {
        if (Math.random() < 0.5) {
            leftButton.setText(rightWord.getWord());
            leftButton.setTag(rightWord.getWord());
            rightButton.setText(wrongWord.getWord());
            rightButton.setTag(wrongWord.getWord());
        } else {
            rightButton.setText(wrongWord.getWord());
            leftButton.setText(rightWord.getWord());
            rightButton.setTag(wrongWord.getWord());
            leftButton.setTag(rightWord.getWord());
        }

        text.setText(rightWord.getSentence());
        img.setImageResource(getResources().getIdentifier(rightWord.getMainImg(), "drawable", getPackageName()));
        correct = rightWord.getWord();
        leftButton.setEnabled(true);
        leftButton.setBackgroundResource(R.drawable.border_black);
        rightButton.setEnabled(true);
        rightButton.setBackgroundResource(R.drawable.border_black);
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
        TextView score = (TextView) findViewById(R.id.)
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

    public void setWords(String right, final String wrong) {
        Call<Word> call = apiInetface.getWord(right);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if (response.isSuccessful()) {
                    rightWord = response.body();
                    setWrongWord(wrong);

                } else {
                    Toast.makeText(getApplicationContext(), response.message(),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setWrongWord(String id) {

        Call<Word> call = apiInetface.getWord(id);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if (response.isSuccessful()) {
                    wrongWord = response.body();
                    continueSetup();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
