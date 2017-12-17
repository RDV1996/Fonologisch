package be.thomasmore.fonoapp;

import android.content.Intent;
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
import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.Classes.WordPair;
import be.thomasmore.fonoapp.Classes.WordPairType;
import be.thomasmore.fonoapp.rest.APIClient;
import be.thomasmore.fonoapp.rest.APIInterface;
import be.thomasmore.fonoapp.test.TestAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static be.thomasmore.fonoapp.Global.words;

public class CategorySelect extends AppCompatActivity {

    Button buttons[] = new Button[2];
    CheckBox checkBox[] = new CheckBox[5];
    String selectedAge;
    String selectedType;
    String selectedTypeWord;
    TestAPI testAPI;
    APIInterface apiInetface;
    ArrayList<AgeRange> ages;
    ArrayList<DisorderType> disorderTypes;
    ArrayList<WordPairType> wordPairTypes;
    ArrayList<WordPair> wordPairs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        apiInetface = APIClient.getClient().create(APIInterface.class);
        makeAgeLayout();
        Global.words = new ArrayList<>();

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

                    for (int i = 0; i < disorderTypes.size(); i++) {
                        Button button = new Button(getApplicationContext());
                        button.setText(disorderTypes.get(i).getName());
                        button.setTag(disorderTypes.get(i).getId());
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
        Call<ArrayList<WordPairType>> call = apiInetface.getWordPairTypes();
        call.enqueue(new Callback<ArrayList<WordPairType>>() {
            @Override
            public void onResponse(Call<ArrayList<WordPairType>> call, Response<ArrayList<WordPairType>> response) {
                if (response.isSuccessful()) {
                    wordPairTypes = response.body();
                    Toast.makeText(getApplicationContext(), "succesvol types",
                            Toast.LENGTH_LONG).show();

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ageSelect);
                    linearLayout.removeAllViews();
                    for (int i = 0; i < wordPairTypes.size(); i++) {
                        Button button = new Button(getApplicationContext());
                        button.setText(wordPairTypes.get(i).getFrom() + " -> " + wordPairTypes.get(i).getTo());
                        button.setTag(wordPairTypes.get(i).getId());
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
                                selectedTypeWord = v.getTag().toString();
                                GetWords();
                            }
                        });

                        buttons[i] = button;
                        linearLayout.addView(button);
                    }

                    } else

                    {
                        Toast.makeText(getApplicationContext(), response.message(),
                                Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure (Call < ArrayList < WordPairType >> call, Throwable t){
                    call.cancel();
                    Toast.makeText(getApplicationContext(), t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });


    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }


    private void GetWords() {
        Call<ArrayList<WordPair>> call = apiInetface.getWordPaires(selectedAge, selectedTypeWord);
        call.enqueue(new Callback<ArrayList<WordPair>>() {
            @Override
            public void onResponse(Call<ArrayList<WordPair>> call, Response<ArrayList<WordPair>> response) {
                if (response.isSuccessful()) {
                    wordPairs = response.body();
                    Global.wordPairs = wordPairs;
                    for (int i = 0; i < wordPairs.size(); i++) {
                        setWords(wordPairs.get(i).getWrongWordId());
                        setWords(wordPairs.get(i).getRightWordId());
                    }


                } else {
                    Toast.makeText(getApplicationContext(), response.message(),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<WordPair>> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setWords(String right) {
        Call<Word> call = apiInetface.getWord(right);
        call.enqueue(new Callback<Word>() {
            @Override
            public void onResponse(Call<Word> call, Response<Word> response) {
                if (response.isSuccessful()) {
                    Global.words.add(response.body());
                    if(words.size() == Global.wordPairs.size() * 2){
                        Intent intent = new Intent(getApplicationContext(), ExerciseOne.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), response.message(),
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Word> call, Throwable t) {
                call.cancel();
                Toast.makeText(getApplicationContext(), t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
