package lk.ac.mrt.cse.dbs.simpleexpensemanager.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.R;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants.EXPENSE_MANAGER;
/**
 *
 */
public class AccountFragment extends Fragment {
    private ExpenseManager currentExpenseManager;

    public static AccountFragment newInstance(ExpenseManager expenseManager) {
        AccountFragment accountFragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXPENSE_MANAGER, expenseManager);
        accountFragment.setArguments(args);
        return accountFragment;
    }

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense_logs, container, false);
        TableLayout logsTableLayout = (TableLayout) rootView.findViewById(R.id.logs_table);
        TableRow tableRowHeader = (TableRow) rootView.findViewById(R.id.logs_table_header);

        currentExpenseManager = (ExpenseManager) getArguments().get(EXPENSE_MANAGER);
        List<Account> accounList = new ArrayList<>();
        if (currentExpenseManager != null) {
            accounList = currentExpenseManager.getAccountsDAO().getAccountsList();
        }
        generateTransactionsTable(rootView, logsTableLayout, accounList);
        return rootView;
    }

    private void generateTransactionsTable(View rootView, TableLayout logsTableLayout,
                                           List<Account> accounList) {
        for (Account account : accounList) {
            TableRow tr = new TableRow(rootView.getContext());
            TextView lDateVal = new TextView(rootView.getContext());


            TextView lAccountNoVal = new TextView(rootView.getContext());
            lAccountNoVal.setText(account.getAccountNo());
            tr.addView(lAccountNoVal);

            TextView lExpenseTypeVal = new TextView(rootView.getContext());
            lExpenseTypeVal.setText(account.getBankName());
            tr.addView(lExpenseTypeVal);

            TextView lAmountVal = new TextView(rootView.getContext());
            lAmountVal.setText(String.valueOf(account.getBalance()).toString());
            tr.addView(lAmountVal);

            TextView lAccountHolder = new TextView(rootView.getContext());
            lAccountHolder.setText(String.valueOf(account.getAccountHolderName()));
            tr.addView(lAccountHolder);

            logsTableLayout.addView(tr);
        }
    }
}

