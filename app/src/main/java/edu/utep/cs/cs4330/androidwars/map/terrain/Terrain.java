/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map.terrain;

import android.util.Log;

import edu.utep.cs.cs4330.androidwars.map.Sprite;

public abstract class Terrain extends Sprite {
    private static String TAG = "AndroidWars.Terrain";
    public Terrain(String filename, int colorDebug) {
        super(filename, colorDebug);
    }

    public static Terrain fromName(String name){
        if(name.equalsIgnoreCase("bh"))
            return new TerrainBridgeH();
        else if(name.equalsIgnoreCase("bv"))
            return new TerrainBridgeV();
        else if(name.equalsIgnoreCase("f"))
            return new TerrainForest();
        else if(name.equalsIgnoreCase("m"))
            return new TerrainMountain();
        else if(name.equalsIgnoreCase("p"))
            return new TerrainPlain();
        else if(name.equalsIgnoreCase("rh"))
            return new TerrainRiverH();
        else if(name.equalsIgnoreCase("rv"))
            return new TerrainRiverV();
        else if(name.equalsIgnoreCase("v"))
            return new TerrainValley();
        else if(name.equalsIgnoreCase("w"))
            return new TerrainWall();

        Log.d(TAG, "Attempted to load an invalid terrain: " + name);
        return null;
    }
}
