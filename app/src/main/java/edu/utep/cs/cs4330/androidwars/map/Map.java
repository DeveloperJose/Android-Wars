package edu.utep.cs.cs4330.androidwars.map;

import java.util.List;
import java.util.Scanner;

import edu.utep.cs.cs4330.androidwars.util.ResourceManager;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class Map {
    private static final String TAG = "AndroidWars.Map";
    private List<MapListener> listeners;
    private Place[][] places;
    public final int width;
    public final int height;

    public static Map fromFilename(String filename){
        Scanner input = ResourceManager.getScanner(filename);

        int width = input.nextInt();
        int height = input.nextInt();

        Map mapTemp = new Map(width, height);

        return mapTemp;
    }

    public Map(int width, int height){
        this.width = width;
        this.height = height;

        places = new Place[width][height];
        for(int i = 0; i < places.length; i++)
            for(int j = 0; j < places[i].length; j++)
                places[i][j] = new Place();
    }

    private void notifyOnBoardUpdate(){
        for(MapListener listener : listeners)
            listener.onMapUpdate();
    }

    public void registerListener(MapListener listener){
        listeners.add(listener);
    }

    public synchronized Place placeAt(Vector2 pos) {
        return placeAt(pos.X, pos.Y);
    }

    public synchronized Place placeAt(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return null;

        return places[x][y];
    }
}
