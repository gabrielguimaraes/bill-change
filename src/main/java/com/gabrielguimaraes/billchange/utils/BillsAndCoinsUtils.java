package com.gabrielguimaraes.billchange.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class BillsAndCoinsUtils {
    public static final Logger LOG = LoggerFactory.getLogger(BillsAndCoinsUtils.class);

    private BillsAndCoinsUtils() {
    }

    public static final BigDecimal COIN_0_01 = new BigDecimal("0.01").stripTrailingZeros();
    public static final BigDecimal COIN_0_05 = new BigDecimal("0.05").stripTrailingZeros();
    public static final BigDecimal COIN_0_10 = new BigDecimal("0.10").stripTrailingZeros();
    public static final BigDecimal COIN_0_25 = new BigDecimal("0.25").stripTrailingZeros();

    public static final BigDecimal BILL_1 = new BigDecimal("1").stripTrailingZeros();
    public static final BigDecimal BILL_2 = new BigDecimal("2").stripTrailingZeros();
    public static final BigDecimal BILL_5 = new BigDecimal("5").stripTrailingZeros();
    public static final BigDecimal BILL_10 = new BigDecimal("10").stripTrailingZeros();
    public static final BigDecimal BILL_20 = new BigDecimal("20").stripTrailingZeros();
    public static final BigDecimal BILL_50 = new BigDecimal("50").stripTrailingZeros();
    public static final BigDecimal BILL_100 = new BigDecimal("100").stripTrailingZeros();

    public static final List<BigDecimal> COINS = List.of(COIN_0_25, COIN_0_10, COIN_0_05, COIN_0_01);

    public static final List<BigDecimal> BILLS = List.of(BILL_1, BILL_2, BILL_5, BILL_10, BILL_20, BILL_50, BILL_100);

    public static Boolean isValidCoin(String cointStr) {
        Optional<BigDecimal> parsedCoin = parseBigDecimal(cointStr);
        return parsedCoin.map(COINS::contains).orElse(false);
    }

    public static Boolean isValidBill(String billStr) {
        Optional<BigDecimal> parsedBill = parseBigDecimal(billStr);
        return parsedBill.map(BILLS::contains).orElse(false);
    }

    private static Optional<BigDecimal> parseBigDecimal(String value) {
        return Optional.ofNullable(value)
                .map(str -> {
                    try {
                        return new BigDecimal(value).stripTrailingZeros();
                    } catch (NumberFormatException e) {
                        return null;
                    }
                });
    }

    public static String printBillsAsString() {
        return BILLS.stream().map(BigDecimal::toPlainString).collect(Collectors.joining(","));
    }
}
