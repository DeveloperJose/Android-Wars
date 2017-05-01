/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map.terrain;

import android.graphics.Color;

import edu.utep.cs.cs4330.androidwars.map.Vector2;

public class TerrainMountain extends Terrain {
    public TerrainMountain(Vector2 mapPosition){
        super("terrain_mountain", Color.MAGENTA, mapPosition);
    }
}
