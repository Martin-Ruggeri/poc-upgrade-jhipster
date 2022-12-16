package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WellDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WellDTO.class);
        WellDTO wellDTO1 = new WellDTO();
        wellDTO1.setId(1L);
        WellDTO wellDTO2 = new WellDTO();
        assertThat(wellDTO1).isNotEqualTo(wellDTO2);
        wellDTO2.setId(wellDTO1.getId());
        assertThat(wellDTO1).isEqualTo(wellDTO2);
        wellDTO2.setId(2L);
        assertThat(wellDTO1).isNotEqualTo(wellDTO2);
        wellDTO1.setId(null);
        assertThat(wellDTO1).isNotEqualTo(wellDTO2);
    }
}
