package layout;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.Card.CreditCard;
import com.enihsyou.shane.bankapp.Card.DebitCard;
import com.enihsyou.shane.bankapp.Card.PlatinumCard;
import com.enihsyou.shane.bankapp.R;

import java.math.BigDecimal;

public class MainDialog extends DialogFragment {
    public static final String EXTRA_CARD = "com.enihsyou.shane.bankapp.card";
    private static final String ARG_CREATE_CARD = "create";
    private EditText mAccountNameInput;
    private EditText mCardNumberInput;
    private EditText mCardBalanceInput;
    private RadioGroup mCardTypeRadio;

    public static MainDialog newInstance(BaseCard baseCard) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CREATE_CARD, baseCard);

        MainDialog fragment = new MainDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BaseCard card = (BaseCard) getArguments().getSerializable(ARG_CREATE_CARD);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_create_main, null);
        mAccountNameInput = (EditText) view.findViewById(R.id.input_account_name);
        mCardNumberInput = (EditText) view.findViewById(R.id.input_card_number);
        mCardBalanceInput = (EditText) view.findViewById(R.id.input_card_balance);
        mCardTypeRadio = (RadioGroup) view.findViewById(R.id.radio_card_type_group);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.create_new_card)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String accountName = mAccountNameInput.getText().toString().trim();
                        String cardNumber = mCardNumberInput.getText().toString().trim();
                        String cardBalance = mCardBalanceInput.getText().toString().trim();
                        if (accountName.isEmpty()) Toast.makeText(getActivity(), "输入户名为空", Toast.LENGTH_SHORT).show();
                        else if (cardNumber.isEmpty()) Toast
                                .makeText(getActivity(), "输入卡号为空", Toast.LENGTH_SHORT)
                                .show();
                        else if (cardBalance.isEmpty()) Toast
                                .makeText(getActivity(), "输入余额为空", Toast.LENGTH_SHORT)
                                .show();
                        else {
                            BigDecimal amount = new BigDecimal(cardBalance);
                            switch (mCardTypeRadio.getCheckedRadioButtonId()) {
                                case R.id.radio_DebitCard:
                                    card.loadProperty(DebitCard.class, card, amount);
                                    break;
                                case R.id.radio_CreditCard:
                                    card.loadProperty(CreditCard.class, card, amount);
                                    break;
                                case R.id.radio_PlatinumCard:
                                    card.loadProperty(PlatinumCard.class, card, amount);
                                    break;
                            }
                            card.setCardNumber(Long.parseLong(cardNumber));
                            card.setAccountName(accountName);
                            sendResult(Activity.RESULT_OK, card);
                        }
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, BaseCard card) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CARD, card);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
