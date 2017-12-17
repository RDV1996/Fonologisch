package be.thomasmore.fonoapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExerciseThree extends AppCompatActivity {

    int column = 2;
    int row = 2;
    int total = column * row;
    TextView textViews[] = new TextView[total];

    int cijfers = 1;
    int teller;
    int fouten;

    int audioTeller = 0;
    List<String> imageFiles;
    List<String> audioFiles;

    SoundPool soundPool;
    int[] sm = new int[4];
    AudioManager amg;

    List<String> imageViewFilesString = Arrays.asList("leftTop", "leftBottom", "rightTop", "rightBottom");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_three);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        teller = bundle.getInt("teller");
        fouten = bundle.getInt("fouten");

        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(String.valueOf(teller));

        findMedia();
        initSound();

        numbers();

        findViewById(R.id.leftTop).setOnDragListener(new ExerciseThree.MyDragListener());
        findViewById(R.id.rightTop).setOnDragListener(new ExerciseThree.MyDragListener());
        findViewById(R.id.leftBottom).setOnDragListener(new ExerciseThree.MyDragListener());
        findViewById(R.id.rightBottom).setOnDragListener(new ExerciseThree.MyDragListener());
    }

    private void findMedia() {
        imageFiles = new ArrayList<>();
        audioFiles = new ArrayList<>();

        Collections.shuffle(imageViewFilesString);

        Field[] raws = be.thomasmore.fonoapp.R.raw.class.getFields();
        Field[] drawables = be.thomasmore.fonoapp.R.drawable.class.getFields();
        for (Field f : raws) {
            audioFiles.add(f.getName());

            for (Field g : drawables) {
                if (g.getName().startsWith("img_" + audioFiles.get(audioTeller))) {
                    imageFiles.add(g.getName());
                }
            }
            audioTeller++;
        }
        audioFiles.remove(0);
        audioFiles.remove(audioFiles.size() - 1);
    }

    private void initSound() {
        int maxStreams = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(maxStreams)
                    .build();
        } else {
            soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
        }

        for (int i = 0; i < 4; i++) {
            sm[i] = soundPool.load(this, getResources().getIdentifier(audioFiles.get(i), "raw", getPackageName()), 1);
        }

        amg = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void nextActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt("teller", teller);
        bundle.putInt("fouten", fouten);
        Intent intent = new Intent(this, ExerciseFour.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void numbers() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.numbers);
        mainLayout.removeAllViews();

        for (int i = 0; i < row; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            mainLayout.addView(linearLayout);
            for (int j = 0; j < column; j++) {
                TextView textView = new TextView(this);
                textView.setBackgroundResource(R.drawable.border_black);

                LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(100, 100);
                textLayoutParams.leftMargin = 5;
                textLayoutParams.topMargin = 5;
                textLayoutParams.width = 150;
                textLayoutParams.height = 150;
                textView.setLayoutParams(textLayoutParams);

                String imageFile = imageViewFilesString.get(cijfers - 1);
                ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(imageFile, "id", getPackageName()));
                imageView.setImageResource(getResources().getIdentifier(imageFiles.get(imageViewFilesString.indexOf(imageFile)), "drawable", getPackageName()));
                imageView.setTag(cijfers - 1);

                textView.setText(String.valueOf(cijfers));
                textView.setTag(cijfers - 1);

                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);

                textView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            ClipData data = ClipData.newPlainText("", "");
                            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                            view.startDrag(data, shadowBuilder, view, 0);
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                textViews[i] = textView;
                linearLayout.addView(textView);
                cijfers++;
            }
        }
    }

    // Kent punten toe bij een juiste sleepactie.
    private void correctDrag(View view, TextView scoreView) {
        view.setOnTouchListener(null);
        view.setBackgroundResource(getResources().getIdentifier(imageFiles.get((Integer)view.getTag()), "drawable", getPackageName()));
        teller++;
        scoreView.setText(String.valueOf(teller));
        if (teller == 20) {
            onCompletion();
        }
    }

    // Genereert de geluiden
    private void randomSounds(int k) {
        soundPool.play(sm[k], 1, 1, 1, 0, 1f);
    }

    // De DragListener die ervoor zorgt dat bij het slepen naar de juiste afbeelding, de juiste zaken gebeuren
    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();
            TextView dropped = (TextView) view;
            String tag = dropped.getTag() + "";

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    randomSounds(Integer.parseInt(tag));
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    ImageView dropTarget = (ImageView) v;
                    TextView scoreView = (TextView) findViewById(R.id.score);
                    String tagTarget = dropTarget.getTag() + "";

                    if (tag.equals(tagTarget)) {
                        correctDrag(view, scoreView);
                    } else {
                        fouten++;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }
    }

    // DialogBox wanneer de oefening voorbij is. Gaat naar de volgende oefening
    public void onCompletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fouten: " + String.valueOf(fouten))
                .setTitle(R.string.title_activity_exercise_three)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nextActivity();
                        cleanUpIfEnd();
                    }
                })
                .show();
    }

    // Maakt de SoundPool leeg
    public final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}