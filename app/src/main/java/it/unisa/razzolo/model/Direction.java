package it.unisa.razzolo.model;

public record Direction(int x, int y)
{
    public static final Direction[] DIRECTIONS =
    {
        new Direction(0, -1),  // up
        new Direction(1, -1),  // up-dx
        new Direction(1, 0),   // dx
        new Direction(1, 1),   // dw-dx
        new Direction(0, 1),   // dw
        new Direction(-1, 1),  // dw-sx
        new Direction(-1, 0),  // sx
        new Direction(-1, -1), // up-sx
    };
}