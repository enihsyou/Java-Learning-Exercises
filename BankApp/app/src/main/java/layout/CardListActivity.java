package layout;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.enihsyou.shane.bankapp.Account.Account;

public class CardListActivity extends SingleFragmentActivity {
    private static final String EXTRA_ACCOUNT = "com.enihsyou.shane.bankapp.account";

    public static Intent newIntent(Context packageContext, Account account) {
        Intent intent = new Intent(packageContext, CardListActivity.class);
        intent.putExtra(EXTRA_ACCOUNT, account);

        return intent;
    }

    @Override
    protected Fragment CreateFragment() {
        Account account = (Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT);
        return CardListFragment.newInstance(account);
    }
}
