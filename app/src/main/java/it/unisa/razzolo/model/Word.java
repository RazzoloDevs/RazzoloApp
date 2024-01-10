package it.unisa.razzolo.model;

import java.util.ArrayList;

public record Word(String text, int score, ArrayList<Point> coordinates) {
}
