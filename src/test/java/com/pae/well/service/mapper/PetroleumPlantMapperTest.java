package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PetroleumPlantMapperTest {

    private PetroleumPlantMapper petroleumPlantMapper;

    @BeforeEach
    public void setUp() {
        petroleumPlantMapper = new PetroleumPlantMapperImpl();
    }
}
