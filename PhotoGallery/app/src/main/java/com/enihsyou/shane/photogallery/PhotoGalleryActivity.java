package com.enihsyou.shane.photogallery;

import android.content.Context;
import android.content.Intent;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PhotoGalleryActivity.class);
    }
}
