/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map.unit;

import android.graphics.Canvas;
import android.util.Log;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.map.Map;
import edu.utep.cs.cs4330.androidwars.map.Place;
import edu.utep.cs.cs4330.androidwars.map.Sprite;
import edu.utep.cs.cs4330.androidwars.map.Vector2;
import edu.utep.cs.cs4330.androidwars.map.terrain.Terrain;

public abstract class Unit extends Sprite {
    public Unit(String filename, int colorDebug, Vector2 mapPosition) {
        super(filename, colorDebug, mapPosition);
    }

    public abstract List<Vector2> getMovementShape();
    public abstract boolean canTraverseTerrain(Terrain terrain);

    public boolean canTraverse(Map map, int x, int y){
        return canTraverse(map, new Vector2(x, y));
    }

    public boolean canTraverse(Map map, Vector2 pos){
        if(!getMovementShape().contains(pos))
            return false;

        return canTraverse(map.placeAt(pos));
    }

    public boolean canTraverse(Place place){
        if(!canTraverseTerrain(place.terrain))
            return false;

        return true;
    }
}
