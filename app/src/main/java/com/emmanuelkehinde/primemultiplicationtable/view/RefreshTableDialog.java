package com.emmanuelkehinde.primemultiplicationtable.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emmanuelkehinde.primemultiplicationtable.R;
import com.emmanuelkehinde.primemultiplicationtable.data.PreferenceManager;
import com.emmanuelkehinde.primemultiplicationtable.util.Constants;
import com.emmanuelkehinde.primemultiplicationtable.util.DisplayUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import smartdevelop.ir.eram.showcaseviewlib.GuideView;

public class RefreshTableDialog extends DialogFragment {

    @BindView(R.id.edt_number_of_prime_no)
    EditText edt_number_of_prime_no;

    private RefreshTableListener mListener;
    private int numberOfPrimeNos = 10; //Initial number of prime numbers set

    public static RefreshTableDialog newInstance(int numberOfPrimeNos) {
        RefreshTableDialog refreshTableDialog = new RefreshTableDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BUNDLE_NUMBER_OF_PRIME, numberOfPrimeNos);
        refreshTableDialog.setArguments(bundle);
        return refreshTableDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.numberOfPrimeNos = getArguments().getInt(Constants.BUNDLE_NUMBER_OF_PRIME);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_refresh_table);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.view_refresh_table, null, false);
        ButterKnife.bind(this, v);
        builder.setView(v);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton("Refresh", null);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                edt_number_of_prime_no.setText(String.valueOf(numberOfPrimeNos));
                initializeRefreshButton(alertDialog);
            }
        });

        return alertDialog;

    }

    private void initializeRefreshButton(AlertDialog alertDialog) {
        Button refreshButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noOfPrimeNos = edt_number_of_prime_no.getText().toString();
                if (noOfPrimeNos.isEmpty()) {
                    DisplayUtil.showToast(getActivity(), getString(R.string.prompt_empty_number_of_prime_numbers));
                    return;
                }

                if (Integer.valueOf(noOfPrimeNos) > 100) {
                    DisplayUtil.showToast(getActivity(), "Maximum number of prime numbers allowed exceeded");
                    return;
                }

                mListener.onRefreshClicked(Integer.parseInt(noOfPrimeNos));
                dismissAllowingStateLoss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (RefreshTableListener) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

    public interface RefreshTableListener {
        void onRefreshClicked(int numberOfPrimeNos);
    }

}
