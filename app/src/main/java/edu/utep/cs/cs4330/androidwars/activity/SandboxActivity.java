/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.utep.cs.cs4330.androidwars.map.Map;
import edu.utep.cs.cs4330.androidwars.map.MapView;
import edu.utep.cs.cs4330.androidwars.R;

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
        mapViewSandbox.setMap(map);
    }
}
