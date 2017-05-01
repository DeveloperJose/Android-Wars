/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import edu.utep.cs.cs4330.androidwars.activity.ResourceManager;

public abstract class Sprite {
    private Bitmap bitmapSprite;
    private int colorDebug;

    protected Vector2 mapPosition;

    public Sprite(String filename, int colorDebug, Vector2 mapPosition){
        bitmapSprite = ResourceManager.getBitmap(filename);
        this.colorDebug = colorDebug;
        this.mapPosition = mapPosition;
    }

    public int getAlpha(){
        return 255;
    }

    private Paint getAlphaPaint(){
        Paint p = new Paint();
        p.setAlpha(getAlpha());
        return p;
    }

    public void draw(Canvas canvas, RectF rect){
        if(bitmapSprite == null)
            drawDebug(canvas, rect);
        else
            canvas.drawBitmap(bitmapSprite, null, rect, getAlphaPaint());
    }

    public void drawDebug(Canvas canvas, RectF rect){
        Paint p = getAlphaPaint();
        p.setColor(colorDebug);
        canvas.drawRect(rect, p);
    }
}
