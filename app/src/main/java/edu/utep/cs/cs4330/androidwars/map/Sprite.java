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
    private static final Paint paintUnit = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap bitmapSprite;
    private int colorDebug;

    public Sprite(String filename, int colorDebug){
        bitmapSprite = ResourceManager.getBitmap(filename);
        this.colorDebug = colorDebug;
    }

    public void draw(Canvas canvas, RectF rect){
        if(bitmapSprite == null)
            drawDebug(canvas, rect);
        else
            canvas.drawBitmap(bitmapSprite, null, rect, paintUnit);
    }

    public void drawDebug(Canvas canvas, RectF rect){
        Paint p = paintUnit;
        p.setColor(colorDebug);
        canvas.drawRect(rect, p);
    }
}
