package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WellStatusMapperTest {

    private WellStatusMapper wellStatusMapper;

    @BeforeEach
    public void setUp() {
        wellStatusMapper = new WellStatusMapperImpl();
    }
}
