package be.thomasmore.fonoapp.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by robyd on 5/12/2017.
 */

public class AgeRange {
    @SerializedName("_id")
    private String id;
    @SerializedName("minAge")
    private int minAge;
    @SerializedName("maxAge")
    private int maxAge;

    public AgeRange(String id, int minAge, int maxAge) {
        this.id = id;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public AgeRange() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
