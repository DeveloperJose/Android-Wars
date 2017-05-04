/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.game.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.Sprite;
import edu.utep.cs.cs4330.androidwars.game.map.Map;
import edu.utep.cs.cs4330.androidwars.game.map.Place;
import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public abstract class Unit extends Sprite {
    public int currentTeam;
    public boolean canMove;
    public boolean canAttack;

    protected abstract List<Vector2> getMovementShape();

    protected abstract boolean canTraverseTerrain(Terrain terrain);

    public Unit(String filename, int colorDebug, Vector2 mapPosition) {
        super(filename, colorDebug, mapPosition);
        currentTeam = -1;
        canMove = false;
    }

    @Override
    public int getAlpha() {
        if (!canAttack)
            return 100;

        return super.getAlpha();
    }


    public boolean canTraverse(Place place) {
        // Cannot move outside of our movement range
        if (!getMovementShape().contains(place.position))
            return false;

        // Cannot move across certain terrains
        if (!canTraverseTerrain(place.terrain))
            return false;

        // Cannot move if there's a unit there
        if(place.unit != null)
            return false;

        return true;
    }

    public boolean canTraverse(Map map, int x, int y) {
        return canTraverse(map, new Vector2(x, y));
    }

    public boolean canTraverse(Map map, Vector2 pos) {
        return canTraverse(map.placeAt(pos));
    }

    @Override
    public void drawDebug(Canvas canvas, RectF rect) {
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);
        p.setStrokeWidth(5f);
        p.setAlpha(getAlpha());
        canvas.drawOval(rect, p);

        p = new Paint();
        p.setColor(Color.RED);
        p.setTextSize(50f);
        p.setAlpha(getAlpha());
        String name = getClass().getSimpleName();
        float offset = p.measureText(name) / 2;
        canvas.drawText(name, rect.centerX() - offset, rect.centerY(), p);
    }
}