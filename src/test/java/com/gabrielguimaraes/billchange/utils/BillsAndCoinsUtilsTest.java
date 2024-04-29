package com.gabrielguimaraes.billchange.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BillsAndCoinsUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = { "0.01", "0.05", "0.10", "0.1", "0.25", "0.250" })
    public void givenValidCoins_whenIsValidCoin_thenReturnTrue(String coinStr) {
        // given
        // when
        boolean actual = BillsAndCoinsUtils.isValidCoin(coinStr);

        // then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "3", "test", "1.O" })
    public void givenInvalidCoins_whenIsValidCoin_thenReturnFalse(String coinStr) {
        // given
        // when
        boolean actual = BillsAndCoinsUtils.isValidCoin(coinStr);

        // then
        assertThat(actual).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = { "1", "2", "5", "10", "20", "50", "100" })
    public void givenValidBills_whenIsValidBill_thenReturnTrue(String billStr) {
        // given
        // when
        boolean actual = BillsAndCoinsUtils.isValidBill(billStr);

        // then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "1.0", "2.000", "5.000" })
    public void givenValidBillAsDecimal_whenIsValidBill_thenReturnTrue(String billStr) {
        // given
        // when
        boolean actual = BillsAndCoinsUtils.isValidBill(billStr);

        // then
        assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = { "3", "test", "1.O" })
    public void givenInvalidBills_whenIsValidBill_thenReturnFalse(String billStr) {
        // given
        // when
        boolean actual = BillsAndCoinsUtils.isValidBill(billStr);

        // then
        assertThat(actual).isFalse();
    }
}
