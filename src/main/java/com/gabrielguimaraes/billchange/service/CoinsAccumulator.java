package com.gabrielguimaraes.billchange.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gabrielguimaraes.billchange.model.CoinsHolder;

public class CoinsAccumulator {
    public static final Logger LOG = LoggerFactory.getLogger(CoinsAccumulator.class);
    private Map<BigDecimal, Integer> coinsHolder;

    public CoinsAccumulator() {
        this.coinsHolder = new HashMap<>();
    }

    public void increment(BigDecimal coin) {
        var a = coinsHolder.compute(coin, (key, value) -> value == null ? 1 : ++value);
        LOG.info("increment {}, map {}", a, coinsHolder);

    }

    public List<CoinsHolder> result() {
        return coinsHolder.entrySet().stream()
                .map(entry -> new CoinsHolder(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Map<BigDecimal, Integer> retrieveMap() {
        return new HashMap<>(coinsHolder);
    }

}
