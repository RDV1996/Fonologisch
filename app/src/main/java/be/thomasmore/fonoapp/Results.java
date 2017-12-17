package be.thomasmore.fonoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Results extends AppCompatActivity {

    int teller;
    int fouten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        teller = bundle.getInt("teller");
        fouten = bundle.getInt("fouten");

        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText("Score: " + String.valueOf(teller));

        TextView errorView = (TextView) findViewById(R.id.errors);
        errorView.setText("Fouten: " + String.valueOf(fouten));

        Button button = (Button) findViewById(R.id.back);
        button.setText("Terug");
    }

    public void backToHome(View v) {
        Intent intent = new Intent(this, CategorySelect.class);
        startActivity(intent);
    }
}
