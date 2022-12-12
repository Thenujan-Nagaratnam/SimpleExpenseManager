package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistentExpenseManager extends ExpenseManager {
    Context context;

    public PersistentExpenseManager(Context context) {
        this.context = context;
        try {
            setup();
        } catch (ExpenseManagerException expenseManagerException){
            expenseManagerException.printStackTrace();
        }
    }

    @Override
    public void setup() throws ExpenseManagerException {
        SQLiteDatabase database = context.openOrCreateDatabase("200647R",context.MODE_PRIVATE,null);

        Database db = new Database(this.context);

        db.onCreate(database);

//        database.execSQL("create table if not exists " +
//                "account (accountno varchar primary key, bankname varchar, accountholdername varchar,balance real)");
//
//        database.execSQL("create table if not exists " +
//                "account_transaction( transactionid integer primary key ," +
//                "accountno varchar ," +
//                "expensetype integer," +
//                "amount real," +
//                "date date," +
//                "foreign key (accountno) references account(accountno)) ;");

        PersistentAccountDAO persistentAccountDAO = new PersistentAccountDAO(database);
        PersistentTransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(database);
        setAccountsDAO(persistentAccountDAO);
        setTransactionsDAO(persistentTransactionDAO);

    }
}
