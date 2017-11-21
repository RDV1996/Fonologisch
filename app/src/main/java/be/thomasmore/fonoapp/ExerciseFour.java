package be.thomasmore.fonoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExerciseFour extends AppCompatActivity {

    String correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_four);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        correct = "Beer";
    }

    public void antwoord(View v){
        Button leftButton = (Button) findViewById(R.id.ex4ButtonLeft);
        Button rightButton = (Button) findViewById(R.id.ex4ButtonRight);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        String test = v.getTag() + "";
        if(test.equals(correct)){
            right(v);
        }
        else{
            wrong(v);
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.exFourlayout);
        Button button = new Button(this);
        button.setText("Volgende");
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                volgende();
            }
        });
        linearLayout.addView(button);
    }

    public void right(View v) {
        v.setBackgroundResource(R.drawable.border_green);
    }

    public void wrong(View v) {
        v.setBackgroundResource(R.drawable.border_red);
    }

    public void volgende(){
        Intent intent = new Intent(this, ExerciseFive.class);
        startActivity(intent);
    }

}
