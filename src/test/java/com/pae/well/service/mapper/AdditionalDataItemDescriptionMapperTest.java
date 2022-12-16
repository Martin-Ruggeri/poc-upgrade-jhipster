package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdditionalDataItemDescriptionMapperTest {

    private AdditionalDataItemDescriptionMapper additionalDataItemDescriptionMapper;

    @BeforeEach
    public void setUp() {
        additionalDataItemDescriptionMapper = new AdditionalDataItemDescriptionMapperImpl();
    }
}
