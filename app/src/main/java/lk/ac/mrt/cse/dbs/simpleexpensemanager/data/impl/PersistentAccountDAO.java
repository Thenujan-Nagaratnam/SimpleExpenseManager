package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.Database;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {
    public static final String ACCOUNT = "account";
    public static final String ACCOUNTHOLDERNAME = "accountholdername";
    public static final String ACCOUNTNO = "accountno";
    public static final String BANKNAME = "bankname";
    public static final String BALANCE = "balance";

    SQLiteDatabase database;

    public PersistentAccountDAO(SQLiteDatabase database){
        this.database = database;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumbers = new ArrayList<>();
        String getAccountNo = "select " + ACCOUNTNO + " from " + ACCOUNT;
        Cursor cursor = database.rawQuery(getAccountNo,null);
        try {
            while(cursor.moveToNext()){
                String accountNo = cursor.getString(cursor.getColumnIndex(ACCOUNTNO));
                accountNumbers.add(accountNo);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "select * from " + ACCOUNT;
        Cursor cursor =  database.rawQuery(query,null);
        try {
            while (cursor.moveToNext()){
                Account account = new Account(
                        cursor.getString(cursor.getColumnIndex(ACCOUNTNO)),
                        cursor.getString(cursor.getColumnIndex(BANKNAME)),
                        cursor.getString(cursor.getColumnIndex(ACCOUNTHOLDERNAME)),
                        cursor.getDouble(cursor.getColumnIndex(BALANCE))
                );
                accounts.add(account);
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        finally {
            if(cursor !=  null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String getAccount = "select * from " + ACCOUNT + " where " + ACCOUNTNO + " = " +accountNo;
        Cursor cursor = database.rawQuery(getAccount,null);
        if(cursor != null){
            cursor.moveToFirst();
            Account account = new Account(
                    cursor.getString(cursor.getColumnIndex(ACCOUNTNO)),
                    cursor.getString(cursor.getColumnIndex(BANKNAME)),
                    cursor.getString(cursor.getColumnIndex(ACCOUNTHOLDERNAME)),
                    cursor.getDouble(cursor.getColumnIndex(BALANCE))
            );
            return account;
        }else {
            throw new InvalidAccountException("Account no "+accountNo+" is invalid");
        }
    }



    @Override
    public void addAccount(Account account) {
        String addAccount = "insert into " + ACCOUNT + " (" + ACCOUNTNO + "," + BANKNAME + "," + ACCOUNTHOLDERNAME + "," + BALANCE + ") " +
                "values(?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(addAccount);
        statement.bindString(1,account.getAccountNo());
        statement.bindString(2,account.getBankName());
        statement.bindString(3,account.getAccountHolderName());
        statement.bindDouble(4,account.getBalance());
        statement.executeInsert();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String deleteAccount = "delete from " + ACCOUNT + " where " + ACCOUNTNO + " = ?";
        SQLiteStatement statement = database.compileStatement(deleteAccount);
        statement.bindString(1,accountNo);
        if(statement.executeUpdateDelete() == 0){
            throw new InvalidAccountException("Account not found");
        }

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        String updateBalance = "update " + ACCOUNT + " set " + BALANCE + " = " + BALANCE + " + ? where " + ACCOUNTNO + " = ?";
        SQLiteStatement statement = database.compileStatement(updateBalance);
        statement.bindString(2,accountNo);
        if(expenseType == ExpenseType.EXPENSE){
            statement.bindDouble(1,-amount);
        }else {
            statement.bindDouble(1,amount);
        }
        if(statement.executeUpdateDelete() ==0){
            throw new InvalidAccountException("Account not found");
        }

    }
}