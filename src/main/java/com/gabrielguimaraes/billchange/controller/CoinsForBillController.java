package com.gabrielguimaraes.billchange.controller;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielguimaraes.billchange.model.CoinsHolder;
import com.gabrielguimaraes.billchange.service.CoinsForBillService;
import com.gabrielguimaraes.billchange.utils.BillsAndCoinsUtils;

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

@RestController
@RequestMapping(path = "/api")
public class CoinsForBillController {
    public static final Logger LOG = LoggerFactory.getLogger(CoinsForBillController.class);
    private final CoinsForBillService coinsForBillService;

    public CoinsForBillController(CoinsForBillService coinsForBillService) {
        this.coinsForBillService = coinsForBillService;
    }

    @GetMapping({ "/change", "/change/", "/change/{bill}" })
    public List<CoinsHolder> getCoinsForBill(@PathVariable(value = "bill", required = false) String bill) {
        LOG.info(bill);
        if (!BillsAndCoinsUtils.isValidBill(bill)) {
            throw new InvalidParameterException(
                    String.format("Invalid bill. Please provide one of the following bills: %s",
                            BillsAndCoinsUtils.printBillsAsString()));
        }
        return this.coinsForBillService.convertBillIntoLeastNumberOfCoins(new BigDecimal(bill));
    }

    @GetMapping("/reset")
    public String resetCoins() {
        LOG.info("Reseting coins");
        this.coinsForBillService.initialize();
        return "Coins reset.";
    }

    @GetMapping("/map")
    public String map() {
        LOG.info("Get map");
        return this.coinsForBillService.currentCoins().toString();
    }

}
