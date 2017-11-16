package be.thomasmore.fonoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class AgeSelect extends AppCompatActivity {

    Button buttons[] = new Button[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        maakLayout();
    }

    private void maakLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);

        for (int i = 0; i < 2; i++) {
            Button button = new Button(this);
            button.setText("leefteid");
            button.setTag(i);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            button.setGravity(Gravity.CENTER);
            button.setBackgroundResource(R.drawable.border_black);

            LinearLayout.LayoutParams layoutTextParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutTextParams.leftMargin = 10;
            layoutTextParams.rightMargin = 10;
            layoutTextParams.height = 400;
            layoutTextParams.width = 400;
            button.setLayoutParams(layoutTextParams);


            buttons[i] = button;
            linearLayout.addView(button);
        }
    }

}
