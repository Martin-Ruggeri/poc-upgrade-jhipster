package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaltWaterInjectionPlantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaltWaterInjectionPlantDTO.class);
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO1 = new SaltWaterInjectionPlantDTO();
        saltWaterInjectionPlantDTO1.setId(1L);
        SaltWaterInjectionPlantDTO saltWaterInjectionPlantDTO2 = new SaltWaterInjectionPlantDTO();
        assertThat(saltWaterInjectionPlantDTO1).isNotEqualTo(saltWaterInjectionPlantDTO2);
        saltWaterInjectionPlantDTO2.setId(saltWaterInjectionPlantDTO1.getId());
        assertThat(saltWaterInjectionPlantDTO1).isEqualTo(saltWaterInjectionPlantDTO2);
        saltWaterInjectionPlantDTO2.setId(2L);
        assertThat(saltWaterInjectionPlantDTO1).isNotEqualTo(saltWaterInjectionPlantDTO2);
        saltWaterInjectionPlantDTO1.setId(null);
        assertThat(saltWaterInjectionPlantDTO1).isNotEqualTo(saltWaterInjectionPlantDTO2);
    }
}
