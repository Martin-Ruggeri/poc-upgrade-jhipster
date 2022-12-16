package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WellChangeExtractionMethodTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WellChangeExtractionMethod.class);
        WellChangeExtractionMethod wellChangeExtractionMethod1 = new WellChangeExtractionMethod();
        wellChangeExtractionMethod1.setId(1L);
        WellChangeExtractionMethod wellChangeExtractionMethod2 = new WellChangeExtractionMethod();
        wellChangeExtractionMethod2.setId(wellChangeExtractionMethod1.getId());
        assertThat(wellChangeExtractionMethod1).isEqualTo(wellChangeExtractionMethod2);
        wellChangeExtractionMethod2.setId(2L);
        assertThat(wellChangeExtractionMethod1).isNotEqualTo(wellChangeExtractionMethod2);
        wellChangeExtractionMethod1.setId(null);
        assertThat(wellChangeExtractionMethod1).isNotEqualTo(wellChangeExtractionMethod2);
    }
}
