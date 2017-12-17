package be.thomasmore.fonoapp.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by robyd on 5/12/2017.
 */

public class Word {
    @SerializedName("_id")
    private String id;
    @SerializedName("word")
    private String word;
    @SerializedName("mainImg")
    private String mainImg;
    @SerializedName("subImg")
    private String subImg;
    @SerializedName("wordSound")
    private String Wordsound;
    @SerializedName("sentence")
    private String sentence;
    @SerializedName("sentenceSound")
    private String sentenceSound;

    public Word(String id, String word, String mainImg, String subImg, String wordsound, String sentence, String sentenceSound) {
        this.id = id;
        this.word = word;
        this.mainImg = mainImg;
        this.subImg = subImg;
        Wordsound = wordsound;
        this.sentence = sentence;
        this.sentenceSound = sentenceSound;
    }

    public Word() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getSubImg() {
        return subImg;
    }

    public void setSubImg(String subImg) {
        this.subImg = subImg;
    }

    public String getWordsound() {
        return Wordsound;
    }

    public void setWordsound(String wordsound) {
        Wordsound = wordsound;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentenceSound() {
        return sentenceSound;
    }

    public void setSentenceSound(String sentenceSound) {
        this.sentenceSound = sentenceSound;
    }
}
