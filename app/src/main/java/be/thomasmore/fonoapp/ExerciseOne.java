package be.thomasmore.fonoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ExerciseOne extends AppCompatActivity {

    int column = 3;
    int row = 3;
    int total = column * row;
    ImageView imageViews[] = new ImageView[total];
    int teller;

    List<String> filenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        teller=0;

        findPictures();
        randomPictures();
    }

    private void findPictures()
    {
        filenames = new ArrayList<String>();
        Field[] drawables = be.thomasmore.fonoapp.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("img_")) {
                filenames.add(f.getName());
            }
        }
        Collections.shuffle(filenames);
    }

    private void randomPictures() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.randomPictures);
        mainLayout.removeAllViews();

        Random rand = new Random();
        int k;

        for (int i = 0; i < row; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            mainLayout.addView(linearLayout);
            for (int j = 0; j < column; j++) {
                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(R.drawable.border_black);

                LayoutParams imageLayoutParams = new LayoutParams(100,100);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageLayoutParams.width = 180;
                imageLayoutParams.height = 180;
                imageView.setLayoutParams(imageLayoutParams);

                k = rand.nextInt(2);

                imageView.setImageResource(getResources().getIdentifier(filenames.get(k), "drawable", getPackageName()));
                imageView.setBackgroundResource(R.drawable.border_black);

                imageView.setTag(i);

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplicationContext(), ExerciseTwo.class);
                        startActivity(intent);
                    }
                });

                imageViews[k] = imageView;
                linearLayout.addView(imageView);
            }
        }
    }

}
