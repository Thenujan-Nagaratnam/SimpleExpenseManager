package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.Date;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class Database extends SQLiteOpenHelper {

    public static final String ACCOUNT = "account";
    public static final String ACCOUNTNO = "accountno";
    public static final String BANKNAME = "bankname";
    public static final String ACCOUNTHOLDERNAME = "accountholdername";
    public static final String ACCOUNT_TRANSACTION = "account_transaction";
    public static final String EXPENSETYPE = "expensetype";
    public static final String AMOUNT = "amount";
    public static final String DATE = "date";

    public Database(@Nullable Context context) {
        super(context, "200647R", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccount = "create table if not exists " +
                ACCOUNT + " (" + ACCOUNTNO + " varchar primary key, " + BANKNAME + " varchar, " + ACCOUNTHOLDERNAME + " varchar,balance real)";
        String createTransaction = "create table if not exists " + ACCOUNT_TRANSACTION + "( transactionid integer primary key ," +
                ACCOUNTNO + " varchar , " +
                EXPENSETYPE + " integer, " +
                AMOUNT + " real, " +
                DATE + " date, " +
                "foreign key (" + ACCOUNTNO + ") references account(" + ACCOUNTNO + ")) ;";

        sqLiteDatabase.execSQL(createAccount);
        sqLiteDatabase.execSQL(createTransaction);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    public boolean addAccount(Account account){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(ACCOUNTNO , account.getAccountNo());
//        contentValues.put(BANKNAME, account.getBankName());
//        contentValues.put(ACCOUNTHOLDERNAME, account.getAccountHolderName());
//
//        long insert = db.insert(ACCOUNT,null, contentValues);
//
//        if (insert == -1){
//            return false;
//        }else{
//            return true;
//        }
//    }
//
//    public boolean addTransaction(Transaction transaction){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//
//        contentValues.put(ACCOUNTNO, transaction.getAccountNo());
//        ExpenseType expenseType = transaction.getExpenseType();
//        if(expenseType == ExpenseType.EXPENSE){
//            contentValues.put(EXPENSETYPE, 0);
//        }else{
//            contentValues.put(EXPENSETYPE, 1);
//        }
//        Date date = transaction.getDate();
//        contentValues.put(AMOUNT, transaction.getAmount());
//        contentValues.put(DATE, date.getTime());
//
//        long insert = db.insert(ACCOUNT_TRANSACTION,null, contentValues);
//
//        if (insert == -1){
//            return false;
//        }else{
//            return true;
//        }
//
//    }


}
