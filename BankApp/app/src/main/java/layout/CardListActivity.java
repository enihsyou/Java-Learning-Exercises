package layout;

import android.support.v4.app.Fragment;

public class CardListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment CreateFragment() {
        return new CardListFragment();
    }
}
