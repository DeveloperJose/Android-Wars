package edu.utep.cs.cs4330.androidwars.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import edu.utep.cs.cs4330.androidwars.TextPosition;
import edu.utep.cs.cs4330.androidwars.activity.ResourceManager;
import edu.utep.cs.cs4330.androidwars.map.unit.Unit;

public class GameManager implements MapViewListener {
    private static final Paint paintHighlightOpponent = new Paint(Paint.ANTI_ALIAS_FLAG);{
        paintHighlightOpponent.setColor(Color.argb(125, 255, 0, 0));
    }

    private static final Paint paintHighlight = new Paint(Paint.ANTI_ALIAS_FLAG);{
        paintHighlight.setColor(Color.argb(125, 100, 100, 100));
    }

    private MapView mapView;
    private Map map;
    private Team teamOne;
    private Team teamTwo;
    private int currentPlayer;

    public GameManager(MapView mapView, Team teamOne, Team teamTwo){
        this.mapView = mapView;
        this.map = mapView.getMap();
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;

        this.mapView.registerListener(this);
        currentPlayer = ResourceManager.getRandomTeam(teamOne, teamTwo);
        updateUnits();
    }

    public Team getCurrentTeam(){
        if(getCurrentPlayer() == teamOne.teamNumber)
            return teamOne;
        else
            return teamTwo;
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public void changeTurns(){
        if(currentPlayer == teamOne.teamNumber)
            currentPlayer = teamTwo.teamNumber;
        else
            currentPlayer = teamOne.teamNumber;

        mapView.drawText("Turn Change!", TextPosition.Center, 1500);
        updateUnits();
    }

    private void updateUnits(){
        getCurrentTeam().resetTurn();
    }

    @Override
    public void onUnitMove(Unit selectedUnit, Vector2 oldPosition, Vector2 newPosition) {
        if(selectedUnit.currentTeam != getCurrentPlayer())
            return;

        Place place = map.placeAt(newPosition);
        // The player wants to move and can traverse the place
        if (selectedUnit.canTraverse(place)) {
            selectedUnit.canMove = false;
            selectedUnit.mapPosition = newPosition;

            map.placeAt(newPosition).unit = selectedUnit;
            map.placeAt(oldPosition).unit = null;

            // Check if the player has moved all their units
            if(getCurrentTeam().getAvailableUnits() == 0)
                changeTurns();
        }
    }

    @Override
    public void onUnitHighlight(Unit selectedUnit, Canvas canvas, RectF rect) {
        Paint paint;
        if(selectedUnit.currentTeam != getCurrentPlayer() || !selectedUnit.canMove)
            paint = paintHighlightOpponent;
        else
            paint = paintHighlight;

        canvas.drawRect(rect, paint);
    }
}
