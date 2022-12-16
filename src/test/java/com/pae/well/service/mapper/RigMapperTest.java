package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RigMapperTest {

    private RigMapper rigMapper;

    @BeforeEach
    public void setUp() {
        rigMapper = new RigMapperImpl();
    }
}
