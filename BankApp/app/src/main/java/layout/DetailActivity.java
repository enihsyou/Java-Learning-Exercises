package layout;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class DetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_CARD = "com.enihsyou.shane.bankapp.card";

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_CARD, uuid);

        return intent;
    }
    @Override
    protected Fragment CreateFragment() {
        UUID card = (UUID) getIntent().getSerializableExtra(EXTRA_CARD);
        return DetailFragment.newInstance(card);
    }
}
