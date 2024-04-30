package com.gabrielguimaraes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gabrielguimaraes.billchange.exceptions.NotEnoughCoinsException;
import com.gabrielguimaraes.billchange.model.CoinsHolder;
import com.gabrielguimaraes.billchange.service.CoinsForBillService;
import com.gabrielguimaraes.billchange.utils.BillsAndCoinsUtils;

@SpringBootTest
public class CoinsForBillServiceTest {
    public static List<CoinsHolder> COINS_20 = List.of(new CoinsHolder(BillsAndCoinsUtils.COIN_0_25, 80));
    public static List<CoinsHolder> COINS_20_02 = List.of(
            new CoinsHolder(BillsAndCoinsUtils.COIN_0_25, 60),
            new CoinsHolder(BillsAndCoinsUtils.COIN_0_10, 50));
    public static List<CoinsHolder> COINS_10 = List.of(new CoinsHolder(BillsAndCoinsUtils.COIN_0_25, 40));
    public static List<CoinsHolder> COINS_10_02 = List.of(
            new CoinsHolder(BillsAndCoinsUtils.COIN_0_25, 20),
            new CoinsHolder(BillsAndCoinsUtils.COIN_0_10, 50));

    @Autowired
    private CoinsForBillService coinsForBillService;

    public static Stream<Arguments> sourceOfBillsAndCoins() {
        return Stream.of(
                Arguments.of(List.of("10", "20"), List.of(COINS_10, COINS_20_02)),
                Arguments.of(List.of("20", "10"), List.of(COINS_20, COINS_10_02)));
    }

    @BeforeEach
    public void setUp() {
        this.coinsForBillService.initialize();
    }

    @ParameterizedTest
    @MethodSource("sourceOfBillsAndCoins")
    public void givenValidBillString_whenConvertBillIntoLeastNumberOfCoins_thenReturnCorrectResults(List<String> bills,
            List<List<CoinsHolder>> expected) {
        System.out.println(this.coinsForBillService.currentCoins());
        for (int i = 0; i < bills.size(); i++) {
            // given
            BigDecimal billValue = new BigDecimal(bills.get(i));

            // when
            List<CoinsHolder> actual = coinsForBillService.convertBillIntoLeastNumberOfCoins(billValue);

            // then
            assertThat(actual).hasSameElementsAs(expected.get(i));
        }

    }

    @ParameterizedTest
    @ValueSource(strings = { "100", "50" })
    public void givenValidBillString_whenConvertBillIntoLeastNumberOfCoins_thenThrowException(String bill) {
        // given
        BigDecimal billValue = new BigDecimal(bill);

        // when
        Executable executable = () -> coinsForBillService.convertBillIntoLeastNumberOfCoins(billValue);

        // then
        assertThrows(NotEnoughCoinsException.class, executable);
    }
}
