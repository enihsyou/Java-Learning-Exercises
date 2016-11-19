package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.enihsyou.shane.bankapp.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_account_container);

        if (fragment == null) {
            fragment = CreateFragment();
            manager.beginTransaction().add(R.id.fragment_account_container, fragment).commit();
        }
    }

    protected abstract Fragment CreateFragment();
}

