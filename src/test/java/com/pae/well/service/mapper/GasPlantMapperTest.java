package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GasPlantMapperTest {

    private GasPlantMapper gasPlantMapper;

    @BeforeEach
    public void setUp() {
        gasPlantMapper = new GasPlantMapperImpl();
    }
}
