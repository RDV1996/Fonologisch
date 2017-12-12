package be.thomasmore.fonoapp;

import android.content.Intent;
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

import java.util.ArrayList;

import be.thomasmore.fonoapp.Classes.AgeRange;
import be.thomasmore.fonoapp.Classes.DisorderType;
import be.thomasmore.fonoapp.Classes.WordPairType;
import be.thomasmore.fonoapp.test.TestAPI;

public class CategorySelect extends AppCompatActivity {

    Button buttons[] = new Button[2];
    CheckBox checkBox[] = new CheckBox[5];
    int selectedAge;
    int selectedType;
    ArrayList<Integer> selectedTypeWords;
    TestAPI testAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testAPI = new TestAPI();
        makeAgeLayout();
        selectedTypeWords = new ArrayList<>();
    }

    private void makeAgeLayout() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
        linearLayout.removeAllViews();
        ArrayList<AgeRange> ages = testAPI.getAgeRanges();

        for (int i = 0; i < ages.size(); i++) {
            Button button = new Button(this);
            button.setText(ages.get(i).getMinAge() + " - " + ages.get(i).getMaxAge());
            button.setTag(ages.get(i).getId());
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
                    selectedAge = Integer.parseInt(v.getTag().toString());
                    makeCategoryLayout((Button) v);
                }
            });

            buttons[i] = button;
            linearLayout.addView(button);
        }
    }

    private void makeCategoryLayout(View v) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
        linearLayout.removeAllViews();

        ArrayList<DisorderType> types = testAPI.getDisorderTypes();

        for (int i = 0; i < types.size(); i++) {
            Button button = new Button(this);
            button.setText(types.get(i).getName());
            button.setTag(types.get(i).getId());
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
                    selectedType = Integer.parseInt(v.getTag().toString());
                    makeLetterLayout((Button) v);
                }
            });

            buttons[i] = button;
            linearLayout.addView(button);
        }
    }

    private void makeLetterLayout(View v) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.removeAllViews();
        LinearLayout checkLayout = new LinearLayout(this);
        checkLayout.setGravity(1);
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);

        ArrayList<WordPairType> wordTypes = testAPI.getWordpairsTypeByDisorderType(selectedType);

        for (int i = 0; i < wordTypes.size(); i++) {
            CheckBox check = new CheckBox(this);
            check.setText(wordTypes.get(i).getFrom() + " - " + wordTypes.get(i).getTo());
            check.setTag(wordTypes.get(i).getId());
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
                        selectedTypeWords.add(new Integer(Integer.parseInt(v.getTag().toString())));
                        v.setBackgroundResource(R.drawable.border_blue);
                    } else {
                        selectedTypeWords.remove(selectedTypeWords.indexOf(new Integer(Integer.parseInt(v.getTag().toString()))));
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
                nextActivity();
            }
        });

        linearLayout.addView(checkLayout);
        linearLayout.addView(button);
    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

    private void ExFour() {
        Intent intent = new Intent(this, ExerciseFour.class);
        startActivity(intent);
    }

    private void nextActivity() {
        Intent intent = new Intent(this, ExerciseOne.class);
        startActivity(intent);
    }


}
