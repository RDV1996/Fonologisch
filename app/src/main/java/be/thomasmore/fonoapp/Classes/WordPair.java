package be.thomasmore.fonoapp.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by robyd on 5/12/2017.
 */

public class WordPair {
    @SerializedName("_id")
    private String id;
    @SerializedName("rightWord")
    private String rightWordId;
    @SerializedName("wrongWord")
    private String wrongWordId;
    @SerializedName("ageRange")
    private String ageRangeId;
    @SerializedName("wordPairType")
    private String wordPairTypeId;

    public WordPair(String id, String rightWordId, String wrongWordId, String ageRangeId, String wordPairTypeId) {
        this.id = id;
        this.rightWordId = rightWordId;
        this.wrongWordId = wrongWordId;
        this.ageRangeId = ageRangeId;
        this.wordPairTypeId = wordPairTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRightWordId() {
        return rightWordId;
    }

    public void setRightWordId(String rightWordId) {
        this.rightWordId = rightWordId;
    }

    public String getWrongWordId() {
        return wrongWordId;
    }

    public void setWrongWordId(String wrongWordId) {
        this.wrongWordId = wrongWordId;
    }

    public String getAgeRangeId() {
        return ageRangeId;
    }

    public void setAgeRangeId(String ageRangeId) {
        this.ageRangeId = ageRangeId;
    }

    public String getWordPairTypeId() {
        return wordPairTypeId;
    }

    public void setWordPairTypeId(String wordPairTypeId) {
        this.wordPairTypeId = wordPairTypeId;
    }
}
