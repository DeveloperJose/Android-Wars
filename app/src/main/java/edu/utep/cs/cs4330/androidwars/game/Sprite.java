/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.io.Serializable;

import edu.utep.cs.cs4330.androidwars.util.SerialBitmap;
import edu.utep.cs.cs4330.androidwars.util.Vector2;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;

public abstract class Sprite implements Serializable {
    private SerialBitmap serialBitmapSprite;
    private int colorDebug;

    protected Vector2 mapPosition;

    public Vector2 getMapPosition(){
        return mapPosition;
    }

    public Sprite(String filename, int colorDebug, Vector2 mapPosition){
        serialBitmapSprite = ResourceManager.getBitmap(filename);
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
        if(serialBitmapSprite.bitmap == null)
            drawDebug(canvas, rect);
        else
            canvas.drawBitmap(serialBitmapSprite.bitmap, null, rect, getAlphaPaint());
    }

    public void drawDebug(Canvas canvas, RectF rect){
        Paint p = getAlphaPaint();
        p.setColor(colorDebug);
        canvas.drawRect(rect, p);
    }
}
