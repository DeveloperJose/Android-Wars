/**
 * Author: Jose Perez <josegperez@mail.com>
 */
package edu.utep.cs.cs4330.androidwars.map.terrain;

import android.graphics.Color;

import edu.utep.cs.cs4330.androidwars.map.Vector2;

public class TerrainWall extends Terrain {
    public TerrainWall(Vector2 mapPosition){
        super("terrain_wall", Color.RED, mapPosition);
    }
}
