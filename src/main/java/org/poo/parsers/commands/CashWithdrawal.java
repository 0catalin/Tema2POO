package org.poo.parsers.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.accounts.cards.Card;
import org.poo.bankPair.Bank;
import org.poo.baseinput.User;
import org.poo.exceptions.CardNotFoundException;
import org.poo.exceptions.UserNotFoundException;
import org.poo.parsers.fileio.CommandInput;

public class CashWithdrawal implements Command {
    public double amount;
    public String cardNumber;
    public String email;
    public String location;
    public int timestamp;

    public CashWithdrawal (CommandInput commandInput) {
        amount = commandInput.getAmount();
        cardNumber = commandInput.getCardNumber();
        email = commandInput.getEmail();
        location = commandInput.getLocation();
        timestamp = commandInput.getTimestamp();
    }
    public void execute() {
        try {
            Card card = Bank.getInstance().getCardByCardNumber(cardNumber);
            User user = Bank.getInstance().getUserByEmail(email);
            Account account = Bank.getInstance().getAccountByCardNumber(cardNumber);
            if (card.getStatus().equals("frozen")) {

            } else if (amount * Bank.getInstance().findExchangeRate("RON", account.getCurrency()) * user.getPlanMultiplier(amount) > account.getBalance()) {

            } else if (amount * Bank.getInstance().findExchangeRate("RON", account.getCurrency()) * user.getPlanMultiplier(amount) > account.getBalance() - account.getMinBalance()) {

            } else {
                account.setBalance(account.getBalance() - amount * Bank.getInstance().findExchangeRate("RON", account.getCurrency()) * user.getPlanMultiplier(amount));
                user.getTranzactions().add(successWithdrawal());


            }
        } catch (UserNotFoundException e) {

        } catch (CardNotFoundException e) {
            Bank.getInstance().getOutput().add(cardNotFound());
        }

    }

    private ObjectNode successWithdrawal() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode output = mapper.createObjectNode();
        output.put("timestamp", timestamp);
        output.put("description", "Cash withdrawal of " + amount);
        output.put("amount", amount);
        return output;
    }

    private ObjectNode cardNotFound() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode output = mapper.createObjectNode();
        output.put("command", "cashWithdrawal");
        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", "Card not found");
        outputNode.put("timestamp", timestamp);
        output.set("output", outputNode);
        output.put("timestamp", timestamp);
        return output;
    }
}
