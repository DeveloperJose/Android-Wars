/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.game.map;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.io.Serializable;

import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.game.unit.Unit;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public final class Place implements Serializable {
    //public Vector2 position;
    public Unit unit;
    public Terrain terrain;

    public void draw(Canvas canvas, RectF rect) {
        if (terrain != null)
            terrain.draw(canvas, rect);
        else
            canvas.drawRect(rect, ResourceManager.getRandomPaint());

        if (unit != null)
            unit.draw(canvas, rect);
    }
}
