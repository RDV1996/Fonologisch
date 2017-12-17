package be.thomasmore.fonoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_five);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiInetface = APIClient.getClient().create(APIInterface.class);
        pictures = new ArrayList<>();
        makeGame();
    }

    public void makeGame(){
        Collections.shuffle(Global.words);
        int size = Global.words.size();

        switch (size){
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
                column = 4;
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
                        imageClick((ImageView) v);
                        imageView.setImageResource(getResources().getIdentifier("basket", "drawable", getPackageName()));
                    }
                });

                pictures.add(imageView);
                k++;
                linearLayout.addView(imageView);
            }
        }

    }
    public void imageClick(View v){
        show(v.getTag() + "");
    }

    private void show(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}
