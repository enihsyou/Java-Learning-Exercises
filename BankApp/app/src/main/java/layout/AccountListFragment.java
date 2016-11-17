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
import com.enihsyou.shane.bankapp.Account.Account;
import com.enihsyou.shane.bankapp.Account.AccountLab;
import com.enihsyou.shane.bankapp.R;

import java.util.ArrayList;

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

    private class AccountHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;

        public AccountHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
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
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new AccountHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountHolder holder, int position) {
            Account account = mAccounts.get(position);
            holder.mNameTextView.setText(account.getAccountName());
        }

        @Override
        public int getItemCount() {
            return mAccounts.size();
        }
    }
}

