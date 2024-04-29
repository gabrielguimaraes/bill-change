package com.gabrielguimaraes.billchange.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gabrielguimaraes.billchange.exceptions.NotEnoughCoinsException;
import com.gabrielguimaraes.billchange.model.CoinsAccumulator;
import com.gabrielguimaraes.billchange.model.CoinsHolder;

@Service
public class CoinsForBillService {
    public static final Logger LOG = LoggerFactory.getLogger(CoinsForBillService.class);

    private static final BigDecimal CENTS_0_01 = new BigDecimal("0.01");
    private static final BigDecimal CENTS_0_05 = new BigDecimal("0.05");
    private static final BigDecimal CENTS_0_10 = new BigDecimal("0.10");
    private static final BigDecimal CENTS_0_25 = new BigDecimal("0.25");
    /**
     * 
     * • Available bills are (1, 2, 5, 10, 20, 50, 100) 
     * • Available coins are (0.01, 0.05, 0.10, 0.25)
     * • Start with 100 coins of each type
     * • Change should be made by utilizing the least amount of coins
     * • API should validate bad input and respond accordingly
     * • Service should respond with an appropriate message if it does not have
     * enough coins to make change • The service should maintain the state of the
     * coins throughout the transactions
     * • Deliver the code with test cases
     * • Upload your code to GitHub and come to interview prepared to walk through
     * code
     * 
     * Bonus:
     * • Allow for number of coins to be configurable and easily changed
     * • Allow for the user to request for the most amount of coins to make change
     * 
     * Please “reply to all” if you need any clarification on the above assignment.
     * The team is copied on this email.
     */

    public final static Map<BigDecimal, Integer> TOTAL_COINS = new HashMap<>();
    static {
        initialize();
    }

    public static void initialize() {
        TOTAL_COINS.put(CENTS_0_01, 100);
        TOTAL_COINS.put(CENTS_0_05, 100);
        TOTAL_COINS.put(CENTS_0_10, 100);
        TOTAL_COINS.put(CENTS_0_25, 100);
    }
    
    public List<CoinsHolder> convertBillIntoCoins(final String bill) {
        BigDecimal billValue = new BigDecimal(bill);
        CoinsAccumulator acc = new CoinsAccumulator();
        // subtract the bill using higher coin
        // iterate and decrease value in map
        while (billValue.compareTo(BigDecimal.ZERO) != 0) {
            if (canSubstract(CENTS_0_25)) {
                billValue = subtractAndUpdate(billValue, CENTS_0_25);
                acc.increment(CENTS_0_25);
            } else if (canSubstract(CENTS_0_10)) {
                billValue = subtractAndUpdate(billValue, CENTS_0_10);
                acc.increment(CENTS_0_10);
            } else if (canSubstract(CENTS_0_05)) {
                billValue = subtractAndUpdate(billValue, CENTS_0_05);
                acc.increment(CENTS_0_05);
            } else if (canSubstract(CENTS_0_01)) {
                billValue = subtractAndUpdate(billValue, CENTS_0_01);
                acc.increment(CENTS_0_01);
            } else {
                throw new NotEnoughCoinsException("ooops, there is not enough coins anymore");
            }
        }

        return acc.result();
    }
    

    private Boolean canSubstract(BigDecimal value) {
        return TOTAL_COINS.get(value) > 0;
    }

    private BigDecimal subtractAndUpdate(BigDecimal billValue, BigDecimal value) {
        billValue = billValue.subtract(value);
        var a = TOTAL_COINS.compute(value, (key, total) -> --total);
        LOG.info("{}", a);
        LOG.info("{}", TOTAL_COINS);
        return billValue;
    }

}
