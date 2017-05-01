/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.map.unit;

import edu.utep.cs.cs4330.androidwars.map.Sprite;

public abstract class Unit extends Sprite {
    public Unit(String filename, int colorDebug) {
        super(filename, colorDebug);
    }
}
