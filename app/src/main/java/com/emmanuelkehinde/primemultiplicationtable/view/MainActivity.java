package com.emmanuelkehinde.primemultiplicationtable.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.emmanuelkehinde.primemultiplicationtable.R;
import com.emmanuelkehinde.primemultiplicationtable.data.PreferenceManager;
import com.emmanuelkehinde.primemultiplicationtable.util.DisplayUtil;
import com.emmanuelkehinde.primemultiplicationtable.util.NumberUtil;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;

public class MainActivity extends AppCompatActivity implements RefreshTableDialog.RefreshTableListener {

    TableRow tb_row_horizontal;

    @BindView(R.id.tb_layout)
    TableLayout tb_layout;

    @BindView(R.id.menu_item_refresh)
    ImageView menu_item_refresh;

    private int numberOfPrimeNos = 10; //Initial number of prime numbers set
    private PreferenceManager preferenceManager;
    private TextView column1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(this);

        processTable(numberOfPrimeNos); //First Launch

        menu_item_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRefreshDialog();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!preferenceManager.isHomeShowCase1Shown()) {
                    showShowCase1();
                } else if (!preferenceManager.isHomeShowCase2Shown()) {
                    showShowCase2();
                }
            }
        },2000); //Wait 2 sec before showing showcase
    }

    private void showRefreshDialog() {
        RefreshTableDialog refreshTableDialog = RefreshTableDialog.newInstance(numberOfPrimeNos);
        refreshTableDialog.show(getSupportFragmentManager(), null);
    }

    private void showShowCase1() {
        new GuideView.Builder(MainActivity.this)
                .setTitle("Prime Number Multiplication Table")
                .setContentText("The table shows the multiplication of the first ten prime numbers")
                .setGravity(GuideView.Gravity.center)
                .setTargetView(column1)
                .setDismissType(GuideView.DismissType.anywhere)
                .setGuideListener(new GuideView.GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        preferenceManager.setHomeShowCase1Shown();
                        showShowCase2();
                    }
                })
                .build()
                .show();
    }

    private void showShowCase2() {
        new GuideView.Builder(MainActivity.this)
                .setTitle("Refresh Table Button")
                .setContentText("Tap the button to open a dialog\n...Then insert the number of prime numbers desired...")
                .setGravity(GuideView.Gravity.center)
                .setTargetView(menu_item_refresh)
                .setDismissType(GuideView.DismissType.anywhere)
                .setGuideListener(new GuideView.GuideListener() {
                    @Override
                    public void onDismiss(View view) {
                        preferenceManager.setHomeShowCase2Shown();
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onRefreshClicked(final int numberOfPrimeNos) {
        this.numberOfPrimeNos = numberOfPrimeNos;
        DisplayUtil.showLongToast(this, getString(R.string.prompt_processing));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                processTable(numberOfPrimeNos);
            }
        }, 1000); //Allow Toast message to show first to the user
    }

    private void processTable(final int numberOfPrimeNos) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() { //So as to access views
                    @Override
                    public void run() {
                        createAndShowTable(numberOfPrimeNos);
                    }
                });

            }
        }).start();

    }

    private void createAndShowTable(int numberOfPrimeNos) {

        emptyTable(); //Empty the table layout before redrawing
        createFirstTableRow(); //Create first row to contain the horizontal heading
        createFirstColumn();

        List<Integer> primeNos = NumberUtil.getPrimeNumbers(numberOfPrimeNos);
        for (int i = 0; i < primeNos.size(); i++) {
            //Add horizontal heading contents
            tb_row_horizontal.addView(createTextView(primeNos.get(i).toString(), true));

            //Add vertical heading contents
            TableRow tableRowVertical = new TableRow(MainActivity.this);
            tableRowVertical.addView(createTextView(primeNos.get(i).toString(), true));

            for (int j = 0; j < primeNos.size(); j++) {
                //Add Products
                String product = String.valueOf(NumberUtil.productOf(primeNos.get(i), primeNos.get(j)));
                tableRowVertical.addView(createTextView(product, false));
            }

            tb_layout.addView(tableRowVertical); //Finally add the vertical table rows to the table layout
        }

    }

    private void emptyTable() {
        tb_layout.removeAllViewsInLayout();
    }

    private void createFirstTableRow() {
        TableRow tableRow = new TableRow(this);
        tableRow.setId(R.id.tb_row_horizontal);
        tb_layout.addView(tableRow);
    }

    private void createFirstColumn() {
        column1 = createTextView(String.valueOf("X"), true); //X to symbolise multiplication
        tb_row_horizontal = findViewById(R.id.tb_row_horizontal); //Butterknife won't handle view binding after onCreate is called
        tb_row_horizontal.addView(column1);
    }

    private TextView createTextView(String text, boolean isHeading) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(32,32,32,32);
        if (isHeading) {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_table_item_heading));
        } else {
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_table_item));
        }
        return textView;
    }

}
