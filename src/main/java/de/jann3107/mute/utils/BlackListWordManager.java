package de.jann3107.mute.utils;

import java.util.ArrayList;

public class BlackListWordManager {
    public boolean isBlackListed(String word) {
        ArrayList<String> variations = getVariations(word.toLowerCase());
        for(String vari : variations) {
            System.out.println("Checking: " + vari);
            if(isOnBlacklist(vari)) {
                return true;
            }
        }
        return false;
    }
    private boolean isOnBlacklist(String word){
        return false;
    }
    private ArrayList<String> getVariations(String word){
        ArrayList<String> variations = new ArrayList<>();
        variations.add(word);
        variations.add(word.replaceAll("@", "a"));
        variations.add(word.replaceAll("@", "o"));
        variations.add(word.replace("!", "i"));
        variations.add(word.replace("!", "l"));
        variations.add(word.replaceAll(".", ""));
        variations.add(word.replaceAll(",", ""));
        variations.add(word.replace("*", ""));
        variations.add(word.replace("+", ""));
        variations.add(word.replace("*", ""));
        variations.add(word.replace("~", ""));
        variations.add(word.replace("!", ""));
        variations.add(word.replace("?", ""));
        variations.add(word.replace("/", ""));
        variations.add(word.replace("\"", ""));
        variations.add(word.replace("+", ""));
        variations.add(word.replace(" ", ""));
        variations.add(word.replace(".", "").replaceAll(",", "").replace("*", "").replace("+", "").replace("*", "").replace("~", "").replace("!", "").replace("?", "").replace("/", "").replace("\"", "").replace("+", "").replace(" ", ""));

        return variations;
    }
}
