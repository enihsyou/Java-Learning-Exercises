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
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.Card.DebitCard;
import com.enihsyou.shane.bankapp.R;

public class CardFragment extends Fragment {
    private BaseCard mCard;

    private EditText mCardNumberInput;
    private Button mCardCreateButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCard = new DebitCard(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        mCardNumberInput = (EditText) view.findViewById(R.id.input_card_number);
        mCardCreateButton = (Button) view.findViewById(R.id.button_create_card);

        mCardNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCard.setCardNumber(Long.parseLong(charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mCardCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "按钮按下", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
