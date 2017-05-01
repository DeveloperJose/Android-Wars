package edu.utep.cs.cs4330.androidwars.map.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.map.Map;
import edu.utep.cs.cs4330.androidwars.map.Vector2;
import edu.utep.cs.cs4330.androidwars.map.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.map.terrain.TerrainMountain;
import edu.utep.cs.cs4330.androidwars.map.terrain.TerrainRiverH;
import edu.utep.cs.cs4330.androidwars.map.terrain.TerrainRiverV;
import edu.utep.cs.cs4330.androidwars.map.terrain.TerrainWall;

public class TestUnit extends Unit{
    public TestUnit(Vector2 mapPosition){
        super("", -1, mapPosition);
    }

    @Override
    public boolean canTraverseTerrain(Terrain terrain) {
        if(terrain == null)
            return false;

        boolean canTraverse = true;
        // Cannot traverse mountains
        canTraverse &= !(terrain instanceof TerrainMountain);

        // Cannot traverse water
        canTraverse &= !(terrain instanceof TerrainRiverH);
        canTraverse &= !(terrain instanceof TerrainRiverV);

        // No walking through walls
        canTraverse &= !(terrain instanceof TerrainWall);

        return canTraverse;
    }

    @Override
    public List<Vector2> getMovementShape() {
        return MovementShape.createDiamond(mapPosition, 2);
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
        String name = "Diego";
        float offset = p.measureText(name) / 2;
        canvas.drawText("Diego", rect.centerX() - offset, rect.centerY(), p);
    }
}
