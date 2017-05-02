package edu.utep.cs.cs4330.androidwars.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

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

    @Override
    public void onUnitMove(Unit selectedUnit, Vector2 oldPosition, Vector2 newPosition) {
        if (selectedUnit.currentTeam != getCurrentPlayer())
            return;

        if (!selectedUnit.canMove)
            return;

        // The player wants to move and can traverse the place
        if (selectedUnit.canTraverse(mapView.getMap(), newPosition)) {
            selectedUnit.canMove = false;
            selectedUnit.mapPosition = newPosition;

            mapView.getMap().placeAt(newPosition).unit = selectedUnit;
            mapView.getMap().placeAt(oldPosition).unit = null;

            // Check if the player has moved all their units
            if (getCurrentTeam().getAvailableUnits() == 0)
                changeTurns();
        }
    }

    @Override
    public void onUnitHighlight(Unit selectedUnit, Canvas canvas, RectF rect) {
        Paint paint;
        if (selectedUnit.currentTeam != getCurrentPlayer() || !selectedUnit.canMove)
            paint = paintHighlightOpponent;
        else
            paint = paintHighlight;

        canvas.drawRect(rect, paint);
    }
}
