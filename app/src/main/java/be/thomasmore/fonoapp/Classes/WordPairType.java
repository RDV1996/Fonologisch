package be.thomasmore.fonoapp.Classes;

/**
 * Created by robyd on 5/12/2017.
 */

public class WordPairType {
    private int id;
    private String From;
    private String To;
    private int DisorderTypeId;

    public WordPairType(int id, String from, String to, int disorderTypeId) {
        this.id = id;
        From = from;
        To = to;
        DisorderTypeId = disorderTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public int getDisorderTypeId() {
        return DisorderTypeId;
    }

    public void setDisorderTypeId(int disorderTypeId) {
        DisorderTypeId = disorderTypeId;
    }
}
