package com.gabrielguimaraes.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gabrielguimaraes.billchange.repository.CoinsRepository;
import com.gabrielguimaraes.billchange.service.CoinsForBillService;
import com.gabrielguimaraes.billchange.utils.BillsAndCoinsUtils;

@ExtendWith(MockitoExtension.class)
public class CoinsForBillServiceTest {
    @Mock
    private CoinsRepository coinsRepository;

    private CoinsForBillService coinsForBillService;

    @BeforeEach
    void setUp() {
        this.coinsForBillService = new CoinsForBillService(coinsRepository);
    }

    @Test
    void givenMostAmountAsTrue_whenStreamOfCoins_shouldBeInverted() {
        // given
        boolean mostAmount = true;

        // when
        Optional<BigDecimal> first = this.coinsForBillService.streamOfCoins(mostAmount).findFirst();

        // then
        Assertions.assertThat(first)
                .isPresent()
                .contains(BillsAndCoinsUtils.COIN_0_01);
    }
}
