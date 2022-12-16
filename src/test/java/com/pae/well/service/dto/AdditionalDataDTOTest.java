package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalDataDTO.class);
        AdditionalDataDTO additionalDataDTO1 = new AdditionalDataDTO();
        additionalDataDTO1.setId(1L);
        AdditionalDataDTO additionalDataDTO2 = new AdditionalDataDTO();
        assertThat(additionalDataDTO1).isNotEqualTo(additionalDataDTO2);
        additionalDataDTO2.setId(additionalDataDTO1.getId());
        assertThat(additionalDataDTO1).isEqualTo(additionalDataDTO2);
        additionalDataDTO2.setId(2L);
        assertThat(additionalDataDTO1).isNotEqualTo(additionalDataDTO2);
        additionalDataDTO1.setId(null);
        assertThat(additionalDataDTO1).isNotEqualTo(additionalDataDTO2);
    }
}
