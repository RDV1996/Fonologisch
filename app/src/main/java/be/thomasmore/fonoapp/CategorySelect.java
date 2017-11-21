package be.thomasmore.fonoapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CategorySelect extends AppCompatActivity {

    Button buttons[] = new Button[2];
    CheckBox checkBox[] = new CheckBox[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        maakAgeLayout();
    }

    private void maakAgeLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
        linearLayout.removeAllViews();

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

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    maakCategoryLayout((Button) v);
                }
            });

            buttons[i] = button;
            linearLayout.addView(button);
        }
    }

    private void maakCategoryLayout(View v) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
        linearLayout.removeAllViews();

        for (int i = 0; i < 2; i++) {
            Button button = new Button(this);
            button.setText("Soort");
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

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    maakLetterLayout((Button) v);
                }
            });

            buttons[i] = button;
            linearLayout.addView(button);
        }
    }

    private void maakLetterLayout(View v) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.removeAllViews();
        LinearLayout checkLayout = new LinearLayout(this);
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);

        for (int i = 0; i < 5; i++) {
            CheckBox check = new CheckBox(this);
            check.setText("A");
            check.setTag(i);
            check.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            check.setGravity(Gravity.CENTER);
            check.setBackgroundResource(R.drawable.border_black);
            check.setButtonDrawable(transparentDrawable);

            LinearLayout.LayoutParams layoutTextParams =
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutTextParams.leftMargin = 10;
            layoutTextParams.rightMargin = 10;
            layoutTextParams.height = 200;
            layoutTextParams.width = 200;
            check.setLayoutParams(layoutTextParams);

            check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox box = (CheckBox) v;
                    if (box.isChecked()) {
                        v.setBackgroundResource(R.drawable.border_blue);
                    } else {
                        v.setBackgroundResource(R.drawable.border_black);
                    }

                }
            });

            checkBox[i] = check;
            checkLayout.addView(check);
        }
        Button button = new Button(this);
        button.setText("start spel");
        button.setTag("start");
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toon(v.getTag() + "");
            }
        });

        linearLayout.addView(checkLayout);
        linearLayout.addView(button);
    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

}
