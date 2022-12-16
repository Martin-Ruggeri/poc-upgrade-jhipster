package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GasPlantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GasPlant.class);
        GasPlant gasPlant1 = new GasPlant();
        gasPlant1.setId(1L);
        GasPlant gasPlant2 = new GasPlant();
        gasPlant2.setId(gasPlant1.getId());
        assertThat(gasPlant1).isEqualTo(gasPlant2);
        gasPlant2.setId(2L);
        assertThat(gasPlant1).isNotEqualTo(gasPlant2);
        gasPlant1.setId(null);
        assertThat(gasPlant1).isNotEqualTo(gasPlant2);
    }
}
