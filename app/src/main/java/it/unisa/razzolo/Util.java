package it.unisa.razzolo;

import it.unisa.razzolo.model.Point;

import java.util.ArrayList;
import java.util.List;

public class Util {
    // controlla che una coordinata sia valida
    public static boolean checkCells(int i, int j){
        return (i >= 0 && i < 4) && (j >= 0 && j < 4);
    }

    // controlla che una coordinata sia valida e che non sia già stata visitata
    public static boolean isValid(int x, int y, char targetChar, List<Point> path, char[][] matrix){
        if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length)
            return false;

        if (matrix[x][y] != targetChar)
            return false;

        for (Point point : path)
            if (point.getI() == x && point.getJ() == y)
                return false;

        return true;
    }

    // controlla che una coordinata sia valida e che non sia già stata visitata
    public static boolean checkCells(int i, int j, ArrayList<Point> l){
        return (checkCells(i, j) && _checkVisited(i, j, l));
    }

    // controlla che una coordinata non sia già presente nella lista di punti
    private static boolean _checkVisited(int i, int j, ArrayList<Point> l){
        for(int k = 0; k < l.size() - 1; k++)
            if(l.get(k).getI() == i && l.get(k).getJ() == j)
                return false;
        return true;
    }

    // restituisce una stringa a partire da una lista di punti
    public static String getString(final ArrayList<Point> l){
        final var sb = new StringBuilder();
        for(final Point p : l)
            sb.append(p.getValue());
        return sb.toString();
    }
}