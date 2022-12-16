package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WellChangeExtractionMethodDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WellChangeExtractionMethodDTO.class);
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO1 = new WellChangeExtractionMethodDTO();
        wellChangeExtractionMethodDTO1.setId(1L);
        WellChangeExtractionMethodDTO wellChangeExtractionMethodDTO2 = new WellChangeExtractionMethodDTO();
        assertThat(wellChangeExtractionMethodDTO1).isNotEqualTo(wellChangeExtractionMethodDTO2);
        wellChangeExtractionMethodDTO2.setId(wellChangeExtractionMethodDTO1.getId());
        assertThat(wellChangeExtractionMethodDTO1).isEqualTo(wellChangeExtractionMethodDTO2);
        wellChangeExtractionMethodDTO2.setId(2L);
        assertThat(wellChangeExtractionMethodDTO1).isNotEqualTo(wellChangeExtractionMethodDTO2);
        wellChangeExtractionMethodDTO1.setId(null);
        assertThat(wellChangeExtractionMethodDTO1).isNotEqualTo(wellChangeExtractionMethodDTO2);
    }
}
