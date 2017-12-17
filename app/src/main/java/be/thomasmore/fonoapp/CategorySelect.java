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
import be.thomasmore.fonoapp.rest.APIClient;
import be.thomasmore.fonoapp.rest.APIInterface;
import be.thomasmore.fonoapp.test.TestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategorySelect extends AppCompatActivity {

    Button buttons[] = new Button[2];
    CheckBox checkBox[] = new CheckBox[5];
    String selectedAge;
    String selectedType;
    ArrayList<String> selectedTypeWords;
    TestAPI testAPI;
    APIInterface apiInetface;
    ArrayList<AgeRange> ages;
    ArrayList<DisorderType> disorderTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testAPI = new TestAPI();
        apiInetface = APIClient.getClient().create(APIInterface.class);
        makeAgeLayout();
        selectedTypeWords = new ArrayList<>();

    }

    private void makeAgeLayout() {
        Call<ArrayList<AgeRange>> call = apiInetface.getAgeRanges();
        call.enqueue(new Callback<ArrayList<AgeRange>>() {
            @Override
            public void onResponse(Call<ArrayList<AgeRange>> call, Response<ArrayList<AgeRange>> response) {
                if (response.isSuccessful()) {
                    ages = response.body();
                    Toast.makeText(getApplicationContext(), "succesvol age",
                            Toast.LENGTH_LONG).show();

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
                    linearLayout.removeAllViews();
                    for (int i = 0; i < ages.size(); i++) {
                        Button button = new Button(getApplicationContext());
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
                                selectedAge = v.getTag().toString();
                                makeCategoryLayout((Button) v);
                            }
                        });

                        buttons[i] = button;
                        linearLayout.addView(button);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.message(),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<AgeRange>> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void makeCategoryLayout(View v) {
        Call<ArrayList<DisorderType>> call = apiInetface.getDisorderTypes();
        call.enqueue(new Callback<ArrayList<DisorderType>>() {
            @Override
            public void onResponse(Call<ArrayList<DisorderType>> call, Response<ArrayList<DisorderType>> response) {
                if (response.isSuccessful()) {
                    disorderTypes = response.body();

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
                    linearLayout.removeAllViews();

                    ArrayList<DisorderType> types = testAPI.getDisorderTypes();

                    for (int i = 0; i < types.size(); i++) {
                        Button button = new Button(getApplicationContext());
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
                                selectedType = v.getTag().toString();
                                makeLetterLayout((Button) v);
                            }
                        });

                        buttons[i] = button;
                        linearLayout.addView(button);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), response.message(),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<DisorderType>> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
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
                        selectedTypeWords.add(v.getTag().toString());
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
