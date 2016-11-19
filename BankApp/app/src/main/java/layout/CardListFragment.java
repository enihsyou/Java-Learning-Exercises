package layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import com.enihsyou.shane.bankapp.Account.Account;
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.Card.CardLab;
import com.enihsyou.shane.bankapp.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class CardListFragment extends Fragment {
    private static final int REQUEST_CREATE_CARD = 101;
    private static final String TAG_CREATE_CARD = "CREATE_ACCOUNT_DIALOG";
    private static final int REQUEST_FUNCTION = 102;
    private static final String TAG_FUNCTION = "FUNCTION";
    private RecyclerView mCardRecyclerView;
    private CardAdapter mCardAdapter;
    private Account mAccount; //隶属的账户
    private CardListFragment mCardListFragment;
    private static final String ARG_ACCOUNT = "ARG_ACCOUNT";

    public static CardListFragment newInstance(Account account) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT, account);

        CardListFragment fragment = new CardListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccount = (Account) getArguments().getSerializable(ARG_ACCOUNT);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_card_list, menu); //添加菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_card:
                FragmentManager manager = getFragmentManager();

                BaseCard card = new BaseCard();

                CardCreateFragment dialog = CardCreateFragment.newInstance(card);
                dialog.setTargetFragment(this, REQUEST_CREATE_CARD);
                dialog.show(manager, TAG_CREATE_CARD);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateUI();
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CREATE_CARD:
                BaseCard card = (BaseCard) data.getSerializableExtra(CardCreateFragment.EXTRA_CARD);
                mCardAdapter.mCards.add(card);
        }
    }


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

    public void updateUI() {
        if (mCardAdapter == null) {
            CardLab cardLab = new CardLab(mAccount);
            mCardAdapter = new CardAdapter(cardLab.getCards());
            mCardRecyclerView.setAdapter(mCardAdapter);
        } else mCardAdapter.notifyDataSetChanged();
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
            if (BigDecimal.ZERO.equals(card.getQuota())) mCardRemain.setText("");
            else mCardRemain.setText(BaseCard.format(card.getRemain()));
            mCardNumber.setText(String.format(Locale.getDefault(), "卡号: %d", card.getCardNumber()));
            mCardType.setText(card.getCardName());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("card", mCard);
            startActivity(intent);
            // FragmentManager manager = getFragmentManager();
            //
            // FunctionDialog dialog = FunctionDialog.newInstance(mCard, CardListFragment.this);
            // dialog.setTargetFragment(CardListFragment.this, REQUEST_FUNCTION);
            // dialog.show(manager, TAG_FUNCTION);
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

