package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SaltWaterInjectionPlantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaltWaterInjectionPlant.class);
        SaltWaterInjectionPlant saltWaterInjectionPlant1 = new SaltWaterInjectionPlant();
        saltWaterInjectionPlant1.setId(1L);
        SaltWaterInjectionPlant saltWaterInjectionPlant2 = new SaltWaterInjectionPlant();
        saltWaterInjectionPlant2.setId(saltWaterInjectionPlant1.getId());
        assertThat(saltWaterInjectionPlant1).isEqualTo(saltWaterInjectionPlant2);
        saltWaterInjectionPlant2.setId(2L);
        assertThat(saltWaterInjectionPlant1).isNotEqualTo(saltWaterInjectionPlant2);
        saltWaterInjectionPlant1.setId(null);
        assertThat(saltWaterInjectionPlant1).isNotEqualTo(saltWaterInjectionPlant2);
    }
}
