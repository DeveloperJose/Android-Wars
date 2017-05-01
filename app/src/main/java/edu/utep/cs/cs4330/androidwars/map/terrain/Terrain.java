package edu.utep.cs.cs4330.androidwars.map.terrain;

import edu.utep.cs.cs4330.androidwars.map.Sprite;

public abstract class Terrain extends Sprite {
    public Terrain(String filename, int colorDebug) {
        super(filename, colorDebug);
    }

    public static Terrain fromName(String name){
        if(name.equalsIgnoreCase("P"))
            return new TerrainPlain();
        else if(name.equalsIgnoreCase("F"))
            return new TerrainForest();

        return null;
    }
}
