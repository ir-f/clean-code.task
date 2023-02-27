package com.endava.tech.courses.java;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Alexandr Pumnea
 */
public class BalanceHandler {

    public String processBalance(Map<String, BigDecimal> map) {
        BigDecimal balance = map.getOrDefault("BALANCE", BigDecimal.ZERO);
        BigDecimal historicBalance = map.get("HISTORIC_BALANCE");

        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        //The result is put in a setter of a class which is omitted for less complexity
        return calculateTrackingBalance(balance, historicBalance);
    }

    public String calculateTrackingBalance(BigDecimal balance, BigDecimal historicBalance) {

        BigDecimal calculatedBalance;

        if (shouldUseBalance(balance, historicBalance)) {
            calculatedBalance = balance;
        } else if (shouldAddBalances(balance, historicBalance)) {
            calculatedBalance = balance.add(historicBalance);
        } else {
            calculatedBalance = historicBalance;
        }

        return calculatedBalance.toPlainString();
    }

    private boolean shouldUseBalance(BigDecimal balance, BigDecimal historicBalance) {
        boolean noHistoricBalance = historicBalance == null;
        boolean historicBalanceIsZero = historicBalance != null && historicBalance.compareTo(BigDecimal.ZERO) == 0;
        boolean balanceIsDebit = getPosition(balance) == BalancePosition.DEBIT;
        boolean balanceIsCredit = getPosition(balance) == BalancePosition.CREDIT;

        boolean historicBalanceIsCredit = getPosition(historicBalance) == BalancePosition.CREDIT;

        return noHistoricBalance || historicBalanceIsZero || (balanceIsDebit && historicBalanceIsCredit) || (balanceIsCredit && historicBalanceIsCredit);
    }

    private boolean shouldAddBalances(BigDecimal balance, BigDecimal historicBalance) {
        boolean balanceIsDebit = getPosition(balance) == BalancePosition.DEBIT;
        boolean historicBalanceIsDebit = getPosition(historicBalance) == BalancePosition.DEBIT;
        boolean balanceIsCredit = getPosition(balance) == BalancePosition.CREDIT;

        return (balanceIsDebit && historicBalanceIsDebit) || (balanceIsCredit && historicBalanceIsDebit);
    }

    private BalancePosition getPosition(BigDecimal balance) {
        if (balance == null) {
            return BalancePosition.ZERO;
        } else if (balance.signum() == -1) {
            return BalancePosition.DEBIT;
        } else {
            return BalancePosition.CREDIT;
        }
    }

    private enum BalancePosition {

        /**
         * A debit position.
         */
        DEBIT,

        /**
         * A credit position.
         */
        CREDIT,

        /**
         * A zero position.
         */
        ZERO
    }

}

