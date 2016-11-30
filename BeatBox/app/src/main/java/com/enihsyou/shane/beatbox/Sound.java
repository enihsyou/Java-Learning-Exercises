package com.enihsyou.shane.beatbox;

import android.text.TextUtils;

import java.util.Arrays;

public class Sound {
    private String mAssetPath;
    private String mName;
    private Integer mSoundID;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");
        String filename = components[components.length - 1];
        String[] strings = filename.split("\\.");
        mName = TextUtils.join("", Arrays.copyOfRange(strings, 0, strings.length - 1));
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundID() {
        return mSoundID;
    }

    public void setSoundID(Integer soundID) {
        mSoundID = soundID;
    }
}
