package it.unisa.razzolo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import it.unisa.razzolo.informed.DfsTrie;
import it.unisa.razzolo.model.Point;

public class Background implements Runnable{
    private final char[][] matrix;
    private TreeMap<String, ArrayList<Point>> foundWords;
    private double elapsedTime;

    public Background(char[][] matrix){
        this.matrix = matrix;
    }

    @Override
    public void run() {
        this.foundWords = new TreeMap<>(compareStringByLength);
        long start = System.nanoTime();
        foundWords.putAll(DfsTrie.run(matrix));
        long end = System.nanoTime();
        this.elapsedTime = ((double)(end-start)/1000000000);
    }

    Comparator<String> compareStringByLength = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            if (s1.length() > s2.length())
                return -1;
            else if (s1.length() < s2.length())
                return 1;
            else
                return s1.compareTo(s2);
        }
    };

    // Getters
    public TreeMap<String, ArrayList<Point>> getFoundWords() {
        return foundWords;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }
}
