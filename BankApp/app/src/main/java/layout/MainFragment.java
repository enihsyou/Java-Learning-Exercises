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
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.Card.CardLab;
import com.enihsyou.shane.bankapp.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

import static layout.MainDialog.EXTRA_CARD;

public class MainFragment extends Fragment {
    public static final String EXTRA_SHOW_ACCOUNT = "EXTRA_SHOW_ACCOUNT";

    private static final String TAG_CREATE_ACCOUNT = "CREATE_ACCOUNT_DIALOG";

    private static final int REQUEST_CREATE_ACCOUNT = 100;
    static final int DETAIL_FRAGMENT = 101;
    private RecyclerView mAccountRecyclerView;
    private MainAdapter mMainAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_main, container, false);

        mAccountRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_main_list, menu); //添加菜单
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_account:
                FragmentManager manager = getFragmentManager();

                BaseCard card = new BaseCard();

                MainDialog dialog = MainDialog.newInstance(card);
                dialog.setTargetFragment(MainFragment.this, REQUEST_CREATE_ACCOUNT);
                dialog.show(manager, TAG_CREATE_ACCOUNT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CREATE_ACCOUNT:
                BaseCard card = (BaseCard) data.getSerializableExtra(EXTRA_CARD);
                mMainAdapter.mCards.add(card);
                updateUI();
                break;
            case DETAIL_FRAGMENT:
                updateUI();
        }
    }

    private void updateUI() {
        if (mMainAdapter == null) {
            CardLab cardLab = CardLab.get(getActivity());
            mMainAdapter = new MainAdapter(cardLab.getCards());
            mAccountRecyclerView.setAdapter(mMainAdapter);
        } else mMainAdapter.notifyDataSetChanged();
    }

    private class MainHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mAccountName;
        private final TextView mCardBalance;
        private final TextView mCardRemain;
        private final TextView mCardNumber;
        private final TextView mCardType;
        private BaseCard mCard;

        MainHolder(View itemView) {
            super(itemView);
            /*给变量赋值 样式对象*/
            mAccountName = (TextView) itemView.findViewById(R.id.list_item_account_name);
            mCardBalance = (TextView) itemView.findViewById(R.id.list_item_card_balance);
            mCardRemain = (TextView) itemView.findViewById(R.id.list_item_card_remain);
            mCardNumber = (TextView) itemView.findViewById(R.id.list_item_card_number);
            mCardType = (TextView) itemView.findViewById(R.id.list_item_card_type);
            /*设置点击监听器*/
            itemView.setOnClickListener(this);
        }

        void bindCard(BaseCard card) {
            mCard = card;
            /*更新显示的内容*/
            mCardBalance.setText(getResources().getString(
                    R.string.card_balance_string,
                    BaseCard.format(card.getBalance())));
            if (BigDecimal.ZERO.equals(card.getQuota())) mCardRemain.setText("");
            else mCardRemain.setText(getResources().getString(
                    R.string.card_remain_string,
                    BaseCard.format(card.getRemain())));
            mCardNumber.setText(String.format(Locale.getDefault(), "卡号: %d", card.getCardNumber()));
            mCardType.setText(card.getCardName());
            mAccountName.setText(String.format(Locale.getDefault(), "账户: %s", mCard.getAccountName()));
        }

        @Override
        public void onClick(View view) {
            startActivityForResult(DetailActivity.newIntent(getActivity(), mCard.getID()), DETAIL_FRAGMENT);
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MainHolder> {
        private ArrayList<BaseCard> mCards;

        MainAdapter(ArrayList<BaseCard> cards) {
            mCards = cards;
        }

        @Override
        public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_main, parent, false);
            return new MainHolder(view);
        }

        @Override
        public void onBindViewHolder(MainHolder holder, int position) {
            BaseCard card = mCards.get(position);
            holder.bindCard(card);
        }

        @Override
        public int getItemCount() {
            return mCards.size();
        }
    }

}

