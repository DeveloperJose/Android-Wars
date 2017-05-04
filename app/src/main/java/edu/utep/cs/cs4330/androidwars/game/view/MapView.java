/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.map.Map;
import edu.utep.cs.cs4330.androidwars.game.map.Place;
import edu.utep.cs.cs4330.androidwars.game.unit.Unit;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public final class MapView extends View {
    private static final String TAG = "AndroidWars.BoardView";
    private final int colorMapBackground = Color.argb(255, 102, 255, 100);
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

    private List<TextMessage> textMessageList;
    private List<MapViewListener> listenerList;
    private Map map;

    public void registerListener(MapViewListener listener) {
        listenerList.add(listener);
    }

    public void notifySelectPlace(Place place) {
        for (MapViewListener listener : listenerList)
            listener.onSelectPlace(place);
    }

    public void notifyHoldPlace(Place place, int duration){
        for (MapViewListener listener : listenerList)
            listener.onHoldPlace(place, duration);
    }

    public void notifyDrawPlace(Place place, Canvas canvas, RectF rect) {
        for (MapViewListener listener : listenerList)
            listener.onDrawPlace(place, canvas, rect);
    }

    public void setMap(Map map) {
        this.map = map;
        invalidate();
    }

    public Map getMap() {
        return map;
    }

    public void showTextMessage(final TextMessage textMessage) {
        textMessageList.add(textMessage);

        if (textMessage.durationMs > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    textMessageList.remove(textMessage);
                    invalidate();
                }
            }, textMessage.durationMs);
        }

        invalidate();
    }

    public void onBoardViewTouch(int x, int y) {
        if (map == null)
            return;

        Place place = map.placeAt(x, y);
        notifySelectPlace(place);
   }

   public void onBoardViewLongTouch(int x, int y){
       if (map == null)
           return;

       Place place = map.placeAt(x, y);
       notifyHoldPlace(place, holdDuration);
   }

    private int holdDuration = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int xy = locatePlace(event.getX(), event.getY());
                if (xy >= 0) {
                    int x = xy / 100;
                    int y = xy % 100;
                    onBoardViewTouch(x, y);
                    onBoardViewLongTouch(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                holdDuration = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                holdDuration++;
                xy = locatePlace(event.getX(), event.getY());
                if (xy >= 0) {
                    int x = xy / 100;
                    int y = xy % 100;
                    onBoardViewLongTouch(x, y);
                    invalidate();
                }
                break;
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
                else {
                    Place place = map.placeAt(x, y);
                    place.draw(canvas, rect);
                    notifyDrawPlace(place, canvas, rect);
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
        for (int i = 1; i < horizontalLines; i++)
            canvas.drawLine(0, i * placeWidth, getMeasuredWidth(), i * placeWidth, paintMapGrid);

        final int verticalLines = getVerticalLines();
        final float placeHeight = getPlaceHeight();
        for (int i = 1; i < verticalLines; i++)
            canvas.drawLine(i * placeHeight, 0, i * placeHeight, getMeasuredHeight(), paintMapGrid);

    }

    private void drawText(Canvas canvas) {
        for (TextMessage textMessage : textMessageList)
            textMessage.draw(this, canvas);
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
        textMessageList = new ArrayList<>();
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