package be.thomasmore.fonoapp.test;

import java.util.ArrayList;

import be.thomasmore.fonoapp.Classes.AgeRange;
import be.thomasmore.fonoapp.Classes.DisorderType;
import be.thomasmore.fonoapp.Classes.Word;
import be.thomasmore.fonoapp.Classes.WordPair;
import be.thomasmore.fonoapp.Classes.WordPairType;

/**
 * Created by robyd on 5/12/2017.
 */

public class TestAPI {
    private ArrayList<AgeRange> ageRanges;
    private ArrayList<DisorderType> disorderTypes;
    private ArrayList<Word> words;
    private ArrayList<WordPair> wordPairs;
    private ArrayList<WordPairType> wordPairTypes;

    public TestAPI() {
        this.ageRanges = new ArrayList<>();
        this.disorderTypes = new ArrayList<>();
        this.words = new ArrayList<>();
        this.wordPairs = new ArrayList<>();
        this.wordPairTypes = new ArrayList<>();
        this.words.add(new Word("1", "Beer", "img_beer", "img_beer2", "beer.m4a", "In dit bos zit een...", "beer_zin.m4a"));
        this.words.add(new Word("2", "Peer", "img_peer", "img_peer2", "peer.m4a", "De jonge eet zijn...", "peer_zin.m4a"));
        this.words.add(new Word("3", "Baard", "main_baard", "img_baard2", "baard.m4a", "Deze man heeft een lange...", "baard_zin.m4a"));
        this.words.add(new Word("4", "Paard", "img_paard", "img_paard2", "paard.m4a", "De vrouw rijd een...", "paard_zin.m4a"));
        this.words.add(new Word("5", "Bed", "img_bed", "img_bed2", "bed.m4a", "de knuffel ligt in het ...", "bed_zin.m4a"));
        this.words.add(new Word("6", "pet", "img_pet", "img_pet2", "pet.m4a", "De jonge draagt een mooie...", "pet_zin.m4a"));
        this.words.add(new Word("7", "dak", "img_dak", "img_beer2", "dak.m4a", "Er zit een lek in het...", "dak_zin.m4a"));
        this.words.add(new Word("8", "tak", "img_tak", "img_beer2", "tak.m4a", "Aan die boom hangt een...", "tak_zin.m4a"));
        this.words.add(new Word("9", "Das", "img_Das", "img_Das2", "Das.m4a", "De man draagt een mooie...", "Das_zin.m4a"));
        this.words.add(new Word("10", "Tas", "img_Tas", "img_Tas2", "Tas.m4a", "De vrouw heeft een mooie...", "tas_zin.m4a"));

        this.words.add(new Word("11", "Rapen", "img_rapen", "img_rapen2", "rapen.m4a", "De kinderen zijn vuil aan het...", "rapen_zin.m4a"));
        this.words.add(new Word("12", "Wapen", "img_Wapen", "img_wapen2", "Wapen.m4a", "Een geweer is een...", "wapen_zin.m4a"));
        this.words.add(new Word("13", "Rij", "img_rij", "img_rij2", "rij.m4a", "Deze mensen staan in een lange...", "rij_zin.m4a"));
        this.words.add(new Word("14", "Wei", "img_Wei", "img_Wei2", "wei.m4a", "De koeien staan in de...", "wei_zin.m4a"));

        this.disorderTypes.add(new DisorderType("1", "Verstemlozing"));
        this.disorderTypes.add(new DisorderType("2", "Gliding"));

        this.ageRanges.add(new AgeRange("1", 3, 4));
        this.ageRanges.add(new AgeRange("2", 5, 6));

        this.wordPairTypes.add(new WordPairType("1", "B", "P", "1"));
        this.wordPairTypes.add(new WordPairType("2", "D", "T", "1"));
        this.wordPairTypes.add(new WordPairType("3", "R", "W", "2"));

        this.wordPairs.add(new WordPair("1", "1", "2", "1", "1"));
        this.wordPairs.add(new WordPair("1", "3", "4", "1", "1"));
        this.wordPairs.add(new WordPair("1", "5", "6", "1", "1"));
        this.wordPairs.add(new WordPair("1", "7", "8", "2", "1"));
        this.wordPairs.add(new WordPair("1", "9", "10", "2", "1"));
        this.wordPairs.add(new WordPair("1", "11", "12", "1", "2"));
        this.wordPairs.add(new WordPair("1", "13", "14", "2", "2"));

    }

    public ArrayList<AgeRange> getAgeRanges() {
        return ageRanges;
    }

    public void setAgeRanges(ArrayList<AgeRange> ageRanges) {
        this.ageRanges = ageRanges;
    }

    public ArrayList<DisorderType> getDisorderTypes() {
        return disorderTypes;
    }

    public void setDisorderTypes(ArrayList<DisorderType> disorderTypes) {
        this.disorderTypes = disorderTypes;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }

    public ArrayList<WordPair> getWordPairs() {
        return wordPairs;
    }

    public void setWordPairs(ArrayList<WordPair> wordPairs) {
        this.wordPairs = wordPairs;
    }

    public ArrayList<WordPairType> getWordPairTypes() {
        return wordPairTypes;
    }

    public void setWordPairTypes(ArrayList<WordPairType> wordPairTypes) {
        this.wordPairTypes = wordPairTypes;
    }

    public ArrayList<WordPairType> getWordpairsTypeByDisorderType(String DisorderId) {
        ArrayList<WordPairType> temp = new ArrayList<>();
        for (int i = 0; i < this.wordPairTypes.size(); i++) {
            if (wordPairTypes.get(i).getDisorderTypeId() == DisorderId) {
                temp.add(wordPairTypes.get(i));
            }
        }
        return temp;
    }

    public ArrayList<WordPair> getWordpairsByWPTandAge(String wpt, String age) {
        ArrayList<WordPair> temp = new ArrayList<>();
        for (int i = 0; i < this.wordPairs.size(); i++) {
            if (wordPairs.get(i).getAgeRangeId() == age && wordPairs.get(i).getWordPairTypeId() == wpt) {
                temp.add(wordPairs.get(i));
            }
        }
        return temp;
    }

    public Word getWordbyId(String id) {
        for (int i = 0; i < this.words.size(); i++) {
            if (words.get(i).getId() == id) {
                return words.get(i);
            }
        }
        return new Word();
    }


}
