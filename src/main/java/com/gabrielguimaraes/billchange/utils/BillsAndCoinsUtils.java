package com.gabrielguimaraes.billchange.utils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final public class BillsAndCoinsUtils {
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

    private static final Set<BigDecimal> COINS = Set.of(COIN_0_01, COIN_0_05, COIN_0_10, COIN_0_25);

    private static final Set<BigDecimal> BILLS = Set.of(BILL_1, BILL_2, BILL_5, BILL_10, BILL_20, BILL_50, BILL_100);

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
        return BILLS.stream().sorted().map(BigDecimal::toPlainString).collect(Collectors.joining(","));
    }
}
