package be.thomasmore.fonoapp.Classes;

/**
 * Created by robyd on 5/12/2017.
 */

public class Word {
    private int id;
    private String word;
    private String mainImg;
    private String subImg;
    private String Wordsound;
    private String sentence;
    private String sentenceSound;

    public Word(int id, String word, String mainImg, String subImg, String wordsound, String sentence, String sentenceSound) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
