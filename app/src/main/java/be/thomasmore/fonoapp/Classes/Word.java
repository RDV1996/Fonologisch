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
    @SerializedName("sentence")
    private String sentence;


    public Word(String id, String word, String sentence) {
        this.id = id;
        this.word = word;

        this.sentence = sentence;
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

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }


}
