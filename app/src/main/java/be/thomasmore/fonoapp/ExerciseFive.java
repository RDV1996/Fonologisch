package be.thomasmore.fonoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExerciseFive extends AppCompatActivity {

    List<String> filenames;
    int column = 3;
    int row = 3;
    int total = row * column;
    ImageView pictures[] = new ImageView[total];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_five);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        makeGame();
    }

    public void makeGame(){
        leesLogos();
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
                        imageView.setImageResource(getResources().getIdentifier(filenames.get((int)v.getTag()), "drawable", getPackageName()));
                    }
                });

                pictures[k] = imageView;
                k++;
                linearLayout.addView(imageView);
            }
        }

    }
    public void imageClick(View v){
        show(v.getTag() + "");
    }

    private void leesLogos() {
        filenames = new ArrayList<String>();
        Field[] drawables = be.thomasmore.fonoapp.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("img_")) {
                filenames.add(f.getName());
            }
        }
        Collections.shuffle(filenames);
    }

    private void show(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}
