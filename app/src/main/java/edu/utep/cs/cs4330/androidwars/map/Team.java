package edu.utep.cs.cs4330.androidwars.map;

import java.util.ArrayList;
import java.util.List;

import edu.utep.cs.cs4330.androidwars.map.unit.Unit;

public class Team {
    private List<Unit> unitList;
    private Map map;

    public int teamNumber;
    public Team(Map map, int teamNumber){
        unitList = new ArrayList<>();
        this.map = map;
        this.teamNumber = teamNumber;
    }

    public void addUnit(Unit unit){
        unit.currentTeam = teamNumber;
        map.placeAt(unit.mapPosition).unit = unit;
        unitList.add(unit);
    }

    public int getAvailableUnits(){
        int total = 0;
        for(Unit unit : unitList){
            if(unit.canMove)
                total++;
        }

        return total;
    }

    public void resetTurn(){
        for(Unit unit : unitList)
            unit.canMove = true;
    }

}
