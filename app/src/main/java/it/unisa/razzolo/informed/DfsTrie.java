package it.unisa.razzolo.informed;

import it.unisa.razzolo.Util;
import it.unisa.razzolo.model.Direction;
import it.unisa.razzolo.model.Point;
import it.unisa.razzolo.model.Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class DfsTrie {
    private static char[][] matrix;
    private static final Trie trie = Trie.getInstance();

    private static HashMap<String, ArrayList<Point>> dfs(int i, int j){
        final var l = new HashMap<String, ArrayList<Point>>();

        final var stack = new Stack<ArrayList<Point>>();
        final var tmp = new ArrayList<Point>();
        tmp.add(new Point(i, j, matrix[i][j]));
        stack.add(tmp);

        while(!stack.isEmpty()){
            final ArrayList<Point> pointList = stack.pop();
            final Point point = pointList.get(pointList.size()-1);
            final String s = Util.getString(pointList);
            if(trie.search(s))
                l.put(s, pointList);

            int x = point.getI();
            int y = point.getJ();

            final Set<Character> characterSet = trie.searchBySubstring(s);
            // check adjacent cells
            for(final var direction : Direction.DIRECTIONS){
                int deltaX = x + direction.x();
                int deltaY = y + direction.y();
                if(Util.checkCells(deltaX, deltaY, pointList) && characterSet.contains(matrix[deltaX][deltaY])) {
                    final var tmpList = new ArrayList<>(pointList);
                    tmpList.add(new Point(deltaX, deltaY, matrix[deltaX][deltaY]));
                    stack.add(tmpList);
                }
            }
        }
        return l;
    }

    public static HashMap<String, ArrayList<Point>> run(char[][] m){
        matrix = m;
        final var l = new HashMap<String, ArrayList<Point>>();
        for(int i = 0; i < matrix.length; i++)
            for(int j = 0; j < matrix[i].length; j++)
                l.putAll(dfs(i, j));
        return l;
    }
}
