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
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.Card.CardLab;
import com.enihsyou.shane.bankapp.Card.DebitCard;
import com.enihsyou.shane.bankapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class CardListFragment extends Fragment {
    private RecyclerView mCardRecyclerView;
    private CardAdapter mCardAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_card, container, false);

        mCardRecyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);
        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        CardLab cardLab = CardLab.getCardLab(getActivity());
        ArrayList<BaseCard> cards = cardLab.getCards();

        mCardAdapter = new CardAdapter(cards);
        mCardRecyclerView.setAdapter(mCardAdapter);
    }

    private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private BaseCard mCard;

        private final TextView mCardBalance;
        private final TextView mCardRemain;
        private final TextView mCardNumber;
        private final TextView mCardType;

        CardHolder(View itemView) {
            super(itemView);

            /*给变量赋值 样式对象*/
            mCardBalance = (TextView) itemView.findViewById(R.id.list_item_card_balance);
            mCardRemain = (TextView) itemView.findViewById(R.id.list_item_card_remain);
            mCardNumber = (TextView) itemView.findViewById(R.id.list_item_card_number);
            mCardType = (TextView) itemView.findViewById(R.id.list_item_card_type);
            /*设置点击监听器*/
            itemView.setOnClickListener(this);
        }

        void bindCard(BaseCard card) {
            mCard = card;

            mCardBalance.setText(BaseCard.format(card.getBalance()));
            if (!(card instanceof DebitCard)) mCardRemain.setText(BaseCard.format(card.getRemain()));
            else mCardRemain.setText("");
            mCardNumber.setText(String.format(Locale.getDefault(), "卡号: %d", card.getCardNumber()));
            mCardType.setText(card.getCardName());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCard.getCardName() + "Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardHolder> {

        private ArrayList<BaseCard> mCards;

        CardAdapter(ArrayList<BaseCard> cards) {
            mCards = cards;
        }

        @Override
        public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_card, parent, false);
            return new CardHolder(view);
        }

        @Override
        public void onBindViewHolder(CardHolder holder, int position) {
            BaseCard card = mCards.get(position);
            holder.bindCard(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }
    }

}

