package org.poo.parsers.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.bankPair.Bank;
import org.poo.baseinput.User;
import org.poo.exceptions.PaymentInfoNotFoundException;
import org.poo.exceptions.UserNotFoundException;
import org.poo.parsers.fileio.CommandInput;
import org.poo.splitPayment.SplitPaymentInfo;

public final class AcceptSplitPayment implements Command {

    private String email;
    private String splitPaymentType;
    private int timestamp;


    public AcceptSplitPayment(final CommandInput commandInput) {
        email = commandInput.getEmail();
        splitPaymentType = commandInput.getSplitPaymentType();
        timestamp = commandInput.getTimestamp();
    }


    public void execute() {
        try {
            User user = Bank.getInstance().getUserByEmail(email);
            SplitPaymentInfo splitPaymentInfo
                    = Bank.getInstance().getSplitPaymentByTypeAndEmail(email, splitPaymentType);
            if (splitPaymentInfo.getObserver().update(email)) {
                Bank.getInstance().getSplitPayments().remove(splitPaymentInfo);
                splitPaymentInfo.successfulPayment();
            }
        } catch (PaymentInfoNotFoundException ignored) {

        } catch (UserNotFoundException e) {
            Bank.getInstance().getOutput().add(userNotFound());
        }
    }

    public ObjectNode userNotFound() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "acceptSplitPayment");
        ObjectNode outputNode = mapper.createObjectNode();
        outputNode.put("description", "User not found");
        outputNode.put("timestamp", timestamp);
        node.set("output", outputNode);
        node.put("timestamp", timestamp);
        return node;
    }
}
