package com.pae.well.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.pae.well.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WellStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WellStatus.class);
        WellStatus wellStatus1 = new WellStatus();
        wellStatus1.setId(1L);
        WellStatus wellStatus2 = new WellStatus();
        wellStatus2.setId(wellStatus1.getId());
        assertThat(wellStatus1).isEqualTo(wellStatus2);
        wellStatus2.setId(2L);
        assertThat(wellStatus1).isNotEqualTo(wellStatus2);
        wellStatus1.setId(null);
        assertThat(wellStatus1).isNotEqualTo(wellStatus2);
    }
}
