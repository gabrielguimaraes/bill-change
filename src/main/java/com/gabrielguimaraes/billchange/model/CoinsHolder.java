package com.gabrielguimaraes.billchange.model;

import java.math.BigDecimal;

public record CoinsHolder(BigDecimal coin, Integer total) {}