package be.thomasmore.fonoapp;

import java.util.ArrayList;

import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.Classes.WordPair;

/**
 * Created by robyd on 16/12/2017.
 */

public class Global {
    public static ArrayList<WordPair> wordPairs;
    public static ArrayList<Word> words;
    public static int score;
    public static ArrayList<Integer> foutenLijst = new ArrayList<>();

    public static Word getWordById(String id) {
        for (int i = 0; i < words.size(); i++) {
            if(words.get(i).getId().equals(id)){
                return words.get(i);
            }
        }
        return null;
    }
}
