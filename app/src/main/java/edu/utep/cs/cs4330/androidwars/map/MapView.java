/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utep.cs.cs4330.androidwars.TextPosition;
import edu.utep.cs.cs4330.androidwars.activity.ResourceManager;
import edu.utep.cs.cs4330.androidwars.map.unit.Unit;

public final class MapView extends View {
    private static final String TAG = "AndroidWars.BoardView";
    private final int colorMapBackground = Color.argb(255, 102, 163, 255);
    private final int colorMapGrid = Color.argb(50, 255, 255, 255);
    private final int colorMapHighlight = Color.argb(125, 255, 255, 125);

    private final Paint paintMapBackground = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paintMapBackground.setColor(colorMapBackground);
    }

    private final Paint paintMapGrid = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paintMapGrid.setColor(colorMapGrid);
        paintMapGrid.setStrokeWidth(2);
    }

    private final Paint paintMapHighlight = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paintMapHighlight.setColor(colorMapHighlight);
    }

    private static Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paintText.setTextSize(50f);
        paintText.setColor(Color.WHITE);
    }


    private java.util.Map<String, TextPosition> textMap;
    private List<MapViewListener> listenerList;
    private Map map;
    private Unit selectedUnit;

    public void registerListener(MapViewListener listener) {
        listenerList.add(listener);
    }

    public void notifyUnitMove(Unit selectedUnit, Vector2 oldPosition, Vector2 newPosition) {
        for (MapViewListener listener : listenerList)
            listener.onUnitMove(selectedUnit, oldPosition, newPosition);
    }

    public void notifyUnitHighlight(Unit selectedUnit, Canvas canvas, RectF rect) {
        for (MapViewListener listener : listenerList)
            listener.onUnitHighlight(selectedUnit, canvas, rect);
    }

    public void setMap(Map map) {
        this.map = map;
        invalidate();
    }

    public Map getMap(){
        return map;
    }

    public void drawText(final String text, TextPosition position, long durationMs) {
        textMap.put(text, position);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textMap.remove(text);
            }
        }, durationMs);

        invalidate();
    }

    private RectF rectFromPosition(TextPosition position) {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;

        if (position == TextPosition.Center) {
            left = right = getMeasuredWidth() / 2;
            top = bottom = getMeasuredHeight() / 2;
        }

        return new RectF(left, top, right, bottom);
    }


    public void onBoardViewTouch(int x, int y) {
        if (map == null)
            return;

        Vector2 newPosition = new Vector2(x, y);
        Place place = map.placeAt(newPosition);

        // Check if we selected a unit to display path highlight
        if (place.unit != null) {
            selectedUnit = place.unit;
        }
        // We didn't select a unit
        // Check if there was a past unit selected
        else if (selectedUnit != null) {
            notifyUnitMove(selectedUnit, selectedUnit.mapPosition, newPosition);
            // Discard selection highlight
            selectedUnit = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int xy = locatePlace(event.getX(), event.getY());
                if (xy >= 0) {
                    int x = xy / 100;
                    int y = xy % 100;
                    onBoardViewTouch(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawPlaces(canvas);
        drawGrid(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        for (java.util.Map.Entry<String, TextPosition> entry : textMap.entrySet()) {
            String text = entry.getKey();
            TextPosition textPosition = entry.getValue();

            RectF rect = rectFromPosition(textPosition);
            float offset = paintText.measureText(text) / 2;
            Log.d("D", "Drawing");
            canvas.drawText(text, rect.left - offset, rect.top, paintText);
        }
    }
    private void drawPlaces(Canvas canvas) {
        for (int x = 0; x < getVerticalLines(); x++) {
            for (int y = 0; y < getHorizontalLines(); y++) {
                float left = x * getPlaceHeight();
                float top = y * getPlaceWidth();
                float right = left + getPlaceHeight();
                float bottom = top + getPlaceWidth();
                RectF rect = new RectF(left, top, right, bottom);

                // Designer (random colors)
                if (map == null)
                    canvas.drawRect(rect, ResourceManager.getRandomPaint());
                    // Regular map drawing
                else
                    map.placeAt(x, y).draw(canvas, rect);


                // Selection highlighting
                if (selectedUnit != null && selectedUnit.canTraverse(map, x, y))
                    notifyUnitHighlight(selectedUnit, canvas, rect);

            }
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paintMapBackground);
    }

    private void drawGrid(Canvas canvas) {
        final int horizontalLines = getHorizontalLines();
        final float placeWidth = getPlaceWidth();
        for (int i = 1; i < horizontalLines; i++)
            canvas.drawLine(0, i * placeWidth, getMeasuredWidth(), i * placeWidth, paintMapGrid);

        final int verticalLines = getVerticalLines();
        final float placeHeight = getPlaceHeight();
        for (int i = 1; i < verticalLines; i++)
            canvas.drawLine(i * placeHeight, 0, i * placeHeight, getMeasuredHeight(), paintMapGrid);

    }

    protected float getPlaceWidth() {
        return getMeasuredHeight() / getHorizontalLines();
    }

    private float getPlaceHeight() {
        return getMeasuredWidth() / getVerticalLines();
    }

    private int getHorizontalLines() {
        return (map == null) ? 7 : map.height;
    }

    private int getVerticalLines() {
        return (map == null) ? 6 : map.width;
    }

    /**
     * Given screen coordinates, locate the corresponding place in the map
     * and return its coordinates; return -1 if the screen coordinates
     * don't correspond to any place in the map.
     * The returned coordinates are encoded as <code>X*100 + Y</code>.
     */
    private int locatePlace(float x, float y) {
        if (x <= getMeasuredWidth() && y <= getMeasuredHeight()) {
            int ix = (int) (x / getPlaceHeight());
            int iy = (int) (y / getPlaceWidth());
            return ix * 100 + iy;
        }
        return -1;
    }

    private void onConstruct() {
        listenerList = new ArrayList<>();
        textMap = new HashMap<>();
    }

    public MapView(Context context) {
        super(context);
        onConstruct();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onConstruct();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onConstruct();
    }
}