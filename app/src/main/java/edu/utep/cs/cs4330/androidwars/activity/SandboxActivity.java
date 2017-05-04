/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.activity;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.game.GameManager;
import edu.utep.cs.cs4330.androidwars.game.Team;
import edu.utep.cs.cs4330.androidwars.game.map.Map;
import edu.utep.cs.cs4330.androidwars.game.map.Place;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitArcher;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitPegasus;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitHealer;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitKnight;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitSwordsman;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitTest;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitThief;
import edu.utep.cs.cs4330.androidwars.game.unit.UnitWizard;
import edu.utep.cs.cs4330.androidwars.game.view.MapView;
import edu.utep.cs.cs4330.androidwars.game.view.MapViewListener;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;
import edu.utep.cs.cs4330.androidwars.sound.SongManager;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

import static android.view.MotionEvent.ACTION_UP;

public class SandboxActivity extends AppCompatActivity {
    private MapView mapViewSandbox;
    private Button btnEndTurn;

    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        // Set-up Settings
        ResourceManager.context = this;

        mapViewSandbox = (MapView) findViewById(R.id.map_view_sandbox);
        btnEndTurn = (Button)findViewById(R.id.btnEndTurn);

        Bundle intentData = getIntent().getExtras();
        Map map = null;
        if (intentData != null) {
            Object objMap = intentData.get(MapListActivity.EXTRA_MAP);

            if(objMap != null)
                map = (Map)objMap;
            else {
                String filename = intentData.getString(MapListActivity.MAP_FILENAME);
                map = Map.fromFilename(filename);
            }
        }

        Team teamOne = new Team(map, 1);
        teamOne.addUnit(new UnitTest(new Vector2(0, 0)));
        teamOne.addUnit(new UnitPegasus(new Vector2(1, 0)));
        teamOne.addUnit(new UnitHealer(new Vector2(2, 0)));
        teamOne.addUnit(new UnitKnight(new Vector2(3, 0)));

        Team teamTwo = new Team(map, 2);
        teamTwo.addUnit(new UnitSwordsman(new Vector2(map.width - 1, map.height - 1)));
        teamTwo.addUnit(new UnitThief(new Vector2(map.width - 2, map.height - 1)));
        teamTwo.addUnit(new UnitWizard(new Vector2(map.width - 3, map.height - 1)));
        teamTwo.addUnit(new UnitArcher(new Vector2(map.width - 4, map.height - 1)));

        mapViewSandbox.setMap(map);
        gameManager = new GameManager(mapViewSandbox, teamOne, teamTwo);

        mapViewSandbox.registerListener(new MapViewListener() {
            @Override
            public void onSelectPlace(Place place) {
                if(btnEndTurn.getVisibility() == View.VISIBLE)
                    btnEndTurn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawPlace(Place place, Canvas canvas, RectF rect) {

            }

            @Override
            public void onHoldPlace(Place place, int duration) {
                if(place.unit == null && duration > 10){
                        btnEndTurn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void onClickBtnEndTurn(View view){
        gameManager.changeTurns();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SongManager.getInstance(this).playSong(R.raw.song_kick_shock);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SongManager.getInstance(this).onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SongManager.getInstance(this).onResume();
    }
}
