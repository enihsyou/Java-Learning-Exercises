package layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.enihsyou.shane.bankapp.Card.BaseCard;
import com.enihsyou.shane.bankapp.R;

import java.math.BigDecimal;

public class DetailActivity extends AppCompatActivity {
    private BaseCard mCard;
    private Button mDepositButton;
    private Button mWithdrawButton;
    private Button mPurchaseButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mCard = (BaseCard) getIntent().getSerializableExtra("card");

        setContentView(R.layout.dialog_fragment_function);
        mDepositButton = (Button) findViewById(R.id.button_deposit);
        mWithdrawButton = (Button) findViewById(R.id.button_withdraw);
        mPurchaseButton = (Button) findViewById(R.id.button_purchase);

        mDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_fragment_deposit,
                        null);

                new AlertDialog.Builder(getBaseContext()).setView(view1).setTitle(R.string.deposit).setPositiveButton(
                        android.R.string.ok,

                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = ((EditText) view1.findViewById(R.id.input_deposit_amount))
                                        .getText()
                                        .toString();
                                BigDecimal amount = new BigDecimal(input);
                                mCard.deposit(amount);
                            }
                        }).create().show();

            }
        });
        mWithdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_fragment_withdraw,
                        null);
                new AlertDialog.Builder(getBaseContext()).setView(view1).setTitle(R.string.withdraw)
                        .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String input = ((EditText) view1.findViewById(R.id.input_withdraw_amount))
                                        .getText()
                                        .toString();
                                BigDecimal amount = new BigDecimal(input);
                                mCard.withdraw(amount);
                            }
                        }).create().show();
            }
        });
        mPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View view1 = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_fragment_purchase,
                        null);
                new AlertDialog.Builder(getBaseContext()).setView(view1).setTitle(R.string.purchase)
                        .setPositiveButton(android.R.string.ok,
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

                                mCard.withdraw(amount, terms);
                            }
                        }).create().show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("1", mCard.getBalance().toString());
    }
}
