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

public class CardCreateFragment extends DialogFragment {
    public static final String EXTRA_CARD = "com.enihsyou.shane.bankapp.card";

    private static final String ARG_CREATE_CARD = "create_card";

    private EditText mCardNumberInput;
    private EditText mCardBalanceInput;
    private RadioGroup mCardTypeRadio;

    public static CardCreateFragment newInstance(BaseCard baseCard) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CREATE_CARD, baseCard);

        CardCreateFragment fragment = new CardCreateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BaseCard card = (BaseCard) getArguments().getSerializable(ARG_CREATE_CARD);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_create_card, null);

        mCardNumberInput = (EditText) view.findViewById(R.id.input_card_number);
        mCardBalanceInput = (EditText) view.findViewById(R.id.input_card_balance);
        mCardTypeRadio = (RadioGroup) view.findViewById(R.id.radio_card_type_group);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.create_new_card)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mCardNumberInput.getText().toString().trim().isEmpty()) Toast.makeText(
                                getActivity(),
                                "输入卡号为空",
                                Toast.LENGTH_SHORT).show();
                        else if (mCardBalanceInput.getText().toString().trim().isEmpty()) Toast.makeText(
                                getActivity(),
                                "输入余额为空",
                                Toast.LENGTH_SHORT).show();
                        else {
                            BigDecimal amount = new BigDecimal(mCardBalanceInput.getText().toString());
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
                            card.setCardNumber(Long.parseLong(mCardNumberInput.getText().toString()));
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
