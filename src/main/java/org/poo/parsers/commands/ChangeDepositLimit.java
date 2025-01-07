package org.poo.parsers.commands;

import org.poo.accounts.Account;
import org.poo.accounts.BusinessAccount;
import org.poo.bankPair.Bank;
import org.poo.parsers.fileio.CommandInput;

public class ChangeDepositLimit implements Command {
    private int timestamp;
    private double amount;
    private String iban;
    private String email;

    public ChangeDepositLimit(CommandInput commandInput) {
        timestamp = commandInput.getTimestamp();
        amount = commandInput.getAmount();
        iban = commandInput.getAccount();
        email = commandInput.getEmail();
    }

    public void execute() {
        BusinessAccount account = (BusinessAccount) Bank.getInstance().getAccountByIBAN(iban);

        if (!account.getEmailToCards().containsKey(email)) {

        } else if (!account.getRbac().hasPermissions(email, "changeDepositLimit")) {

        } else if (amount < 0) {

        } else {
            account.setDepositLimit(amount);
        }
    }
}
