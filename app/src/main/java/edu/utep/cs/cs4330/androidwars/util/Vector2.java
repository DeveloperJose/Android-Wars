package edu.utep.cs.cs4330.androidwars.util;

import java.io.Serializable;

public class Vector2 implements Serializable {
    public int X, Y;

    public static final Vector2 Zero = new Vector2(0, 0);

    public Vector2(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}
