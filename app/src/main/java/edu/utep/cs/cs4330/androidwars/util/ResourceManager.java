package edu.utep.cs.cs4330.androidwars.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;
import java.util.Scanner;

public abstract class ResourceManager {
    private static String TAG = "AndroidWars.RsrcMngr";
    public static Resources resources;
    public static String packageName;

    private static Random random;
    public static Random getRandom(){
        if(random == null)
            random = new Random();
        return random;
    }

    public static int getRandomColor(){
        int alpha = getRandom().nextInt(255);
        int r = getRandom().nextInt(255);
        int g = getRandom().nextInt(255);
        int b = getRandom().nextInt(255);
        return Color.argb(alpha, r, g, b);
    }

    public static Paint getRandomPaint(){
        Paint p = new Paint();
        p.setColor(getRandomColor());
        return p;
    }

    private static int getResourceID(String type, String filename){
        return resources.getIdentifier(filename, type, packageName);
    }

    public static Bitmap getBitmap(String filename){
        int resID = getResourceID("raw", filename);
        return BitmapFactory.decodeResource(resources, resID);
    }

    public static Scanner getScanner(String filename){
        int resID = getResourceID("raw", filename);
        return new Scanner(resources.openRawResource(resID));
    }

    private ResourceManager() { }
}
