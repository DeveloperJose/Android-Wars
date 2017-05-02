package edu.utep.cs.cs4330.androidwars;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class TextMessage {
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
        RectF rect = getRect(view);

        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);

        canvas.drawRect(rect, backgroundPaint);
        canvas.drawText(message, rect.left, rect.top, getPaint());
    }

    private Paint getPaint(){
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
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

        x -= getTextWidth();

        float width = getTextWidth();
        float height = getTextHeight();

        return new RectF(x, y, width, height);
    }

    private float getTextWidth() {
        return getPaint().measureText(message) / 2;
    }

    private float getTextHeight(){
        return getPaint().getTextSize();
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
