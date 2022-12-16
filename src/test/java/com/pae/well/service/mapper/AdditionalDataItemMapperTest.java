package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdditionalDataItemMapperTest {

    private AdditionalDataItemMapper additionalDataItemMapper;

    @BeforeEach
    public void setUp() {
        additionalDataItemMapper = new AdditionalDataItemMapperImpl();
    }
}
