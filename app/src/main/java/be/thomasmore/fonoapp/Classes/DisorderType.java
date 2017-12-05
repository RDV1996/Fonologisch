package be.thomasmore.fonoapp.Classes;

/**
 * Created by robyd on 5/12/2017.
 */

public class DisorderType {
    private int id;
    private String name;

    public DisorderType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
