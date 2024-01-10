package it.unisa.razzolo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import it.unisa.razzolo.informed.BfsTrie;
import it.unisa.razzolo.informed.DfsTrie;
import it.unisa.razzolo.model.Point;
import it.unisa.razzolo.uninformed.Bfs;
import it.unisa.razzolo.uninformed.Dfs;
import it.unisa.razzolo.uninformed.IterativeDeepening;

public class Background implements Runnable{
    private final char[][] matrix;
    private final String algorithm;
    private TreeMap<String, ArrayList<Point>> foundWords;
    private double elapsedTime;

    public Background(char[][] matrix, String algorithm){
        this.matrix = matrix;
        this.algorithm = algorithm;
    }

    @Override
    public void run() {
        this.foundWords = new TreeMap<>(compareStringByLength);
        long start = System.nanoTime();
        switch (algorithm){
            case "In ampiezza" -> foundWords.putAll(Bfs.run(matrix));
            case "In profondità" -> foundWords.putAll(Dfs.run(matrix));
            case "Approfondimento iterativo" -> foundWords.putAll(IterativeDeepening.run(matrix));
            case "In ampiezza con Trie" -> foundWords.putAll(BfsTrie.run(matrix));
            case "In profondità con Trie" -> foundWords.putAll(DfsTrie.run(matrix));
        }
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
