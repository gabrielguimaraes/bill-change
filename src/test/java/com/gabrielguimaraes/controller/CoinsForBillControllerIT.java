package com.gabrielguimaraes.controller;

import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;

import com.gabrielguimaraes.billchange.controller.CoinsForBillController;
import com.gabrielguimaraes.billchange.model.CoinsHolder;
import com.gabrielguimaraes.billchange.service.CoinsForBillService;
import com.gabrielguimaraes.billchange.utils.BillsAndCoinsUtils;

@WebMvcTest(controllers = CoinsForBillController.class)
public class CoinsForBillControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoinsForBillService coinsForBillService;

    @Test
    void givenBill_whenCallChangeEndpoint_thenGetValidCoinsResponse() throws Exception {
        // given
        BigDecimal bill = new BigDecimal("10");

        // when
        Mockito.when(coinsForBillService.convertBillIntoLeastNumberOfCoins(bill, false)).thenReturn(List.of(new CoinsHolder(BillsAndCoinsUtils.COIN_0_25, 40)));

        mockMvc.perform(get(String.format("/api/change/%s", bill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].coin", is(0.25)))
                .andExpect(jsonPath("$[0].total", is(40)));
    }

}
