package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdditionalDataItemDescriptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdditionalDataItemDescriptionDTO.class);
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO1 = new AdditionalDataItemDescriptionDTO();
        additionalDataItemDescriptionDTO1.setId(1L);
        AdditionalDataItemDescriptionDTO additionalDataItemDescriptionDTO2 = new AdditionalDataItemDescriptionDTO();
        assertThat(additionalDataItemDescriptionDTO1).isNotEqualTo(additionalDataItemDescriptionDTO2);
        additionalDataItemDescriptionDTO2.setId(additionalDataItemDescriptionDTO1.getId());
        assertThat(additionalDataItemDescriptionDTO1).isEqualTo(additionalDataItemDescriptionDTO2);
        additionalDataItemDescriptionDTO2.setId(2L);
        assertThat(additionalDataItemDescriptionDTO1).isNotEqualTo(additionalDataItemDescriptionDTO2);
        additionalDataItemDescriptionDTO1.setId(null);
        assertThat(additionalDataItemDescriptionDTO1).isNotEqualTo(additionalDataItemDescriptionDTO2);
    }
}
