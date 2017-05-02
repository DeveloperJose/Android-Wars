/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.game.map.Map;
import edu.utep.cs.cs4330.androidwars.game.terrain.Terrain;
import edu.utep.cs.cs4330.androidwars.game.terrain.TerrainForest;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;
import edu.utep.cs.cs4330.androidwars.sound.SongManager;
import edu.utep.cs.cs4330.androidwars.util.Vector2;

public class MapListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String EXTRA_MAP = "edu.utep.cs.cs4330.androidwars.MAP";
    public static final String MAP_FILENAME = "edu.utep.cs.cs4330.androidwars.MAP_FILENAME";

    private ListView listViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);

        ResourceManager.context = this;

        listViewMap = (ListView) findViewById(R.id.list_view_map);

        // Load the list with all the map filenames
        List<String> maps = ResourceManager.getMaps();
        maps.add("sandbox");
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.adapter_item, maps);
        listViewMap.setAdapter(adapter);

        listViewMap.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedFilename = (String) listViewMap.getAdapter().getItem(position);
        Intent intent = new Intent(this, SandboxActivity.class);

        if(selectedFilename.equalsIgnoreCase("sandbox")) {
            Map testMap = new Map(6, 10);
            testMap.placeAt(0, 2).terrain = new TerrainForest(new Vector2(0, 2));
            intent.putExtra(EXTRA_MAP, testMap);
        }
        else
            intent.putExtra(MAP_FILENAME, selectedFilename);

        startActivity(intent);
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

    @Override
    protected void onStart() {
        super.onStart();
        SongManager.getInstance(this).playSong(R.raw.song_kick_shock);
    }
}
