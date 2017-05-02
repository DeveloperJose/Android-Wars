/**
 * Author: Jose Perez <josegperez@mail.com>
 */
package edu.utep.cs.cs4330.androidwars.game.terrain;

import android.graphics.Color;

import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class TerrainWall extends Terrain {
    public TerrainWall(Vector2 mapPosition) {
        super("terrain_wall", Color.RED, mapPosition);
    }
}
