package edu.utep.cs.cs4330.androidwars.game.unit;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainMountain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainRiver;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainWall;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class UnitTest extends Unit {
    public UnitTest(Vector2 mapPosition) {
        super("", -1, mapPosition);
    }

    @Override
    public boolean canTraverseTerrain(Terrain terrain) {
        if (terrain == null)
            return false;

        boolean canTraverse = true;
        // Cannot traverse mountains
        canTraverse &= !(terrain instanceof TerrainMountain);

        // Cannot traverse water
        canTraverse &= !(terrain instanceof TerrainRiver);

        // No walking through walls
        canTraverse &= !(terrain instanceof TerrainWall);

        return canTraverse;
    }

    @Override
    public List<Vector2> getMovementShape() {
        return MovementShape.createDiamond(mapPosition, 2);
    }
}
