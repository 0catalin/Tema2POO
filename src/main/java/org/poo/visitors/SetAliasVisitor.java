package org.poo.visitors;

import org.poo.accounts.BusinessAccount;
import org.poo.accounts.ClassicAccount;
import org.poo.accounts.SavingsAccount;
import org.poo.baseinput.User;
import org.poo.visitors.reportVisitors.Visitor;

public final class SetAliasVisitor implements Visitor {
    private final User user;
    private final String alias;
    private final String email;
    private final int timestamp;
    private final String iban;

    public SetAliasVisitor(final User user, final String alias,
                           final String email, final int timestamp,
                           final String iban) {

        this.user = user;
        this.alias = alias;
        this.email = email;
        this.timestamp = timestamp;
        this.iban = iban;

    }



    public void visit(final ClassicAccount account) {
            if (user.getAccounts().contains(account)) {
                account.setAlias(alias);
            }
    }



    public void visit(final BusinessAccount account) {

        if (!account.getEmailToCards().containsKey(email)) {
            return;
        } else if (!account.getRbac().hasPermissions(user.getEmail(), "setAlias")) {
            return;
        } else {
            account.setAlias(alias);
        }
    }



    public void visit(final SavingsAccount account) {
        if (user.getAccounts().contains(account)) {
            account.setAlias(alias);
        }
    }
}
