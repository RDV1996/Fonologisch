package be.thomasmore.fonoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String textFouten = "";

        for(int i=0; i< Global.foutenLijst.size(); i++){
            textFouten += "Oefening " +(i+1)+": " + Global.foutenLijst.get(i) + " fouten";
            if(i +1< Global.foutenLijst.size()){
                textFouten+= "\n";
            }
        }

        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Score: " + String.valueOf(Global.score));

        TextView errorView = (TextView) findViewById(R.id.errors);
        errorView.setText(String.valueOf(textFouten));

        Button button = (Button) findViewById(R.id.back);
        button.setText("Terug");
    }

    public void backToHome(View v) {
        Intent intent = new Intent(this, CategorySelect.class);
        startActivity(intent);
    }
}
