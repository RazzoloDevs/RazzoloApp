package it.unisa.razzolo.uninformed;

import it.unisa.razzolo.Util;
import it.unisa.razzolo.model.Direction;
import it.unisa.razzolo.model.HashDictionary;
import it.unisa.razzolo.model.Point;

import java.util.*;

public class IterativeDeepening {
    private static char[][] matrix;

    private static List<Point> iterativeDeepening(String word, int i, int j) {
        if (word.charAt(0) != matrix[i][j])
            return null;

        List<Point> path = new ArrayList<>();
        path.add(new Point(i, j, matrix[i][j]));

        return deepen(word, path, 1) ? path : null;
    }

    //recursive algorithm
    private static boolean deepen(String word, List<Point> path, int index) {

        //base case
        if (index == word.length()) {
            return true;
        }

        Point lastPoint = path.get(path.size() - 1);
        int x = lastPoint.getI();
        int y = lastPoint.getJ();

        for (Direction direction : Direction.DIRECTIONS) {
            int newX = x + direction.x();
            int newY = y + direction.y();

            if (Util.isValid(newX, newY, word.charAt(index), path, matrix)) {
                path.add(new Point(newX, newY, matrix[newX][newY]));
                if (deepen(word, path, index + 1)) {
                    return true;
                }
                path.remove(path.size() - 1);
            }
        }

        return false;
    }

    public static HashMap<String, ArrayList<Point>> run(char[][] m){
        matrix = m;
        final var foundWords = new HashMap<String, ArrayList<Point>>();
        for(final String word : HashDictionary.getInstance().getSet()) {
            boolean wordIsFound = false;
            for (int i = 0; i < matrix.length && !wordIsFound; i++) {
                for (int j = 0; j < matrix[i].length && !wordIsFound; j++) {
                    final var result = iterativeDeepening(word, i, j);
                    if (result != null) {
                        foundWords.put(word, new ArrayList<>(result));
                        wordIsFound = true;
                    }
                }
            }
        }
        return foundWords;
    }
}
