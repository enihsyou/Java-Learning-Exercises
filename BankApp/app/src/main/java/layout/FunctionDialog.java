package layout;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.R;

import java.math.BigDecimal;

public class FunctionDialog extends DialogFragment {
    private static final String ARG_FUNCTION_CARD = "ARG_FUNCTION_CARD";
    private static CardListFragment sCardListFragment;
    private Button mDepositButton;
    private Button mWithdrawButton;
    private Button mPurchaseButton;

    public static FunctionDialog newInstance(BaseCard card, CardListFragment cardListFragment) {
        sCardListFragment = cardListFragment;
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_FUNCTION_CARD, card);

        FunctionDialog fragment = new FunctionDialog();
        fragment.setArguments(bundle);

        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BaseCard card = (BaseCard) getArguments().getSerializable(ARG_FUNCTION_CARD);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_function, null);

        mDepositButton = (Button) view.findViewById(R.id.button_deposit);
        mWithdrawButton = (Button) view.findViewById(R.id.button_withdraw);
        mPurchaseButton = (Button) view.findViewById(R.id.button_purchase);

        mDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_deposit, null);

                new AlertDialog.Builder(getActivity()).setView(view1).setTitle(R.string.deposit).setPositiveButton
                        (android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = ((EditText) view1.findViewById(R.id.input_deposit_amount))
                                        .getText()
                                        .toString();
                                BigDecimal amount = new BigDecimal(input);
                                card.deposit(amount);
                            }
                        }).create().show();
                sCardListFragment.updateUI();

            }
        });
        mWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_withdraw, null);
                new AlertDialog.Builder(getActivity()).setView(view1).setTitle(R.string.withdraw).setPositiveButton
                        (android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = ((EditText) view1.findViewById(R.id.input_withdraw_amount))
                                        .getText()
                                        .toString();
                                BigDecimal amount = new BigDecimal(input);
                                card.withdraw(amount);
                            }
                        }).create().show();
                sCardListFragment.updateUI();
            }
        });
        mPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_purchase, null);
                new AlertDialog.Builder(getActivity()).setView(view1).setTitle(R.string.purchase).setPositiveButton
                        (android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input1 = ((EditText) view1.findViewById(R.id.input_purchase_amount))
                                        .getText()
                                        .toString();
                                String input2 = ((EditText) view1.findViewById(R.id.input_purchase_terms))
                                        .getText()
                                        .toString();
                                BigDecimal amount = new BigDecimal(input1);
                                int terms = Integer.parseInt(input2);

                                card.withdraw(amount, terms);
                            }
                        }).create().show();
                sCardListFragment.updateUI();
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.choose_function)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        sCardListFragment.updateUI();
                    }
                })
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
