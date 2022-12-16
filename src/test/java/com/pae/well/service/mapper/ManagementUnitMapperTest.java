package com.pae.well.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManagementUnitMapperTest {

    private ManagementUnitMapper managementUnitMapper;

    @BeforeEach
    public void setUp() {
        managementUnitMapper = new ManagementUnitMapperImpl();
    }
}
