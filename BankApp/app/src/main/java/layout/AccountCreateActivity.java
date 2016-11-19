package layout;

import android.support.v4.app.Fragment;

public class AccountCreateActivity extends SingleFragmentActivity {

    @Override
    protected Fragment CreateFragment() {
        return new AccountCreateFragment();
    }
}

