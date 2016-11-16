package com.enihsyou.shane.bankapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class CardFragment extends Fragment {
    private Card mCard;
    private android.widget.EditText mCardNumberInput;
    private android.widget.Spinner mCardTypePicker;
    private android.widget.EditText moperateAmountInput;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCard = new DebitCard(10);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        this.moperateAmountInput = (EditText) view.findViewById(R.id.operate_amount_input);
        this.mCardTypePicker = (Spinner) view.findViewById(R.id.card_type_picker);
        this.mCardNumberInput = (EditText) view.findViewById(R.id.card_number_input);

        moperateAmountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCard.setCardName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}
