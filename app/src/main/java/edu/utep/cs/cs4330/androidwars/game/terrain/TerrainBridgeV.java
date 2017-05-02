/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.game.terrain;

import android.graphics.Color;

import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class TerrainBridgeV extends Terrain {
    public TerrainBridgeV(Vector2 mapPosition) {
        super("terrain_bridgev", Color.GRAY, mapPosition);
    }
}
