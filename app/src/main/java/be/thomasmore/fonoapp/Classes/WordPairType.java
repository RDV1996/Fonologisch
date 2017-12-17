package be.thomasmore.fonoapp.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by robyd on 5/12/2017.
 */

public class WordPairType {
    @SerializedName("_id")
    private String id;
    @SerializedName("from")
    private String From;
    @SerializedName("to")
    private String To;
    @SerializedName("disorderType")
    private String DisorderTypeId;

    public WordPairType(String id, String from, String to, String disorderTypeId) {
        this.id = id;
        From = from;
        To = to;
        DisorderTypeId = disorderTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getDisorderTypeId() {
        return DisorderTypeId;
    }

    public void setDisorderTypeId(String disorderTypeId) {
        DisorderTypeId = disorderTypeId;
    }
}
