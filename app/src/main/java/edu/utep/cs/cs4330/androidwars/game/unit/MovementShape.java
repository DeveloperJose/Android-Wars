package edu.utep.cs.cs4330.androidwars.game.unit;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.androidwars.util.Vector2;

public final class MovementShape {
    private MovementShape() {
    }

    public static List<Vector2> createDiamond(Vector2 origin, int radius) {
        List<Vector2> shape = new ArrayList<>();

        for (int row = origin.X - radius; row <= origin.X + radius; row++) {
            for (int col = origin.Y - radius; col <= origin.Y + radius; col++) {
                // Skip the center
                if (row == origin.X && col == origin.Y)
                    continue;

                // Skip corners who are far away
                if (Math.abs(row - origin.X) + Math.abs(col - origin.Y) > radius)
                    continue;

                shape.add(new Vector2(row, col));
            }
        }
        return shape;
    }

    public static List<Vector2> createSquare(Vector2 origin, int size) {
        List<Vector2> shape = new ArrayList<>();
        for (int row = origin.X - size; row <= origin.X + size; row++) {
            for (int col = origin.Y - size; col <= origin.Y + size; col++) {
                // Skip the center
                if (row == origin.X && col == origin.Y)
                    continue;

                shape.add(new Vector2(row, col));
            }
        }
        return shape;
    }
}
