package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private double balance;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();        
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            balance = balance + amount;
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	        balance = balance - amount;
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
            	if(hasWithdrawals())
            		return amount * 0.001;
            	else
            		return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }
    
    public int getAccountType() {
        return accountType;
    }

    public void transferFunds(Account fromAcct, Account toAcct, double amount) {
    	if (fromAcct.accountType == toAcct.accountType)
    		throw new IllegalArgumentException("Can't transfer to the same account");
    	if(amount <= 0)
    		throw new IllegalArgumentException("amount must be greater than zero");
    	
    	fromAcct.withdraw(amount);
    	toAcct.deposit(amount);
    }
    
    private boolean hasWithdrawals() {
    	
    	for (Transaction t: transactions) {
    		if(t.amount < 0 && t.getTransactionDate().after(DateProvider.getInstance().last10Date())) {
    			return true;
    		}
    	}
    	return false;
    }
    public double getBalance() {
    	return balance;
    }
}
