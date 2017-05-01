/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.io.Serializable;

import edu.utep.cs.cs4330.androidwars.map.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.map.unit.Unit;

public final class Place implements Serializable {
    public Vector2 position;
    public Unit unit;
    public Terrain terrain;

    public void draw(Canvas canvas, RectF rect){
        if(terrain != null)
            terrain.draw(canvas, rect);
    }
}
