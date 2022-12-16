package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalDataItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalDataItemDTO.class);
        AdditionalDataItemDTO additionalDataItemDTO1 = new AdditionalDataItemDTO();
        additionalDataItemDTO1.setId(1L);
        AdditionalDataItemDTO additionalDataItemDTO2 = new AdditionalDataItemDTO();
        assertThat(additionalDataItemDTO1).isNotEqualTo(additionalDataItemDTO2);
        additionalDataItemDTO2.setId(additionalDataItemDTO1.getId());
        assertThat(additionalDataItemDTO1).isEqualTo(additionalDataItemDTO2);
        additionalDataItemDTO2.setId(2L);
        assertThat(additionalDataItemDTO1).isNotEqualTo(additionalDataItemDTO2);
        additionalDataItemDTO1.setId(null);
        assertThat(additionalDataItemDTO1).isNotEqualTo(additionalDataItemDTO2);
    }
}
