package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WellChangeExtractionMethodMapperTest {

    private WellChangeExtractionMethodMapper wellChangeExtractionMethodMapper;

    @BeforeEach
    public void setUp() {
        wellChangeExtractionMethodMapper = new WellChangeExtractionMethodMapperImpl();
    }
}
