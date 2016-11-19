package layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import com.enihsyou.shane.bankapp.Account.Account;
import com.enihsyou.shane.bankapp.Account.AccountLab;
import com.enihsyou.shane.bankapp.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 展示所有的账户，使用列表方式，点击跳转到对应账户的卡片列表
 */
public class AccountListFragment extends Fragment {
    public static final String EXTRA_SHOW_ACCOUNT = "EXTRA_SHOW_ACCOUNT";

    private static final String TAG_CREATE_ACCOUNT = "CREATE_ACCOUNT_DIALOG";

    private static final int REQUEST_CREATE_ACCOUNT = 100;

    private RecyclerView mAccountRecyclerView;
    private AccountAdapter mAccountAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_account, container, false);

        mAccountRecyclerView = (RecyclerView) view.findViewById(R.id.account_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        // AccountLab.get(getActivity()).updateAccount();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_account_list, menu); //添加菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_account:
                FragmentManager manager = getFragmentManager();

                Account account = new Account();

                AccountCreateFragment dialog = AccountCreateFragment.newInstance(account);
                dialog.setTargetFragment(AccountListFragment.this, REQUEST_CREATE_ACCOUNT);
                dialog.show(manager, TAG_CREATE_ACCOUNT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CREATE_ACCOUNT:
                Account account = (Account) data.getSerializableExtra(AccountCreateFragment.EXTRA_ACCOUNT);
                mAccountAdapter.mAccounts.add(account);
                updateUI();
        }
    }

    private void updateUI() {
        if (mAccountAdapter == null) {
            AccountLab accountLab = AccountLab.get(getActivity());
            mAccountAdapter = new AccountAdapter(accountLab.getAccounts());
            mAccountRecyclerView.setAdapter(mAccountAdapter);
        } else mAccountAdapter.notifyDataSetChanged();
    }

    private class AccountHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Account mAccount;

        private final TextView mAccountName;
        private final TextView mAccountCardCount;

        AccountHolder(View itemView) {
            super(itemView);
            /*给变量赋值 样式对象*/
            mAccountName = (TextView) itemView.findViewById(R.id.list_item_account_name);
            mAccountCardCount = (TextView) itemView.findViewById(R.id.list_item_account_card_count);
            /*设置点击监听器*/
            itemView.setOnClickListener(this);
        }

        void bindAccount(Account account) {
            mAccount = account;
            /*更新显示的内容*/
            mAccountName.setText(String.format(Locale.getDefault(), "账户: %s", account.getAccountName()));
            mAccountCardCount.setText(String.format(Locale.getDefault(), "卡片数量: %d", account.getAccountCards().size()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = CardListActivity.newIntent(getActivity(), mAccount);

            startActivity(intent);
        }
    }

    private class AccountAdapter extends RecyclerView.Adapter<AccountHolder> {
        private ArrayList<Account> mAccounts;

        AccountAdapter(ArrayList<Account> accounts) {
            mAccounts = accounts;
        }

        @Override
        public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_account, parent, false);
            return new AccountHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountHolder holder, int position) {
            Account account = mAccounts.get(position);
            holder.bindAccount(account);
        }

        @Override
        public int getItemCount() {
            return mAccounts.size();
        }
    }

}

