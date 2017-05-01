package edu.utep.cs.cs4330.androidwars.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.util.ResourceManager;

public class MapListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public static final String MAP_FILENAME = "edu.utep.cs.cs4330.androidwars.MAP_FILENAME";
    private ListView listViewMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);

        ResourceManager.resources = getResources();
        ResourceManager.packageName = getPackageName();

        listViewMap = (ListView)findViewById(R.id.list_view_map);

        // Load the list with all the map filenames
        List<String> maps = ResourceManager.getMaps();
        ListAdapter adapter = new ArrayAdapter<>(this, R.layout.adapter_item, maps);
        listViewMap.setAdapter(adapter);

        listViewMap.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedFilename = (String)listViewMap.getAdapter().getItem(position);
        Intent intent = new Intent(this, SandboxActivity.class);
        intent.putExtra(MAP_FILENAME, selectedFilename);
        startActivity(intent);
    }
}
