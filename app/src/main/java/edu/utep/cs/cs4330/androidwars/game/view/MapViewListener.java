package edu.utep.cs.cs4330.androidwars.game.view;

import android.graphics.Canvas;
import android.graphics.RectF;

import edu.utep.cs.cs4330.androidwars.game.map.Place;
import edu.utep.cs.cs4330.androidwars.game.unit.Unit;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public interface MapViewListener {
    //void onUnitMove(Unit selectedUnit, Vector2 oldPosition, Vector2 newPosition);
    //void onUnitHighlight(Unit selectedUnit, Canvas canvas, RectF rect);

    void onSelectPlace(Place place);
    void onDrawPlace(Place place, Canvas canvas, RectF rect);
}
