package be.thomasmore.fonoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import be.thomasmore.fonoapp.rest.APIClient;
import be.thomasmore.fonoapp.rest.APIInterface;

public class ExerciseFive extends AppCompatActivity {


    int column;
    int row;
    int total;
    ArrayList<ImageView> pictures;
    APIInterface apiInetface;
    int score1;
    int score2;
    TextView view1;
    TextView view2;
    TextView TopText;
    int player;
    Button but1;
    Button but2;

    int teller = 0;
    int fouten = 0;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_five);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final MediaPlayer playSound = MediaPlayer.create(this, R.raw.instructie5);
        playSound.start();
        apiInetface = APIClient.getClient().create(APIInterface.class);
        pictures = new ArrayList<>();
        score1 = 0;
        score2 = 0;
        player = 1;
        view1 = (TextView) findViewById(R.id.Ex5S1);
        view2 = (TextView) findViewById(R.id.Ex5S2);
        TopText = (TextView) findViewById(R.id.TopText);
        counter = 0;

        but1 = (Button) findViewById(R.id.score1);
        but2 = (Button) findViewById(R.id.score2);

        makeGame();
    }

    public void makeGame() {
        but1.setEnabled(false);
        but2.setEnabled(false);
        Collections.shuffle(Global.words);
        int size = Global.words.size();

        switch (size) {
            case 0:
                show("No words");
                break;
            case 2:
                row = 1;
                column = 2;
                break;
            case 4:
                row = 2;
                column = 2;
                break;
            case 6:
                row = 3;
                column = 2;
                break;
            case 8:
                row = 2;
                column = 4;
                break;
            case 10:
                row = 2;
                column = 5;
                break;
            default:
                row = 3;
                column = 3;
                break;
        }

        total = row * column;
        int k = 0;
        LinearLayout gameLayout = (LinearLayout) findViewById(R.id.exFiveGame);
        for (int i = 0; i < row; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            gameLayout.addView(linearLayout);
            for (int j = 0; j < column; j++) {
                final ImageView imageView = new ImageView(this);

                LinearLayout.LayoutParams imageLayoutParams =
                        new LinearLayout.LayoutParams(100, 100);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageLayoutParams.width = 250;
                imageLayoutParams.height = 250;
                imageView.setLayoutParams(imageLayoutParams);
                imageView.setImageResource(R.drawable.question_mark);
                imageView.setBackgroundResource(R.drawable.border_black);
                imageView.setTag(k);

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (Integer.parseInt(v.getTag().toString()) != -1) {
                            imageView.setImageResource(getResources().getIdentifier(Global.words.get(Integer.parseInt(v.getTag().toString())).getMainImg(), "drawable", getPackageName()));
                            scorePhase();
                            v.setTag(-1);
                        }
                    }
                });

                pictures.add(imageView);
                k++;
                linearLayout.addView(imageView);
            }
        }


    }

    private void show(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    public void scorePhase() {
        for (int i = 0; i < total; i++) {
            pictures.get(i).setEnabled(false);
        }
        but1.setEnabled(true);
        but2.setEnabled(true);
        TopText.setText("Had speler " + player + " het juist?");
    }

    public void exFiveAntwoord(View v) {
        if (Integer.parseInt(v.getTag().toString()) == 1) {
            if (player == 1) {
                score1++;
                teller++;
                player = 2;
            } else {
                score2++;
                player = 1;
            }
        } else {
            if (player == 1) {
                fouten++;
            }
        }
        view1.setText(score1 + "");
        view2.setText(score2 + "");
        gamePhase();
    }

    private void gamePhase() {
        for (int i = 0; i < total; i++) {
            if (Integer.parseInt(pictures.get(i).getTag().toString()) != -1)
                pictures.get(i).setEnabled(true);
        }
        counter++;
        but1.setEnabled(false);
        but2.setEnabled(false);
        TopText.setText("het is de beurt aan speler " + player);
        if (counter == total) {
            onCompletion();
        }
    }

    public void onCompletion() {
        String winnaar = "Winnaar van oefeling 5 is: ";
        if (score1 > score2) {
            winnaar += "Speler 1";
        } else if (score1 < score2) {
            winnaar += "Speler 2";
        } else {
            winnaar = "Gelijkspel voor oefening 5!";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(winnaar + "\nTotale Fouten: " + String.valueOf(fouten))
                .setTitle(R.string.title_activity_exercise_five)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nextActivity();
                    }
                })
                .show();
    }

    // Maakt de SoundPool leeg
    public void nextActivity() {
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
    }

}
