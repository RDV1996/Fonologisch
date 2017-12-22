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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import be.thomasmore.fonoapp.Classes.Word;

public class ExerciseThree extends AppCompatActivity {

    int column = 2;
    int row = 2;
    int total = column * row;
    ImageView imageViews[] = new ImageView[total];

    int cijfers = 1;
    int teller = 0;
    int fouten = 0;

    SoundPool soundPool;
    int[] sm = new int[5];
    AudioManager amg;

    Word leftBottomWord;
    Word leftTopWord;
    Word rightBottomWord;
    Word rightTopWord;

    List<String> imageViewFilesString = Arrays.asList("leftTop", "leftBottom", "rightTop", "rightBottom");

    MediaPlayer playSound;
    int media_length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_three);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Collections.shuffle(Global.wordPairs);

        initWord();
        initSound();

        findViewById(R.id.leftTop).setOnDragListener(new ExerciseThree.MyDragListener());
        findViewById(R.id.rightTop).setOnDragListener(new ExerciseThree.MyDragListener());
        findViewById(R.id.leftBottom).setOnDragListener(new ExerciseThree.MyDragListener());
        findViewById(R.id.rightBottom).setOnDragListener(new ExerciseThree.MyDragListener());

        playSound = MediaPlayer.create(this,R.raw.instructie3);
        playSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer player) {
                numbers();
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

    private void initWord() {
        if (Math.random() < 0.5) {
            leftBottomWord = Global.getWordById(Global.wordPairs.get(0).getRightWordId());
            leftTopWord = Global.getWordById(Global.wordPairs.get(1).getRightWordId());
            rightBottomWord = Global.getWordById(Global.wordPairs.get(0).getWrongWordId());
            rightTopWord = Global.getWordById(Global.wordPairs.get(1).getWrongWordId());
        } else {
            leftBottomWord = Global.getWordById(Global.wordPairs.get(0).getRightWordId());
            leftTopWord = Global.getWordById(Global.wordPairs.get(1).getRightWordId());
            rightBottomWord = Global.getWordById(Global.wordPairs.get(0).getWrongWordId());
            rightTopWord = Global.getWordById(Global.wordPairs.get(1).getWrongWordId());
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

        sm[0] = soundPool.load(this, getResources().getIdentifier(leftBottomWord.getWordsound(), "raw", getPackageName()), 1);
        sm[1] = soundPool.load(this, getResources().getIdentifier(rightBottomWord.getWordsound(), "raw", getPackageName()), 1);
        sm[2] = soundPool.load(this, getResources().getIdentifier(leftTopWord.getWordsound(), "raw", getPackageName()), 1);
        sm[3] = soundPool.load(this, getResources().getIdentifier(rightTopWord.getWordsound(), "raw", getPackageName()), 1);

        amg = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void nextActivity() {
        Intent intent = new Intent(this, ExerciseFour.class);
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
                ImageView imageViewInner = new ImageView(this);
                imageViewInner.setBackgroundResource(R.drawable.border_black);

                LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(100, 100);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageLayoutParams.width = 150;
                imageLayoutParams.height = 150;
                imageViewInner.setLayoutParams(imageLayoutParams);

                String imageFile = imageViewFilesString.get(cijfers - 1);

                ImageView imageView = (ImageView) findViewById(getResources().getIdentifier(imageFile, "id", getPackageName()));
                if (cijfers == 1) {
                    imageView.setImageResource(getResources().getIdentifier(leftBottomWord.getMainImg(), "drawable", getPackageName()));
                    imageViewInner.setBackgroundResource(R.drawable.border_green);
                } else if (cijfers == 2) {
                    imageView.setImageResource(getResources().getIdentifier(rightBottomWord.getMainImg(), "drawable", getPackageName()));
                    imageViewInner.setBackgroundResource(R.drawable.border_black);
                } else if (cijfers == 3) {
                    imageView.setImageResource(getResources().getIdentifier(leftTopWord.getMainImg(), "drawable", getPackageName()));
                    imageViewInner.setBackgroundResource(R.drawable.border_blue);
                } else if (cijfers == 4) {
                    imageView.setImageResource(getResources().getIdentifier(rightTopWord.getMainImg(), "drawable", getPackageName()));
                    imageViewInner.setBackgroundResource(R.drawable.border_red);
                }

                imageView.setTag(cijfers - 1);


                imageViewInner.setTag(cijfers - 1);

                imageViewInner.setOnTouchListener(new View.OnTouchListener() {
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

                imageViews[i] = imageViewInner;
                linearLayout.addView(imageViewInner);
                cijfers++;
            }
        }
    }

    // Kent punten toe bij een juiste sleepactie.
    private void correctDrag(String tag, View view, TextView scoreView) {
        view.setOnTouchListener(null);

        switch (tag) {
            case "0":
                view.setBackgroundResource(getResources().getIdentifier(leftBottomWord.getMainImg(), "drawable", getPackageName()));
                break;
            case "1":
                view.setBackgroundResource(getResources().getIdentifier(rightBottomWord.getMainImg(), "drawable", getPackageName()));
                break;
            case "2":
                view.setBackgroundResource(getResources().getIdentifier(leftTopWord.getMainImg(), "drawable", getPackageName()));
                break;
            case "3":
                view.setBackgroundResource(getResources().getIdentifier(rightTopWord.getMainImg(), "drawable", getPackageName()));
                break;
            default:
                break;
        }

        teller++;
        Global.score++;
        scoreView.setText(String.valueOf(Global.score));
        if (teller >= 4) {
            Global.foutenLijst.add(fouten);
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
                    TextView scoreView = (TextView) findViewById(R.id.score);
                    String tagTarget = dropTarget.getTag() + "";

                    if (tag.equals(tagTarget)) {
                        correctDrag(tagTarget, view, scoreView);
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
                .setCancelable(false)
                .show();
    }

    // Maakt de SoundPool leeg
    public final void cleanUpIfEnd() {
        sm = null;
        soundPool.release();
        soundPool = null;
    }
}