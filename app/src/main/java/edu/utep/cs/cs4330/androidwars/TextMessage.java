package edu.utep.cs.cs4330.androidwars;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.View;

public class TextMessage {
    /**
     * Margin added to all messages with backgrounds
     */
    private static final float BG_MARGIN = 15;

    public String message;
    public TextPosition position;
    public float fontSize;
    public int fontColor;
    public int backgroundColor;
    public int durationMs;

    public TextMessage(){
        fontSize = 100f;
        fontColor = Color.WHITE;
        backgroundColor = Color.alpha(0);
        durationMs = -1;
    }

    public TextMessage(String message, TextPosition position){
        this();
        this.message = message;
        this.position = position;
    }

    public void draw(View view, Canvas canvas){
        // Background drawing
        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        canvas.drawRect(getBackgroundRect(view), backgroundPaint);

        // Text drawing
        RectF rect = getRect(view);
        canvas.drawText(message, rect.left, rect.top, getPaint());
    }

    private Paint getPaint(){
        Paint p = new TextPaint(Paint.ANTI_ALIAS_FLAG);;
        p.setTextSize(fontSize);
        p.setColor(fontColor);
        return p;
    }

    private RectF getRect(View view){
        float x, y;

        if (position == TextPosition.Center) {
            x = view.getMeasuredWidth() / 2;
            y = view.getMeasuredHeight() / 2;
        }
        else{
            x = view.getMeasuredWidth() / 2;
            y = view.getTop() + getTextHeight();
        }
        // Adjust alignment based on text width
        // Otherwise it isn't properly centered
        x -= getTextWidth() / 2;

        return new RectF(x, y , 0, 0);
    }

    public Rect getBackgroundRect(View view){
        // Get the rectangle that can fit the text
        Rect rectBackground = new Rect();
        getPaint().getTextBounds(message, 0, message.length(), rectBackground);
        rectBackground.left -= BG_MARGIN;
        rectBackground.right += BG_MARGIN;
        rectBackground.top -= BG_MARGIN;
        rectBackground.bottom += BG_MARGIN;

        // Place it where the text actually is
        RectF rectText = getRect(view);
        rectBackground.offset((int)rectText.left, (int)rectText.top);

        return rectBackground;
    }

    private float getTextWidth() {
        return getPaint().measureText(message);
    }

    private float getTextHeight(){
        Paint.FontMetrics fm = getPaint().getFontMetrics();
        return fm.descent - fm.ascent;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        TextMessage other = (TextMessage)obj;
        if(!message.equals(other.message)) return false;

        return true;
    }
}
