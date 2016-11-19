package layout;

import android.support.v4.app.Fragment;

public class CardCreateActivity extends SingleFragmentActivity {
    @Override
    protected Fragment CreateFragment() {
        return new CardCreateFragment();
    }
}
