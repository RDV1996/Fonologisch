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
import java.util.List;
import java.util.Random;

public class ExerciseOne extends AppCompatActivity {

    int teller = 0;
    int fouten = 0;
    int idTeller = 1;
    int aantalFotos = 9;
    int column = aantalFotos / 3;
    int row = aantalFotos / 3;
    int total = column * row;
    ImageView imageViews[] = new ImageView[total];

    int audioTeller = 0;
    List<String> imageFiles;
    List<String> audioFiles;
    List<String> imageViewFilesString = Arrays.asList("left", "right");

    SoundPool soundPool;
    int[] sm = new int[2];
    AudioManager amg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView scoreView = (TextView) findViewById(R.id.score);
        scoreView.setText(String.valueOf(teller));

        findMedia();
        initSound();
        basePictures();
        randomPictures();

        findViewById(R.id.right).setOnDragListener(new MyDragListener());
        findViewById(R.id.left).setOnDragListener(new MyDragListener());
    }

    private void findMedia() {
        imageFiles = new ArrayList<>();
        audioFiles = new ArrayList<>();

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

        for (int i = 0; i < 2; i++) {
            sm[i] = soundPool.load(this, getResources().getIdentifier(audioFiles.get(i + 1), "raw", getPackageName()), 1);
        }

        amg = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void nextActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt("teller", teller);
        bundle.putInt("fouten", fouten);
        Intent intent = new Intent(this, ExerciseTwo.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void basePictures() {
        for (int i = 0; i < imageViewFilesString.size(); i++){
            String imageFile = imageViewFilesString.get(i);
            ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(imageFile, "id", getPackageName()));
            imageView.setImageResource(getResources().getIdentifier(imageFiles.get(imageViewFilesString.indexOf(imageFile)), "drawable", getPackageName()));
            imageView.setTag(i);
        }
    }

    private void randomPictures() {
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.randomPictures);
        mainLayout.removeAllViews();

        Random rand = new Random();
        int k;

        for (int i = 0; i < row; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            mainLayout.addView(linearLayout);
            for (int j = 0; j < column; j++) {
                ImageView imageView = new ImageView(this);
                imageView.setBackgroundResource(R.drawable.border_black);

                LinearLayout.LayoutParams[] imageLayoutParams = {new LinearLayout.LayoutParams(100, 100)};
                imageLayoutParams[0].leftMargin = 5;
                imageLayoutParams[0].topMargin = 5;
                imageLayoutParams[0].width = 110;
                imageLayoutParams[0].height = 110;
                imageView.setLayoutParams(imageLayoutParams[0]);

                k = rand.nextInt(2);

                imageView.setImageResource(getResources().getIdentifier(imageFiles.get(k), "drawable", getPackageName()));
                imageView.setBackgroundResource(R.drawable.border_black);

                imageView.setTag(k);

                imageView.setOnTouchListener(new View.OnTouchListener() {
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

                imageViews[i] = imageView;
                linearLayout.addView(imageView);
                idTeller++;
            }
        }
    }

    // DialogBox wanneer de oefening voorbij is. Gaat naar de volgende oefening
    public void onCompletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fouten: " + String.valueOf(fouten))
                .setTitle(R.string.title_activity_exercise_one)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        nextActivity();
                        cleanUpIfEnd();
                    }
                })
                .show();
    }

    // Kent punten toe bij een juiste sleepactie.
    private void correctDrag(ViewGroup owner, View view, TextView scoreView) {
        owner.removeView(view);
        teller++;
        scoreView.setText(String.valueOf(teller));
        if (teller == aantalFotos) {
            onCompletion();
        }
    }

    // Genereert de geluiden
    private void randomSounds(int k) {
        soundPool.play(sm[k],1,1,1,0,1f);
    }

    // De DragListener die ervoor zorgt dat bij het slepen naar de juiste afbeelding, de juiste zaken gebeuren
    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();
            ImageView dropped = (ImageView) view;
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
                    ViewGroup owner = (ViewGroup) view.getParent();
                    TextView scoreView = (TextView) findViewById(R.id.score);
                    String tagTarget = dropTarget.getTag() + "";

                    if (tag.equals(tagTarget)) {
                        correctDrag(owner, view, scoreView);
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

    // Maakt de SoundPool leeg
    public final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}
