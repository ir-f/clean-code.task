package com.endava.tech.courses.java;

import java.math.BigDecimal;
import java.util.Map;

public class BalanceHandler {

    public String processBalance(final Map<String, BigDecimal> map) {
        BigDecimal balance = map.get("BALANCE");
        BigDecimal historicBalance = map.get("HISTORIC_BALANCE");

        if (balance == null) {
            balance = BigDecimal.ZERO;
        }

        //The result is put in a setter of a class which is omitted for less complexity
        return calculateTrackingBalance(balance, historicBalance);
    }

    //Feel free to make changes to the code below:
    private String calculateTrackingBalance(final BigDecimal balance, final BigDecimal historicBalance) {
        BigDecimal trackingBalance = BigDecimal.ZERO;

        if ((historicBalance == null || historicBalance.equals(BigDecimal.ZERO))
            || (isPositionInDebit(balance) && isPositionInCredit(historicBalance))
            || (isPositionInCredit(balance) && isPositionInCredit(historicBalance))) {
            trackingBalance = balance;
        } else if ((isPositionInDebit(balance) && isPositionInDebit(historicBalance))
            || (isPositionInCredit(balance) && isPositionInDebit(historicBalance))) {
            trackingBalance = balance.add(historicBalance);
        }

        return trackingBalance.toPlainString();
    }

    private boolean isPositionInDebit(BigDecimal position) {
        return position.signum() == -1;
    }

    private boolean isPositionInCredit(BigDecimal position) {
        return position.signum() == 1 || position.signum() == 0;
    }
}