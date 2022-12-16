package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WellMapperTest {

    private WellMapper wellMapper;

    @BeforeEach
    public void setUp() {
        wellMapper = new WellMapperImpl();
    }
}
