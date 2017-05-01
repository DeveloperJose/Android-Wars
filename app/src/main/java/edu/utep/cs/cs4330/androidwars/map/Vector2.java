/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 *
 * Useful Operators:
 * https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/math/Vector2.java
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

    public Vector2(Vector2 other){
        this.X = other.X;
        this.Y = other.Y;
    }

    public double length(){
        return Math.sqrt(X * X + Y * Y);
    }

    public Vector2 normalize(){
        final double length = length();
        Vector2 copy = new Vector2(this);
        if (length != 0) {
            copy.X /= length;
            copy.Y /= length;
        }
        return copy;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(X);
        result = prime * result + Float.floatToIntBits(Y);
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2)obj;
        if (Float.floatToIntBits(X) != Float.floatToIntBits(other.X)) return false;
        if (Float.floatToIntBits(Y) != Float.floatToIntBits(other.Y)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "{" + X + "," + Y + "}";
    }
}
