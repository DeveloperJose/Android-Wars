/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.game.unit;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.Sprite;
import edu.utep.cs.cs4330.androidwars.game.map.Map;
import edu.utep.cs.cs4330.androidwars.game.map.Place;
import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

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
        if (!canMove)
            return 100;

        return super.getAlpha();
    }

    public boolean canTraverse(Map map, int x, int y) {
        return canTraverse(map, new Vector2(x, y));
    }

    public boolean canTraverse(Map map, Vector2 pos) {
        // Cannot move outside of our movement range
        if (!getMovementShape().contains(pos))
            return false;

        return canTraverse(map.placeAt(pos));
    }

    public boolean canTraverse(Place place) {
        // Cannot move across certain terrains
        if (!canTraverseTerrain(place.terrain))
            return false;

        // TODO: Don't allow movement across other units
        return true;
    }
}
