package be.thomasmore.fonoapp;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
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

import java.util.Random;

import be.thomasmore.fonoapp.Classes.Word;

public class ExerciseOne extends AppCompatActivity {

    int teller = 0;
    int fouten = 0;
    int idTeller = 1;
    int aantalFotos = 9;
    int column = aantalFotos / 3;
    int row = aantalFotos / 3;
    int total = column * row;
    ImageView imageViews[] = new ImageView[total];

    SoundPool soundPool;
    int[] sm = new int[2];
    AudioManager amg;

    Word leftWord;
    Word rightWord;
    int counter = 0;

    MediaPlayer playSound;
    int media_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initWords();
        initSound();
        basePictures();

        findViewById(R.id.right).setOnDragListener(new MyDragListener());
        findViewById(R.id.left).setOnDragListener(new MyDragListener());

        playSound = MediaPlayer.create(this,R.raw.instructie1);
        playSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer player){
                randomPictures();
                TextView scoreView = (TextView) findViewById(R.id.score);
                scoreView.setText(String.valueOf(Global.score));
            }
        });
        playSound.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(playSound != null)
        {
            playSound.pause();
            media_length = playSound.getCurrentPosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(playSound != null)
        {
            playSound.seekTo(media_length);
            playSound.start();
        }

    }

    private void initWords() {
        if (Math.random() < 0.5) {
            leftWord = Global.getWordById(Global.wordPairs.get(counter).getRightWordId());
            rightWord = Global.getWordById(Global.wordPairs.get(counter).getWrongWordId());
        } else {
            leftWord = Global.getWordById(Global.wordPairs.get(counter).getWrongWordId());
            rightWord = Global.getWordById(Global.wordPairs.get(counter).getRightWordId());
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

        sm[0] = soundPool.load(this, getResources().getIdentifier(leftWord.getWordsound(), "raw", getPackageName()), 1);
        sm[1] = soundPool.load(this, getResources().getIdentifier(rightWord.getWordsound(), "raw", getPackageName()), 1);

        amg = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void nextActivity() {
        Intent intent = new Intent(this, ExerciseTwo.class);
        startActivity(intent);
    }

    private void basePictures() {

        ImageView imageViewLeft = (ImageView) findViewById(getResources().getIdentifier("left", "id", getPackageName()));
        imageViewLeft.setImageResource(getResources().getIdentifier(leftWord.getSubImg(), "drawable", getPackageName()));
        imageViewLeft.setTag(0);

        ImageView imageViewRight = (ImageView) findViewById(getResources().getIdentifier("right", "id", getPackageName()));
        imageViewRight.setImageResource(getResources().getIdentifier(rightWord.getSubImg(), "drawable", getPackageName()));
        imageViewRight.setTag(1);
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

                if (k == 0) {
                    imageView.setImageResource(getResources().getIdentifier(leftWord.getMainImg(), "drawable", getPackageName()));
                } else {
                    imageView.setImageResource(getResources().getIdentifier(rightWord.getMainImg(), "drawable", getPackageName()));
                }

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
                .setCancelable(false)
                .show();
    }

    // Kent punten toe bij een juiste sleepactie.
    private void correctDrag(ViewGroup owner, View view, TextView scoreView) {
        owner.removeView(view);
        teller++;
        scoreView.setText(String.valueOf(teller));
        if (teller >= aantalFotos) {
            Global.score = teller;
            Global.foutenLijst.add(fouten);
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
