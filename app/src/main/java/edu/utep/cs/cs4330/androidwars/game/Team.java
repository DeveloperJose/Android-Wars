package edu.utep.cs.cs4330.androidwars.game;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.androidwars.game.map.Map;
import edu.utep.cs.cs4330.androidwars.game.unit.Unit;

public final class Team {
    /**
     * The list of units this team owns
     */
    private List<Unit> unitList;

    /**
     * A unique identifying number for this team
     */
    public int teamNumber;
    private Map map;

    public Team(Map map, int teamNumber) {
        unitList = new ArrayList<>();
        this.map = map;
        this.teamNumber = teamNumber;
    }

    public void addUnit(Unit unit) {
        unit.currentTeam = teamNumber;
        map.placeAt(unit.mapPosition).unit = unit;
        unitList.add(unit);
    }

    /**
     * Calculates how many units this team has left
     * that can still move
     *
     * @return Number of units who can still move
     */
    public int getAvailableUnits() {
        int total = 0;
        for (Unit unit : unitList) {
            if (unit.canMove)
                total++;
        }

        return total;
    }

    /**
     * Allows all of this team's units to move again
     */
    public void resetTurn() {
        for (Unit unit : unitList)
            unit.canMove = true;
    }
}
