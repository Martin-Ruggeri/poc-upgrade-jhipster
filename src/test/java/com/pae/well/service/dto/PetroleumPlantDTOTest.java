package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PetroleumPlantDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetroleumPlantDTO.class);
        PetroleumPlantDTO petroleumPlantDTO1 = new PetroleumPlantDTO();
        petroleumPlantDTO1.setId(1L);
        PetroleumPlantDTO petroleumPlantDTO2 = new PetroleumPlantDTO();
        assertThat(petroleumPlantDTO1).isNotEqualTo(petroleumPlantDTO2);
        petroleumPlantDTO2.setId(petroleumPlantDTO1.getId());
        assertThat(petroleumPlantDTO1).isEqualTo(petroleumPlantDTO2);
        petroleumPlantDTO2.setId(2L);
        assertThat(petroleumPlantDTO1).isNotEqualTo(petroleumPlantDTO2);
        petroleumPlantDTO1.setId(null);
        assertThat(petroleumPlantDTO1).isNotEqualTo(petroleumPlantDTO2);
    }
}
