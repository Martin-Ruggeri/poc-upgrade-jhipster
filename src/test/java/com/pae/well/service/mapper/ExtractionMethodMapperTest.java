package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExtractionMethodMapperTest {

    private ExtractionMethodMapper extractionMethodMapper;

    @BeforeEach
    public void setUp() {
        extractionMethodMapper = new ExtractionMethodMapperImpl();
    }
}
