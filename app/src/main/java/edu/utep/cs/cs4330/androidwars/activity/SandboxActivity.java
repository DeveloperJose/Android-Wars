package edu.utep.cs.cs4330.androidwars.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.utep.cs.cs4330.androidwars.map.Map;
import edu.utep.cs.cs4330.androidwars.map.MapView;
import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.util.ResourceManager;
import edu.utep.cs.cs4330.androidwars.map.terrain.TerrainForest;
import edu.utep.cs.cs4330.androidwars.map.terrain.TerrainPlain;

public class SandboxActivity extends AppCompatActivity {

    private MapView mapViewSandbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandbox);

        // Set-up Settings
        // TODO: Remove when Sandbox is not the main
        ResourceManager.resources = getResources();
        ResourceManager.packageName = getPackageName();

        mapViewSandbox = (MapView)findViewById(R.id.map_view_sandbox);

        Map map = new Map(6, 8);
        map.placeAt(0,0).terrain = new TerrainPlain();
        map.placeAt(5,6).terrain = new TerrainForest();

        mapViewSandbox.setMap(map);
    }
}
