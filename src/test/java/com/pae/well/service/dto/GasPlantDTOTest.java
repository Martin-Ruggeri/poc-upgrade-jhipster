package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GasPlantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GasPlantDTO.class);
        GasPlantDTO gasPlantDTO1 = new GasPlantDTO();
        gasPlantDTO1.setId(1L);
        GasPlantDTO gasPlantDTO2 = new GasPlantDTO();
        assertThat(gasPlantDTO1).isNotEqualTo(gasPlantDTO2);
        gasPlantDTO2.setId(gasPlantDTO1.getId());
        assertThat(gasPlantDTO1).isEqualTo(gasPlantDTO2);
        gasPlantDTO2.setId(2L);
        assertThat(gasPlantDTO1).isNotEqualTo(gasPlantDTO2);
        gasPlantDTO1.setId(null);
        assertThat(gasPlantDTO1).isNotEqualTo(gasPlantDTO2);
    }
}
