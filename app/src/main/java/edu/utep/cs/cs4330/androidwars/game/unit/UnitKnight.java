package edu.utep.cs.cs4330.androidwars.game.unit;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainForest;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainMountain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainRiver;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainValley;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainWall;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class UnitKnight extends Unit {
    public UnitKnight(Vector2 mapPosition) {
        super("unit_knight", -1, mapPosition);
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

        // No walking through walls
        canTraverse &= !(terrain instanceof TerrainWall);

        // Cannot traverse mountains
        canTraverse &= !(terrain instanceof TerrainMountain);

        //Cannot traverse forest
        canTraverse &= !(terrain instanceof TerrainForest);

        return canTraverse;
    }

    @Override
    public List<Vector2> getMovementShape() {
        return MovementShape.createDiamond(mapPosition, 2);
    }
}

