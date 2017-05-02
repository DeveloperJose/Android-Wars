/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.resource.ResourceManager;

public class MainMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Set-up Settings
        ResourceManager.context = this;

    }

    public void onClickSinglePlayer(View v) {
        Intent intent = new Intent(this, SandboxActivity.class);
        startActivity(intent);
    }

    public void onClickMultiPlayer(View view) {

    }


}
