package org.poo.parsers.commands;

import org.poo.accounts.Account;
import org.poo.accounts.BusinessAccount;
import org.poo.bankPair.Bank;
import org.poo.parsers.fileio.CommandInput;
import org.poo.visitors.ChangeSpendingLimitVisitor;

public class ChangeSpendingLimit implements Command {

    private int timestamp;
    private double amount;
    private String iban;
    private String email;

    public ChangeSpendingLimit(CommandInput commandInput) {
        timestamp = commandInput.getTimestamp();
        amount = commandInput.getAmount();
        iban = commandInput.getAccount();
        email = commandInput.getEmail();
    }

    public void execute() {
        Account account = Bank.getInstance().getAccountByIBAN(iban);
        ChangeSpendingLimitVisitor visitor = new ChangeSpendingLimitVisitor(amount, email);
        account.accept(visitor);

    }
}
