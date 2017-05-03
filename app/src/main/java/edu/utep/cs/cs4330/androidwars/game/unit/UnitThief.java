package edu.utep.cs.cs4330.androidwars.game.unit;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainRiver;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainValley;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class UnitThief extends Unit {
    public UnitThief(Vector2 mapPosition) {
        super("unit_thief", -1, mapPosition);
    }

    @Override
    public boolean canTraverseTerrain(Terrain terrain) {
        if (terrain == null)
            return false;

        boolean canTraverse = true;
        // Cannot traverse valley
        canTraverse &= !(terrain instanceof TerrainValley);

        // Cannot traverse water
        canTraverse &= !(terrain instanceof TerrainRiver);

        return canTraverse;
    }

    @Override
    public List<Vector2> getMovementShape() {
        return MovementShape.createDiamond(mapPosition, 2);
    }
}
