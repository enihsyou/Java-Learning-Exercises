package layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.Card.CardLab;
import com.enihsyou.shane.bankapp.R;

import java.math.BigDecimal;
import java.util.UUID;

public class DetailFragment extends Fragment {
    private static final String ARG_CARD = "card";
    private static final int DEPOSIT = 101;
    private static final int WITHDRAW = 102;
    private static final int PURCHASE = 103;
    private BaseCard mCard;
    private TextView mBalanceView;
    private TextView mRemainView;
    private Button mDepositButton;
    private Button mWithdrawButton;
    private Button mPurchaseButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCard = CardLab.get(getActivity()).getCard((UUID) getArguments().getSerializable(ARG_CARD));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mBalanceView = (TextView) view.findViewById(R.id.balance_view);
        mRemainView = (TextView) view.findViewById(R.id.remain_view);
        mDepositButton = (Button) view.findViewById(R.id.button_deposit);
        mWithdrawButton = (Button) view.findViewById(R.id.button_withdraw);
        mPurchaseButton = (Button) view.findViewById(R.id.button_purchase);

        updateUI();

        mDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater
                        .from(getActivity())
                        .inflate(R.layout.dialog_fragment_deposit, container, false);
                final EditText editText = (EditText) view1.findViewById(R.id.input_deposit_amount);
                // editText.setText("0");

                setTargetFragment(DetailFragment.this, DEPOSIT);
                new AlertDialog.Builder(getActivity())
                        .setView(view1)
                        .setTitle(R.string.deposit)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = editText.getText().toString();
                                input = input.equals("") ? "0" : input;
                                sendCard(Activity.RESULT_OK, input);
                            }
                        })
                        .create()
                        .show();
            }
        });
        mWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater
                        .from(getActivity())
                        .inflate(R.layout.dialog_fragment_withdraw, container, false);
                final EditText editText = (EditText) view1.findViewById(R.id.input_withdraw_amount);
                // editText.setText("0");

                setTargetFragment(DetailFragment.this, WITHDRAW);
                new AlertDialog.Builder(getActivity())
                        .setView(view1)
                        .setTitle(R.string.withdraw)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = editText.getText().toString();
                                input = input.equals("") ? "0" : input;
                                sendCard(Activity.RESULT_OK, input);
                            }
                        })
                        .create()
                        .show();
            }
        });
        mPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater
                        .from(getActivity())
                        .inflate(R.layout.dialog_fragment_purchase, container, false);
                final EditText editText1 = (EditText) view1.findViewById(R.id.input_purchase_amount);
                final EditText editText2 = (EditText) view1.findViewById(R.id.input_purchase_terms);
                // editText1.setText("0");
                editText2.setText("1");
                if (BigDecimal.ZERO.equals(mCard.getQuota())) editText2.setVisibility(View.INVISIBLE);

                setTargetFragment(DetailFragment.this, PURCHASE);
                new AlertDialog.Builder(getActivity())
                        .setView(view1)
                        .setTitle(R.string.purchase)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input1 = editText1.getText().toString();
                                input1 = input1.equals("") ? "0" : input1;
                                String input2 = editText2.getText().toString();
                                sendCard(Activity.RESULT_OK, input1, input2);
                            }
                        })
                        .create()
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Log.e("1", mCard.getBalance().toString());

        if (resultCode != Activity.RESULT_OK) return;
        BigDecimal amount;
        int terms;
        switch (requestCode) {
            case DEPOSIT:
                amount = new BigDecimal((String) data.getSerializableExtra("amount"));
                if (BigDecimal.ZERO.equals(mCard.deposit(amount)))
                    Toast.makeText(getActivity(), "什么也没发生", Toast.LENGTH_SHORT).show();
                break;
            case WITHDRAW:
                amount = new BigDecimal((String) data.getSerializableExtra("amount"));
                if (BigDecimal.ZERO.compareTo(mCard.withdraw(amount)) > 0)
                    Toast.makeText(getActivity(), "没钱了", Toast.LENGTH_SHORT).show();
                break;
            case PURCHASE:
                amount = new BigDecimal((String) data.getSerializableExtra("amount"));
                terms = Integer.parseInt((String) data.getSerializableExtra("terms"));
                if (BigDecimal.ZERO.compareTo(mCard.withdraw(amount, terms)) > 0)
                    Toast.makeText(getActivity(), "钱不够了", Toast.LENGTH_SHORT).show();
                break;
        }
        updateUI();
    }

    private void updateUI() {
        mBalanceView.setText(getResources().getString(R.string.card_balance_string, BaseCard.format(mCard.getBalance())));
        if (BigDecimal.ZERO.equals(mCard.getQuota())) mRemainView.setText("");
        else
            mRemainView.setText(getResources().getString(R.string.card_remain_string, BaseCard.format(mCard.getRemain())));
    }

    private void sendCard(int resultCode, String amount) {
        Intent intent = new Intent();
        if (getTargetFragment() == null) return;
        intent.putExtra("amount", amount);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void sendCard(int resultCode, String amount, String terms) {
        Intent intent = new Intent();
        if (getTargetFragment() == null) return;
        intent.putExtra("amount", amount);
        intent.putExtra("terms", terms);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    public static Fragment newInstance(UUID card) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD, card);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
