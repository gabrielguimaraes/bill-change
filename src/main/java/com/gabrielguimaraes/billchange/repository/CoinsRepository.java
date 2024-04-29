package com.gabrielguimaraes.billchange.repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gabrielguimaraes.billchange.utils.BillsAndCoinsUtils;

import jakarta.annotation.PostConstruct;

@Component
public class CoinsRepository {
    public static final Logger LOG = LoggerFactory.getLogger(CoinsRepository.class);

    public final Map<BigDecimal, Integer> TOTAL_COINS = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void initialize() {
        TOTAL_COINS.put(BillsAndCoinsUtils.COIN_0_01, 100);
        TOTAL_COINS.put(BillsAndCoinsUtils.COIN_0_05, 100);
        TOTAL_COINS.put(BillsAndCoinsUtils.COIN_0_10, 100);
        TOTAL_COINS.put(BillsAndCoinsUtils.COIN_0_25, 100);
    }  
    
    public boolean canSubtract(BigDecimal value) {
        return TOTAL_COINS.get(value) > 0;
    }

    public BigDecimal subtractAndUpdate(BigDecimal billValue, BigDecimal value) {
        billValue = billValue.subtract(value);
        var a = TOTAL_COINS.compute(value, (key, total) -> --total);
        LOG.info("{}", a);
        LOG.info("{}", TOTAL_COINS);
        return billValue;
    }

    public void undo(Map<BigDecimal, Integer> coinsToReturn) {
        coinsToReturn.forEach((key, value) -> TOTAL_COINS.compute(key, (k, v) -> v+=value));
    }

    public Map<BigDecimal, Integer> coins() {
        return new ConcurrentHashMap<>(TOTAL_COINS);
    }
}
