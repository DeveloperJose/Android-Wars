package edu.utep.cs.cs4330.androidwars.game.unit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainForest;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainMountain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainRiverH;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainRiverV;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainValley;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainWall;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class KnightUnit extends Unit {
    public KnightUnit(Vector2 mapPosition) {
        super("unit_knight", -1, mapPosition);
    }

    @Override
    public boolean canTraverseTerrain(Terrain terrain) {
        if (terrain == null)
            return false;

        boolean canTraverse = true;
        // Cannot traverse valley
        canTraverse &= !(terrain instanceof TerrainValley);

        // Cannot traverse water
        canTraverse &= !(terrain instanceof TerrainRiverH);
        canTraverse &= !(terrain instanceof TerrainRiverV);

        // No walking through walls
        canTraverse &= !(terrain instanceof TerrainWall);

        // Cannot traverse mountains
        canTraverse &= !(terrain instanceof TerrainMountain);

        //Cannot traverse forest
        canTraverse &= !(terrain instanceof TerrainForest);

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
        String name = "Knight";
        float offset = p.measureText(name) / 2;
        canvas.drawText("Knight", rect.centerX() - offset, rect.centerY(), p);
    }
}

