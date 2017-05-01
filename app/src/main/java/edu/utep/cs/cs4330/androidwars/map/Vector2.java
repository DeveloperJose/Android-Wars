/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map;

import java.io.Serializable;

public final class Vector2 implements Serializable {
    public int X, Y;

    public static final Vector2 Zero = new Vector2(0, 0);

    public Vector2(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}
