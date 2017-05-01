/**
 * Author: Jose Perez <josegperez@mail.com>
 */
package edu.utep.cs.cs4330.androidwars.sound;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import edu.utep.cs.cs4330.androidwars.R;
import edu.utep.cs.cs4330.androidwars.activity.ResourceManager;

public final class SoundManager {
    /**
     * How many sounds can be played at once.
     */
    private static final int MAX_SOUND_POOL_STREAMS = 4;

    private static final int LOW_PRIORITY = 1;
    private static final int NORMAL_PRIORITY = 2;
    private static final int HIGH_PRIORITY = 3;

    private static int SONG_MAIN_ID = -1;
    private static int SONG_BATTLE_ID = -1;
    private static int SONG_CURRENT = -1;

    private static SoundPool soundPool;
    private static void loadSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(MAX_SOUND_POOL_STREAMS);
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(MAX_SOUND_POOL_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        SONG_MAIN_ID = soundPool.load(ResourceManager.context, R.raw.song_kick_shock, 1);
        SONG_BATTLE_ID = soundPool.load(ResourceManager.context, R.raw.song_nowhere_land, 1);
    }

    public static void playSong(SongType songType){
        if(soundPool == null)
           loadSoundPool();

        int soundID;
        if(songType == SongType.MainMenu)
            soundID = SONG_MAIN_ID;
        else
            soundID = SONG_BATTLE_ID;

        if(SONG_CURRENT != -1)
            soundPool.stop(SONG_CURRENT);

        SONG_CURRENT = soundPool.play(soundID, 1.0f, 1.0f, HIGH_PRIORITY, -1, 1.0f);
    }

    private SoundManager() { }
}
