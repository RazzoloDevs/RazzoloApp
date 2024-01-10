package it.unisa.razzolo.uninformed;

import it.unisa.razzolo.Util;
import it.unisa.razzolo.model.Direction;
import it.unisa.razzolo.model.HashDictionary;
import it.unisa.razzolo.model.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Dfs {
    private static char[][] matrix;
    private static final HashSet<String> dictionary = HashDictionary.getInstance().getSet();

    public static HashMap<String, ArrayList<Point>> dfs(int i, int j){
        final var l = new HashMap<String, ArrayList<Point>>();

        final var stack = new Stack<ArrayList<Point>>();
        final var tmp = new ArrayList<Point>();
        tmp.add(new Point(i, j, matrix[i][j]));
        stack.add(tmp);

        while(!stack.isEmpty()){
            final ArrayList<Point> pointList = stack.pop();
            final Point point = pointList.get(pointList.size()-1);
            final String s = Util.getString(pointList);
            if(dictionary.contains(s))
                l.put(s, pointList);

            int x = point.getI();
            int y = point.getJ();

            // check adjacent cells
            for(final var direction : Direction.DIRECTIONS){
                int deltaX = x + direction.x();
                int deltaY = y + direction.y();
                if(Util.checkCells(deltaX, deltaY, pointList)) {
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