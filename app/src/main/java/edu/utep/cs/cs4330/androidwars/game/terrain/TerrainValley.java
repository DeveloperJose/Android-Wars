/**
 * Author: Jose Perez <josegperez@mail.com>
 */
package edu.utep.cs.cs4330.androidwars.game.terrain;

import android.graphics.Color;

import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class TerrainValley extends Terrain {
    public TerrainValley(Vector2 mapPosition) {
        super("terrain_valley", Color.BLACK, mapPosition);
    }
}
