package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WellStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WellStatusDTO.class);
        WellStatusDTO wellStatusDTO1 = new WellStatusDTO();
        wellStatusDTO1.setId(1L);
        WellStatusDTO wellStatusDTO2 = new WellStatusDTO();
        assertThat(wellStatusDTO1).isNotEqualTo(wellStatusDTO2);
        wellStatusDTO2.setId(wellStatusDTO1.getId());
        assertThat(wellStatusDTO1).isEqualTo(wellStatusDTO2);
        wellStatusDTO2.setId(2L);
        assertThat(wellStatusDTO1).isNotEqualTo(wellStatusDTO2);
        wellStatusDTO1.setId(null);
        assertThat(wellStatusDTO1).isNotEqualTo(wellStatusDTO2);
    }
}
