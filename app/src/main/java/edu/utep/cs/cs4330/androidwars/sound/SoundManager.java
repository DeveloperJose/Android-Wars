/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 * https://gist.github.com/tom-dignan/2212532
 */
package edu.utep.cs.cs4330.androidwars.sound;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
    /** SoundPool left volume */
    private static final float LEFT_VOLUME = 1.0f;

    /** SoundPool right volume */
    private static final float RIGHT_VOLUME = 1.0f;

    /** All sounds will have equal priority */
    private static final int STREAM_PRIORITY  = 0;

    /** No loop mode */
    public static final int MODE_NO_LOOP = 0;

    /** SoundPool playback rate */
    private static final float PLAYBACK_RATE = 1.0f;

    private static final String TAG = "AndroidWars.SoundMngr";

    /** Inner SoundManager instance */
    private static SoundManager sInstance = null;

    /** Mapping of resource ids to sound ids returned by loadSound() */
    private HashMap<Integer, Integer> mSoundMap = new HashMap<>();

    /** SoundPool instance */
    private SoundPool mSoundPool;

    /** Application Context */
    private Context mContext;

    /** Maximum concurrent streams that canTraverse playSound */
    private static final int MAX_STREAMS = 2;

    /** Private constructor for singleton */
    private SoundManager(Context context) {
        mContext = context.getApplicationContext();

        // Sound effect management
        mSoundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mSoundPool.play(sampleId, LEFT_VOLUME, RIGHT_VOLUME, STREAM_PRIORITY, MODE_NO_LOOP, PLAYBACK_RATE);
            }
        });
    }

    /** Static access to internal instance */
    public static SoundManager getInstance(Context context) {
        if (sInstance == null)
            sInstance = new SoundManager(context.getApplicationContext());

        return sInstance;
    }

    /** Loads a sound. Called automatically by playSound() if not already loaded */
    public void loadSound(int id) {
        mSoundMap.put(id, mSoundPool.load(mContext, id, 1));
    }

    /**
     * Test if sound is loaded, call with id from R.raw
     *
     * @param resourceId
     * @return true|false
     */
    public boolean isSoundLoaded(int resourceId) {
        return mSoundMap.containsKey(resourceId);
    }

    /** Unload sound, prints warning if sound is not loaded */
    public void unloadSound(int id) {
        if (mSoundMap.containsKey(id)) {
            int soundId = mSoundMap.remove(id);
            mSoundPool.unload(soundId);
        } else {
            Log.w(TAG, "sound: " + id + " is not loaded!");
        }
    }

    public void playSound(int resourceId) {
        if (isSoundLoaded(resourceId))
            mSoundPool.play(mSoundMap.get(resourceId), LEFT_VOLUME, RIGHT_VOLUME, STREAM_PRIORITY, MODE_NO_LOOP, PLAYBACK_RATE);
        else
            loadSound(resourceId);
    }
}