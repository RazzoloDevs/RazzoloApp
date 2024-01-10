package it.unisa.razzolo.model;

import java.util.HashSet;

public class HashDictionary {
    private static HashDictionary _instance;
    private static HashSet<String> set;

    private HashDictionary() {
        set = new HashSet<>();
    }

    public static HashDictionary getInstance() {
        if (_instance == null)
            _instance = new HashDictionary();

        return _instance;
    }

    public void put(final String s){
        set.add(s);
    }

    public HashSet<String> getSet(){
        return set;
    }
}
