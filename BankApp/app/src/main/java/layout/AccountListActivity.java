package layout;

import android.support.v4.app.Fragment;

public class AccountListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment CreateFragment() {
        return new AccountListFragment();
    }
}

