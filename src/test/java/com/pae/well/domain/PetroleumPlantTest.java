package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PetroleumPlantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetroleumPlant.class);
        PetroleumPlant petroleumPlant1 = new PetroleumPlant();
        petroleumPlant1.setId(1L);
        PetroleumPlant petroleumPlant2 = new PetroleumPlant();
        petroleumPlant2.setId(petroleumPlant1.getId());
        assertThat(petroleumPlant1).isEqualTo(petroleumPlant2);
        petroleumPlant2.setId(2L);
        assertThat(petroleumPlant1).isNotEqualTo(petroleumPlant2);
        petroleumPlant1.setId(null);
        assertThat(petroleumPlant1).isNotEqualTo(petroleumPlant2);
    }
}
