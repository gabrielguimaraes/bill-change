package com.gabrielguimaraes.billchange.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gabrielguimaraes.billchange.exceptions.NotEnoughCoinsException;
import com.gabrielguimaraes.billchange.model.CoinsHolder;
import com.gabrielguimaraes.billchange.repository.CoinsRepository;
import com.gabrielguimaraes.billchange.utils.BillsAndCoinsUtils;

@Service
public class CoinsForBillService {
    public static final Logger LOG = LoggerFactory.getLogger(CoinsForBillService.class);

    private final CoinsRepository coinsRepository;

    public CoinsForBillService(CoinsRepository coinsRepository) {
        this.coinsRepository = coinsRepository;
    }

    /**
     * 
     * • Available bills are (1, 2, 5, 10, 20, 50, 100)
     * • Available coins are (0.01, 0.05, 0.10, 0.25)
     * • Start with 100 coins of each type
     * • Change should be made by utilizing the least amount of coins
     * • API should validate bad input and respond accordingly
     * • Service should respond with an appropriate message if it does not have
     * enough coins to make change
     * • The service should maintain the state of the coins throughout the
     * transactions
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

    public List<CoinsHolder> convertBillIntoLeastNumberOfCoins(BigDecimal billValue) {
        CoinsAccumulator acc = new CoinsAccumulator();
        // subtract the bill using higher coin
        // iterate and decrease value in map
        while (billValue.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal coin = BillsAndCoinsUtils.COINS.stream()
                    .filter(coinsRepository::canSubtract)
                    .findFirst()
                    .orElseThrow(() -> this.undoChangesAndThrowException(acc));
            billValue = coinsRepository.subtractAndUpdate(billValue, coin);
            acc.increment(coin);
        }

        return acc.result();
    }

    private NotEnoughCoinsException undoChangesAndThrowException(CoinsAccumulator acc) {
        coinsRepository.undo(acc.retrieveMap());
        return new NotEnoughCoinsException("Oops, there is not enough coins.");
    }

    public void initialize() {
        this.coinsRepository.initialize();
    }

    public Map<BigDecimal, Integer> currentCoins() {
        return this.coinsRepository.coins();
    }
}
