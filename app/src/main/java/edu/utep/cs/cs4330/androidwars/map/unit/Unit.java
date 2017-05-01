/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map.unit;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.map.Map;
import edu.utep.cs.cs4330.androidwars.map.Place;
import edu.utep.cs.cs4330.androidwars.map.Sprite;
import edu.utep.cs.cs4330.androidwars.map.Vector2;
import edu.utep.cs.cs4330.androidwars.map.terrain.Terrain;

public abstract class Unit extends Sprite {
    public int currentTeam;
    public boolean canMove;

    protected abstract List<Vector2> getMovementShape();
    protected abstract boolean canTraverseTerrain(Terrain terrain);

    public Unit(String filename, int colorDebug, Vector2 mapPosition) {
        super(filename, colorDebug, mapPosition);
        currentTeam = -1;
        canMove = false;
    }

    @Override
    public int getAlpha() {
        if(!canMove)
            return 10;

        return super.getAlpha();
    }

    public boolean canTraverse(Map map, int x, int y){
        return canTraverse(map, new Vector2(x, y));
    }

    public boolean canTraverse(Map map, Vector2 pos){
        // Cannot move when it's not your turn
        if(!canMove)
            return false;

        // Cannot move outside of our movement range
        if(!getMovementShape().contains(pos))
            return false;

        return canTraverse(map.placeAt(pos));
    }

    public boolean canTraverse(Place place){
        // Cannot move across certain terrains
        if(!canTraverseTerrain(place.terrain))
            return false;

        // TODO: Don't allow movement across other units
        return true;
    }
}
