/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.utep.cs.cs4330.androidwars.map.GameManager;
import edu.utep.cs.cs4330.androidwars.map.Map;
import edu.utep.cs.cs4330.androidwars.map.MapView;
import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.map.Team;
import edu.utep.cs.cs4330.androidwars.map.Vector2;
import edu.utep.cs.cs4330.androidwars.map.unit.TestUnit;
import edu.utep.cs.cs4330.androidwars.sound.SongManager;
import edu.utep.cs.cs4330.androidwars.sound.SoundManager;

public class SandboxActivity extends AppCompatActivity {
    private MapView mapViewSandbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        // Set-up Settings
        ResourceManager.context = this;

        mapViewSandbox = (MapView)findViewById(R.id.map_view_sandbox);

        Bundle intentData = getIntent().getExtras();
        Map map = null;
        if(intentData != null){
            String filename = intentData.getString(MapListActivity.MAP_FILENAME);
            map = Map.fromFilename(filename);
        }

        Team teamOne = new Team(map, 1);
        teamOne.addUnit(new TestUnit(new Vector2(0, 0)));
        teamOne.addUnit(new TestUnit(new Vector2(1, 0)));
        teamOne.addUnit(new TestUnit(new Vector2(2, 0)));
        teamOne.addUnit(new TestUnit(new Vector2(3, 0)));

        Team teamTwo = new Team(map, 2);
        teamTwo.addUnit(new TestUnit(new Vector2(map.width-1, map.height-1)));
        teamTwo.addUnit(new TestUnit(new Vector2(map.width-2, map.height-1)));
        teamTwo.addUnit(new TestUnit(new Vector2(map.width-3, map.height-1)));
        teamTwo.addUnit(new TestUnit(new Vector2(map.width-4, map.height-1)));

        mapViewSandbox.setMap(map);
        GameManager gameManager = new GameManager(mapViewSandbox, teamOne, teamTwo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SongManager.getInstance(this).playSong(R.raw.song_nowhere_land);
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
