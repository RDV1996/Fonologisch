package be.thomasmore.fonoapp.Classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by robyd on 15/12/2017.
 */

public class User {
    @SerializedName("_id")
    private String id;
    @SerializedName("token")
    private String token;
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
