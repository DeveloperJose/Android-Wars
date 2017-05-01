/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

    private Map map;
    private Unit unitSelected;

    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMap(Map map) {
        this.map = map;
        invalidate();
    }

    public void onBoardViewTouch(int x, int y) {
        if (map == null)
            return;

        Vector2 newPosition = new Vector2(x, y);
        Place p = map.placeAt(newPosition);

        // Check if we selected a unit to display path highlight
        if (p.unit != null) {
            unitSelected = p.unit;
        }
        // We didn't select a unit
        // Check if there was a past unit selected
        else if (unitSelected != null) {
            // Check if we clicked inside the highlight
            //if (unitSelected.canMoveTo(map, newPosition)) {
                // The player wants to move
              //  Vector2 oldPosition = unitSelected.mapPosition;
                //unitSelected.mapPosition = newPosition;
                //map.placeAt(newPosition).unit = unitSelected;
                //map.placeAt(oldPosition).unit = null;
            //}
            // Discard selection highlight
            unitSelected = null;
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
                if(map == null)
                    canvas.drawRect(rect, ResourceManager.getRandomPaint());
                // Regular map drawing
                else {
                    map.placeAt(x, y).draw(canvas, rect);
                }

                // Selection highlighting
                if(unitSelected != null && unitSelected.canTraverse(map, new Vector2(x, y))){
                    canvas.drawRect(rect, paintMapHighlight);
                }
            }
        }

    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paintMapBackground);
    }

    private void drawGrid(Canvas canvas) {
        final int horizontalLines = getHorizontalLines();
        final float placeWidth = getPlaceWidth();
        for(int i = 1; i < horizontalLines; i++)
            canvas.drawLine(0, i * placeWidth, getMeasuredWidth(), i * placeWidth, paintMapGrid);

        final int verticalLines = getVerticalLines();
        final float placeHeight = getPlaceHeight();
        for(int i = 1; i < verticalLines; i++)
            canvas.drawLine(i * placeHeight, 0, i * placeHeight, getMeasuredHeight(), paintMapGrid);

    }

    protected float getPlaceWidth(){
        return getMeasuredHeight() / getHorizontalLines();
    }

    private float getPlaceHeight(){
        return getMeasuredWidth() / getVerticalLines();
    }

    private int getHorizontalLines(){
        return (map == null) ? 7 : map.height;
    }

    private int getVerticalLines(){
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
}