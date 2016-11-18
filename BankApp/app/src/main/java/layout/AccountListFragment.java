package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.enihsyou.shane.bankapp.Account.Account;
import com.enihsyou.shane.bankapp.Account.AccountLab;
import com.enihsyou.shane.bankapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class AccountListFragment extends Fragment {
    private RecyclerView mAccountRecyclerView;
    private AccountAdapter mAccountAdapter;

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

    private void updateUI() {
        AccountLab accountLab = AccountLab.getAccountLab(getActivity());
        ArrayList<Account> accounts = accountLab.getAccounts();

        mAccountAdapter = new AccountAdapter(accounts);
        mAccountRecyclerView.setAdapter(mAccountAdapter);
    }

    private class AccountHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Account mAccount;

        private TextView mAccountName;
        private TextView mAccountCardCount;

        public AccountHolder(View itemView) {
            super(itemView);
            /*给变量赋值 样式对象*/
            mAccountName = (TextView) itemView.findViewById(R.id.list_item_account_name);
            mAccountCardCount = (TextView) itemView.findViewById(R.id.list_item_account_card_count);
            /*设置点击监听器*/
            itemView.setOnClickListener(this);
        }

        public void bindAccount(Account account) {
            mAccount = account;
            /*更新显示的内容*/
            mAccountName.setText(String.format(Locale.getDefault(), "账户: %s", account.getAccountName()));
            mAccountCardCount.setText(String.format(Locale.getDefault(), "卡片数量: %d", account.getAccountCards().size()));
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mAccount.getAccountName() + "Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private class AccountAdapter extends RecyclerView.Adapter<AccountHolder> {
        private ArrayList<Account> mAccounts;

        public AccountAdapter(ArrayList<Account> accounts) {
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

