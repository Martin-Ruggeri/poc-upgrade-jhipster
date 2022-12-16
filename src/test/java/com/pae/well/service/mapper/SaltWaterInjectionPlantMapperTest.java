package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaltWaterInjectionPlantMapperTest {

    private SaltWaterInjectionPlantMapper saltWaterInjectionPlantMapper;

    @BeforeEach
    public void setUp() {
        saltWaterInjectionPlantMapper = new SaltWaterInjectionPlantMapperImpl();
    }
}
