package layout;

import android.support.v4.app.Fragment;

import java.util.UUID;

public class CardListActivity extends SingleFragmentActivity {
    private static final String EXTRA_ACCOUNT_ID = "com.enihsyou.shane.bankapp.account_id";

    @Override
    protected Fragment CreateFragment() {
        UUID accountID = (UUID) getIntent().getSerializableExtra(EXTRA_ACCOUNT_ID);
        return CardListFragment.newInstance(accountID);
    }
}
