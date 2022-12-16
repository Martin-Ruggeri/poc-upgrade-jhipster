package com.pae.well.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExtractionMethodDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtractionMethodDTO.class);
        ExtractionMethodDTO extractionMethodDTO1 = new ExtractionMethodDTO();
        extractionMethodDTO1.setId(1L);
        ExtractionMethodDTO extractionMethodDTO2 = new ExtractionMethodDTO();
        assertThat(extractionMethodDTO1).isNotEqualTo(extractionMethodDTO2);
        extractionMethodDTO2.setId(extractionMethodDTO1.getId());
        assertThat(extractionMethodDTO1).isEqualTo(extractionMethodDTO2);
        extractionMethodDTO2.setId(2L);
        assertThat(extractionMethodDTO1).isNotEqualTo(extractionMethodDTO2);
        extractionMethodDTO1.setId(null);
        assertThat(extractionMethodDTO1).isNotEqualTo(extractionMethodDTO2);
    }
}
