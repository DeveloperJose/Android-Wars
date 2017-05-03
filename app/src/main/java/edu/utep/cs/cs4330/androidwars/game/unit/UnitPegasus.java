package edu.utep.cs.cs4330.androidwars.game.unit;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class UnitPegasus extends Unit {
    public UnitPegasus(Vector2 mapPosition) {
        super("unit_pegasus", -1, mapPosition);
    }

    @Override
    public boolean canTraverseTerrain(Terrain terrain) {
        if (terrain == null)
            return false;

        boolean canTraverse = true;
        // Can traverse everything
        return canTraverse;
    }

    @Override
    public List<Vector2> getMovementShape() {
        return MovementShape.createDiamond(mapPosition, 4);
    }

}

