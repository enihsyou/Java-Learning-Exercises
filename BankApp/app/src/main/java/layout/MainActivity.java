package layout;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment CreateFragment() {
        return new MainFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(MainFragment.DETAIL_FRAGMENT);
    }
}
