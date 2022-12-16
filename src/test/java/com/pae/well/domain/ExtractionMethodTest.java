package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExtractionMethodTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtractionMethod.class);
        ExtractionMethod extractionMethod1 = new ExtractionMethod();
        extractionMethod1.setId(1L);
        ExtractionMethod extractionMethod2 = new ExtractionMethod();
        extractionMethod2.setId(extractionMethod1.getId());
        assertThat(extractionMethod1).isEqualTo(extractionMethod2);
        extractionMethod2.setId(2L);
        assertThat(extractionMethod1).isNotEqualTo(extractionMethod2);
        extractionMethod1.setId(null);
        assertThat(extractionMethod1).isNotEqualTo(extractionMethod2);
    }
}
