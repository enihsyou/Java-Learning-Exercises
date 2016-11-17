package layout;

import android.support.v4.app.Fragment;

public class AccountActivity extends SingleFragmentActivity {

    @Override
    protected Fragment CreateFragment() {
        return new AccountFragment();
    }
}
