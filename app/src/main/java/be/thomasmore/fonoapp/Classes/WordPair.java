package be.thomasmore.fonoapp.Classes;

/**
 * Created by robyd on 5/12/2017.
 */

public class WordPair {
    private int id;
    private int rightWordId;
    private int wrongWordId;
    private int ageRangeId;
    private int wordPairTypeId;

    public WordPair(int id, int rightWordId, int wrongWordId, int ageRangeId, int wordPairTypeId) {
        this.id = id;
        this.rightWordId = rightWordId;
        this.wrongWordId = wrongWordId;
        this.ageRangeId = ageRangeId;
        this.wordPairTypeId = wordPairTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRightWordId() {
        return rightWordId;
    }

    public void setRightWordId(int rightWordId) {
        this.rightWordId = rightWordId;
    }

    public int getWrongWordId() {
        return wrongWordId;
    }

    public void setWrongWordId(int wrongWordId) {
        this.wrongWordId = wrongWordId;
    }

    public int getAgeRangeId() {
        return ageRangeId;
    }

    public void setAgeRangeId(int ageRangeId) {
        this.ageRangeId = ageRangeId;
    }

    public int getWordPairTypeId() {
        return wordPairTypeId;
    }

    public void setWordPairTypeId(int wordPairTypeId) {
        this.wordPairTypeId = wordPairTypeId;
    }
}
