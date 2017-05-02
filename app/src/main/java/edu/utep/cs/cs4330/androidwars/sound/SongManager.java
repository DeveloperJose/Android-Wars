/**
 * Author: Jose Perez <josegperez@mail.com> and Diego Reynoso
 */
package edu.utep.cs.cs4330.androidwars.sound;

import android.content.Context;
import android.media.MediaPlayer;

public final class SongManager {
    private static SongManager mSongManager;

    private Context mContext;
    private MediaPlayer mediaPlayer;

    private SongManager(Context context) {
        mContext = context;
    }

    public static SongManager getInstance(Context context) {
        if (mSongManager == null)
            mSongManager = new SongManager(context);

        return mSongManager;
    }

    public void playSong(int resID) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(mContext, resID);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void onPause() {
        if (mediaPlayer != null)
            mediaPlayer.pause();
    }

    public void onResume() {
        if (mediaPlayer != null)
            mediaPlayer.start();
    }
}