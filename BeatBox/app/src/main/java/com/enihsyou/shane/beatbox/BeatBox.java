package com.enihsyou.shane.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BeatBox {
    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;
    private AssetManager mAssetManager;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    public BeatBox(Context context) {
        mAssetManager = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssetManager.list(SOUNDS_FOLDER);
            Log.i(TAG, String.format("loadSounds: Found %1$d sounds", soundNames.length));
            for (String sound : soundNames) {
                try {
                    String assetPath = SOUNDS_FOLDER + "/" + sound;
                    Sound sound1 = new Sound(assetPath);
                    load(sound1);
                    mSounds.add(sound1);
                } catch (Exception e) {
                    Log.e(TAG, "loadSounds: Could not load sound" + sound, e);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "loadSounds: Could not list assets", e);
        }
    }

    private void load(Sound sound) throws IOException {
        AssetFileDescriptor assetFileDescriptor = mAssetManager.openFd(sound.getAssetPath());
        int soundID = mSoundPool.load(assetFileDescriptor, 1);
        sound.setSoundID(soundID);
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void play(Sound sound) {
        Integer soundID = sound.getSoundID();
        if (soundID == null) return;
        mSoundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        mSoundPool.release();
    }
}
