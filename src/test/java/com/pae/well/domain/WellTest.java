package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WellTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Well.class);
        Well well1 = new Well();
        well1.setId(1L);
        Well well2 = new Well();
        well2.setId(well1.getId());
        assertThat(well1).isEqualTo(well2);
        well2.setId(2L);
        assertThat(well1).isNotEqualTo(well2);
        well1.setId(null);
        assertThat(well1).isNotEqualTo(well2);
    }
}
