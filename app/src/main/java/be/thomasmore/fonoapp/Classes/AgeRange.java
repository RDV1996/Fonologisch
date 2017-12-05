package be.thomasmore.fonoapp.Classes;

/**
 * Created by robyd on 5/12/2017.
 */

public class AgeRange {
    private int id;
    private int minAge;
    private int maxAge;

    public AgeRange(int id, int minAge, int maxAge) {
        this.id = id;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
