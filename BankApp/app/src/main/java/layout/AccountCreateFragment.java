package layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.enihsyou.shane.bankapp.Account.Account;
import com.enihsyou.shane.bankapp.R;

public class AccountCreateFragment extends DialogFragment {
    private Account mAccount;

    private EditText mAccountNameInput;

    public static final String EXTRA_ACCOUNT = "com.enihsyou.shane.bankapp.account";

    private static final String ARG_CREATE_ACCOUNT = "create_account";

    public static AccountCreateFragment newInstance(Account account) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CREATE_ACCOUNT, account);

        AccountCreateFragment fragment = new AccountCreateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Account account = (Account) getArguments().getSerializable(ARG_CREATE_ACCOUNT);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_create_account, null);

        mAccountNameInput = (EditText) view.findViewById(R.id.input_account_name);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.fix_create)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        account.setAccountName(mAccountNameInput.getText().toString()); //设置新建的账户的名字
                        sendResult(Activity.RESULT_OK, account); //传回结果
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Account account) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ACCOUNT, account);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
