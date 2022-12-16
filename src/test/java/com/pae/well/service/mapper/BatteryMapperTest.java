package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BatteryMapperTest {

    private BatteryMapper batteryMapper;

    @BeforeEach
    public void setUp() {
        batteryMapper = new BatteryMapperImpl();
    }
}
