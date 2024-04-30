package com.gabrielguimaraes.billchange.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;

@SpringBootConfiguration
public class CoinsProperties {
    private final int defaultTotalCoins = 100;
    
    @Value("${coins.total:100}")
    private Integer totalCoins;

    public Integer getTotalCoins() {
        if (totalCoins > 0) return totalCoins;
        return defaultTotalCoins;
    }
}
