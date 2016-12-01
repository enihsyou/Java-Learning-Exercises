package com.enihsyou.shane.photogallery;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected android.support.v4.app.Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }

}
