package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdditionalDataMapperTest {

    private AdditionalDataMapper additionalDataMapper;

    @BeforeEach
    public void setUp() {
        additionalDataMapper = new AdditionalDataMapperImpl();
    }
}
