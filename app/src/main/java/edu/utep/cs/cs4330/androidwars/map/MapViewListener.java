package edu.utep.cs.cs4330.androidwars.map;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import edu.utep.cs.cs4330.androidwars.map.unit.Unit;

public interface MapViewListener {
    void onUnitMove(Unit selectedUnit, Vector2 oldPosition, Vector2 newPosition);
    void onUnitHighlight(Unit selectedUnit, Canvas canvas, RectF rect);
}
