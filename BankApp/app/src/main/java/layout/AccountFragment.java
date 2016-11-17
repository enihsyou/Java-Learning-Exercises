package layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.enihsyou.shane.bankapp.Account.Account;
import com.enihsyou.shane.bankapp.R;

public class AccountFragment extends Fragment {
    private Account mAccount;

    private EditText mAccountNameInput;
    private Button mAccountCreateButton;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccount = new Account();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mAccountNameInput = (EditText) view.findViewById(R.id.input_account_name);
        mAccountCreateButton = (Button) view.findViewById(R.id.button_create_account);

        mAccountNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAccount.setAccountName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mAccountCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "touched", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
