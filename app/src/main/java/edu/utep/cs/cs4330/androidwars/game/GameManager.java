package edu.utep.cs.cs4330.androidwars.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import edu.utep.cs.cs4330.androidwars.game.map.Place;
import edu.utep.cs.cs4330.androidwars.game.unit.Unit;
import edu.utep.cs.cs4330.androidwars.game.view.MapView;
import edu.utep.cs.cs4330.androidwars.game.view.MapViewListener;
import edu.utep.cs.cs4330.androidwars.game.view.TextMessage;
import edu.utep.cs.cs4330.androidwars.game.view.TextPosition;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public final class GameManager implements MapViewListener {
    private static final Paint paintHighlightOpponent = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paintHighlightOpponent.setColor(Color.argb(125, 255, 0, 0));
    }

    private static final Paint paintHighlight = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paintHighlight.setColor(Color.argb(125, 100, 100, 100));
    }

    private static final TextMessage textMessageTurnChange = new TextMessage();

    {
        textMessageTurnChange.message = "Turn Change!";
        textMessageTurnChange.position = TextPosition.Center;
        textMessageTurnChange.durationMs = 1500;
    }

    private MapView mapView;
    private Team teamOne;
    private Team teamTwo;
    private int currentPlayer;

    public GameManager(MapView mapView, Team teamOne, Team teamTwo) {
        this.mapView = mapView;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;

        this.mapView.registerListener(this);
        currentPlayer = ResourceManager.getRandomTeam(teamOne, teamTwo);
        updateUnits();
    }

    public Team getCurrentTeam() {
        if (getCurrentPlayer() == teamOne.teamNumber)
            return teamOne;
        else
            return teamTwo;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void changeTurns() {
        if (currentPlayer == teamOne.teamNumber)
            currentPlayer = teamTwo.teamNumber;
        else
            currentPlayer = teamOne.teamNumber;

        mapView.showTextMessage(textMessageTurnChange);
        updateUnits();
    }

    private void updateUnits() {
        getCurrentTeam().resetTurn();
    }

    private Unit selectedUnit;
    private Unit highlightUnit;

    @Override
    public void onSelectPlace(Place place){
        // We selected a unit from our team before
        if(selectedUnit != null){
            // Check unit movement first
            if(selectedUnit.canMove){
                // Check if they can move to the specified place
                Vector2 oldPosition = selectedUnit.getMapPosition();
                Vector2 newPosition = place.position;
                if(selectedUnit.canTraverse(place)){
                    // Update the unit variables
                    selectedUnit.canMove = false;
                    selectedUnit.mapPosition = newPosition;

                    // Update the map
                    mapView.getMap().placeAt(newPosition).unit = selectedUnit;
                    mapView.getMap().placeAt(oldPosition).unit = null;

                    // Check if the player has moved all their units
                    if (getCurrentTeam().getAvailableUnits() == 0)
                        changeTurns();
                }
                // Clear the highlight
                highlightUnit = null;

                // TODO: Highlight enemy units we can attack
            }
            else if(selectedUnit.canAttack){
                // They can't move but can attack
            }
            else{
                // They cannot do anything
                selectedUnit = null;
            }
        }
        else{
            // We didn't select a unit before
            // Check if we have just selected a unit
            Unit placeUnit = place.unit;

            // Highlight unit
            highlightUnit = placeUnit;

            // Can only select units from own team
            if(placeUnit != null && placeUnit.currentTeam == getCurrentPlayer())
                selectedUnit = placeUnit;
        }
    }

    @Override
    public void onDrawPlace(Place place, Canvas canvas, RectF rect){
        if(highlightUnit != null && highlightUnit.canTraverse(place)){
            Paint p = getHighlightPaint(highlightUnit);
            canvas.drawRect(rect, p);
        }
    }

    public Paint getHighlightPaint(Unit selectedUnit) {
        Paint paint;
        if (selectedUnit.currentTeam != getCurrentPlayer() || !selectedUnit.canMove)
            paint = paintHighlightOpponent;
        else
            paint = paintHighlight;

        return paint;
    }
}
