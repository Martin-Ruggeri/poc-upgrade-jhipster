package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RigDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RigDTO.class);
        RigDTO rigDTO1 = new RigDTO();
        rigDTO1.setId(1L);
        RigDTO rigDTO2 = new RigDTO();
        assertThat(rigDTO1).isNotEqualTo(rigDTO2);
        rigDTO2.setId(rigDTO1.getId());
        assertThat(rigDTO1).isEqualTo(rigDTO2);
        rigDTO2.setId(2L);
        assertThat(rigDTO1).isNotEqualTo(rigDTO2);
        rigDTO1.setId(null);
        assertThat(rigDTO1).isNotEqualTo(rigDTO2);
    }
}
